package com.bobo.baseframe.widget.mvp;

import android.os.Bundle;

import androidx.viewbinding.ViewBinding;

import org.jetbrains.annotations.Nullable;

/**
 * @ClassName BaseMvpActivity
 * @Description 通用的activity
 */
public abstract class BaseMvpActivity<VB extends ViewBinding,P extends BasePresenter> extends BaseActivity implements BaseView {
    public P mPresenter;
    public VB mViewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter=getPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P getPresenter();
    protected abstract VB getViewBinding();

}
