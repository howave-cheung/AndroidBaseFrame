package com.bobo.baseframe.widget.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

/**
 * @ClassName BaseMvpFragment
 * @Description 通用的fragment
 */
public abstract class BaseMvpFragment<VB extends ViewBinding,P extends BasePresenter> extends BaseFragment implements BaseView {
    public P mPresenter;
    public VB mViewBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mPresenter != null) {
            mPresenter.resetView(this);
        }else{
            mPresenter = getPresenter();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract P getPresenter();
    protected abstract VB getViewBinding(LayoutInflater inflater, ViewGroup container);

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.destroy();
        }
        super.onDestroyView();
    }
}
