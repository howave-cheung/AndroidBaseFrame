package com.bobo.baseframe.widget.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.viewbinding.ViewBinding;

import com.bobo.baseframe.R;
import com.bobo.baseframe.widget.utils.ResUtils;
import com.bobo.baseframe.widget.utils.ToastUtils;
import com.trello.rxlifecycle4.components.support.RxFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName BaseFragment
 * @Description 底层的fragment封装
 */
public abstract class BaseFragment<VB extends ViewBinding> extends RxFragment implements View.OnClickListener{

    /**
     * Acitivity对象
     **/
    protected BaseActivity mActivity;

    protected Context mContext;

    /**
     * viewBinding
     */
    protected VB mViewBinding;

    /**
     * 当前显示的内容
     **/
    protected View mRootView;

    /**
     * ButterKnife
     */
    Unbinder unbinder;

    /**
     * isViewCreated: Fragment的View加载完毕的标记
     * isVisibleToUser: Fragment对用户可见的标记
     * isDataLoaded: 是否加载过数据
     */
    protected boolean isViewCreated;
    private boolean isVisibleToUser;
    protected boolean isDataLoaded;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        synchronized (BaseFragment.class) {
            this.mActivity = (BaseActivity) activity;
            mContext = mActivity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            if (isOpenViewBinding()){
                mViewBinding = getViewBinding(inflater,container);
                mRootView = mViewBinding.getRoot();
            }else {
                mRootView = inflater.inflate(getLayoutId(), container, false);
            }
        }

        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        unbinder = ButterKnife.bind(this, mRootView);

        if (isOpenEventBus()){
            EventBus.getDefault().register(this);
        }

        return mRootView;
    }

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

    /**
     * 获取ViewBinding
     * @return
     */
    protected abstract VB getViewBinding(LayoutInflater inflater, ViewGroup container);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isOpenEventBus()){
            EventBus.getDefault().unregister(this);
        }
        unbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        synchronized (BaseFragment.class) {
            this.mActivity = null;
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    public void showMessage(@StringRes int resId) {
        showMessage(ResUtils.getString(resId));
    }

    public void showMessage(@NonNull String msg) {
        ToastUtils.showNormalToast(msg);
    }

    public void showDelayedMessage(@StringRes int resId) {
        showDelayedMessage(ResUtils.getString(resId));
    }

    public void showDelayedMessage(@NonNull String msg) {
        ToastUtils.showDelayedToast(msg);
    }

    protected <T> T bindViewById(@IdRes int id) {
        //noinspection unchecked
        int idTemp = id;
        if (idTemp < 0 || mRootView == null) {
            return null;
        }
        //noinspection unchecked
        return (T) mRootView.findViewById(idTemp);
    }

    public void startActivity(Class<? extends Activity> clazz) {
        if (mActivity!=null) {
            mActivity.startActivity(clazz);
        }
    }

    protected void finishActivity() {
        if (mActivity != null) {
            mActivity.finish();
        }
    }

    protected void finishActivityForResult(int resultCode) {
        if (mActivity != null) {
            mActivity.finishActivity(resultCode);
        }
    }

    protected void finishActivity(Activity mActivity) {
        if (mActivity != null) {
            mActivity.finish();
        }
    }

    protected <T> T activityCast() {
        return (T) mActivity;
    }

    public void showProgressDialog() {
        mActivity.showProgressDialog();
    }

    public void dismissProgressDialog() {
        mActivity.dismissProgressDialog();
    }

    public void registerEventBus(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public void unregisterEventBus(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            lazyLoad();
        }
    }

    public void lazyLoad() {
        lazyLoad(false);
    }

    /**
     * 懒加载
     *
     * @param forceLoad 强制更新
     */
    public void lazyLoad(boolean forceLoad) {
        if (isViewCreated && isVisibleToUser && (!isDataLoaded || forceLoad)) {
            loadData();
            isDataLoaded = true;
        }
    }

    protected void loadData() {

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

    // 关闭键盘输入法
    public static void closeSoftInput(Context context, View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

}
