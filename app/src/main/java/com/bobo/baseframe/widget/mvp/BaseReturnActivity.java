package com.bobo.baseframe.widget.mvp;

import android.view.KeyEvent;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.bobo.baseframe.R;
import com.bobo.baseframe.widget.utils.ResUtils;
import com.bobo.baseframe.databinding.BaseActivityReturnBinding;
import butterknife.OnClick;

/**
 * @ClassName BaseReturnActivity
 * @Description 一句话概括作用
 */
public abstract class BaseReturnActivity extends BaseActivity<BaseActivityReturnBinding> {

    private String tag;

    @Override
    protected BaseActivityReturnBinding getViewBinding() {
        return BaseActivityReturnBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_activity_return;
    }

    @Override
    public void onClick(View v) {
        if (tag.equals(getSupportFragmentManager().findFragmentById(R.id.base_return_activity_container).getTag())) {
            finish();
        } else {
            mViewBinding.baseReturnActivityTitleTv.setText(getTitleText());
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void initView() {

        tag = createFragment().getClass().getCanonicalName();
        replaceFragment(R.id.base_return_activity_container, createFragment(), tag, false);
        mViewBinding.baseReturnActivityTitleTv.setText(getTitleText());
        if (getLeftIconRes() != 0) {
            mViewBinding.baseReturnActivityLeftIv.setBackground(ResUtils.getDrawable(getLeftIconRes()));
        }
    }

    public void setTitleText(String titleText) {
        mViewBinding.baseReturnActivityTitleTv.setText(titleText);
    }


    protected abstract Fragment createFragment();

    protected abstract int getLeftIconRes();

    protected abstract String getTitleText();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onClick(mViewBinding.baseReturnActivityLeftIv);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
