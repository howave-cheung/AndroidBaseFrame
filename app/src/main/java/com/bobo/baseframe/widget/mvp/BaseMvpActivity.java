package com.bobo.baseframe.widget.mvp;

import android.os.Bundle;
import org.jetbrains.annotations.Nullable;

/**
 * @ClassName BaseMvpActivity
 * @Description 通用的activity
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView {
    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter=getPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P getPresenter();

}
