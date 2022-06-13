package com.bobo.baseframe.widget.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import com.bobo.baseframe.R;
import com.bobo.baseframe.widget.component.dialog.LoadingDialog;
import com.bobo.baseframe.widget.utils.ResUtils;
import com.bobo.baseframe.widget.utils.StatusBarUtils;
import com.bobo.baseframe.widget.utils.ToastUtils;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName BaseActivity
 * @Description 底层的activity封装
 */
public abstract class BaseActivity<VB extends ViewBinding> extends RxAppCompatActivity implements View.OnClickListener {
    protected int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    private LoadingDialog loadingDialog;
    private Map<String, Fragment> fragments = new ArrayMap<>();
    protected Context mContext;   //上下文
    private Unbinder unbinder;

    /**
     * viewBinding
     */
    protected VB mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar(0);
        if (allowSetRequestedOrientation()) {
            //正向竖屏，显示的高比宽长（锁死为竖屏方向，不再让方向感应起作用）
//            setRequestedOrientation(orientation);
        }

        mContext = this;
        initDialog();
        setContentView(getLayoutId());
        initView();

        if (isOpenEventBus()){
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 获取ViewBinding
     * @return
     */

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        // 重写setContentView
        if (isOpenViewBinding()){
            mViewBinding = getViewBinding();
            unbinder = ButterKnife.bind(mViewBinding.getRoot());
            super.setContentView(mViewBinding.getRoot());
        }else {
            unbinder = ButterKnife.bind(this);
            super.setContentView(layoutResID);
        }
    }

    /**
     * 获取ViewBinding
     * @return
     */
    protected abstract VB getViewBinding();

    /**
     * 是否开启了EventBus
     */
    protected boolean isOpenEventBus() {
        return true;
    }

    /**
     * 是否开启了ViewBinding
     */
    protected boolean isOpenViewBinding() {
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //非默认值
        if (newConfig.fontScale != 1) {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 设置 app 字体不随系统字体设置改变
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res != null) {
            Configuration config = res.getConfiguration();
            if (config != null && config.fontScale != 1.0f) {
                config.fontScale = 1.0f;
                res.updateConfiguration(config, res.getDisplayMetrics());
            }
        }
        return res;
    }

    protected int getLayoutId(){
        return 0;
    };

    protected abstract void initView();

    protected boolean allowSetRequestedOrientation() {
        return true;
    }

    private void initDialog() {
        loadingDialog = new LoadingDialog(this);
    }

    public void showProgressDialog() {
        loadingDialog.show();
    }

    public void dismissProgressDialog() {
        loadingDialog.dismiss();
    }

    private void setStatusBar(int skinType) {
        switch (skinType) {
            case 0:
                StatusBarUtils.setDarkMode(this);
                break;
            default:
                StatusBarUtils.setLightMode(this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (isOpenEventBus()){
            EventBus.getDefault().unregister(this);
        }
        loadingDialog.onDestroy();
        if (unbinder != null){
            unbinder.unbind();
            unbinder = null;
        }

        super.onDestroy();
    }

    public void showMessage(@NonNull String msg) {
        ToastUtils.showNormalToast(msg);
    }

    public void showMessage(@StringRes int resId) {
        ToastUtils.showNormalToast(ResUtils.getString(resId));
    }

    public void showDelayedMessage(@StringRes int resId) {
        showDelayedMessage(ResUtils.getString(resId));
    }

    public void showDelayedMessage(@NonNull String msg) {
        ToastUtils.showDelayedToast(msg);
    }

    public final void replaceFragmentToBackStack(@IdRes int containerViewId, Fragment fragment) {
        if (fragment != null) {
            replaceFragmentToBackStack(containerViewId, fragment, fragment.getClass().getCanonicalName());
        }
    }

    public final void replaceFragmentToBackStack(@IdRes int containerViewId, Fragment fragment, String tag) {
        if (fragment != null) {
            replaceFragment(containerViewId, fragment, tag, true);
        }
    }

    public final void replaceFragment(@IdRes int containerViewId, Fragment fragment) {
        if (fragment != null) {
            replaceFragment(containerViewId, fragment, fragment.getClass().getCanonicalName());
        }
    }

    public final void replaceFragment(@IdRes int containerViewId, Fragment fragment, String tag) {
        if (fragment != null) {
            replaceFragment(containerViewId, fragment, tag, false);
        }
    }

    public void replaceFragment(@IdRes int containerViewId, @NonNull Fragment fragment, String tag, boolean isAddToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment containerFragment = fragmentManager.findFragmentById(containerViewId);
        if (containerFragment == null) {
            fragmentTransaction.add(containerViewId, fragment, tag);
        } else {
            fragmentTransaction.replace(containerViewId, fragment, tag);
        }

        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    public <T extends Fragment> T findFragment(@NonNull Class<T> clazz, @NonNull final FragmentCallBack fragmentCallBack) {
        String key = clazz.getName();
        Fragment fragment = fragments.get(key);
        if (fragment == null) {
            fragment = fragmentCallBack.onCreateFragment();
            fragments.put(key, fragment);
        }
        //noinspection unchecked
        return (T) fragment;
    }

    public interface FragmentCallBack {
        Fragment onCreateFragment();
    }

    public void fullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            for (int i = 0; i < fragmentManager.getFragments().size(); i++) {
                Fragment fragment = fragmentManager.getFragments().get(i);
                if (fragment != null) {
                    handleResult(fragment, requestCode, resultCode, data);
                }
            }
        }
    }

    /**
     * 递归调用每个Fragment的onActivityResult
     */
    private void handleResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);
        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments();
        if (childFragment != null) {
            for (Fragment f : childFragment) {
                if (f != null) {
                    handleResult(f, requestCode, resultCode, data);
                }
            }
        }
    }

    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * EventBus订阅的参考
     *
     * @param event 自己自定义实体
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String event) {
    }

    /**
     * 点击事件
     * @param v 控件
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * 设置背景颜色
     *
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(Context mContext, float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
}
