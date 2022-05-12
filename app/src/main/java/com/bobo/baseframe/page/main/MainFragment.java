package com.bobo.baseframe.page.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.baseframe.R;
import com.bobo.baseframe.databinding.FragmentImageBinding;
import com.bobo.baseframe.databinding.FragmentMainBinding;
import com.bobo.baseframe.widget.mvp.BaseFragment;
import com.bobo.baseframe.databinding.ActivityMainBinding;

/**
 * @author bo
 * @date 2022/4/1
 */
public class MainFragment extends BaseFragment<FragmentMainBinding> {

    @Override
    protected FragmentMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMainBinding.inflate(inflater,container,false);
    }

    @Override
    public void onClick(View v) {
        showMessage("6666");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mViewBinding.tvName.setText("fragment");
    }
}
