package com.bobo.baseframe.page.main;

import android.view.View;
import com.bobo.baseframe.R;
import com.bobo.baseframe.databinding.ActivityMainBinding;
import com.bobo.baseframe.widget.mvp.BaseActivity;
import com.bobo.baseframe.widget.utils.PageUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_name:
                PageUtils.startToolbarActivity(MainActivity.this, MainFragment.class);
                break;
            case R.id.tv_value:
                showMessage("2222");
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mViewBinding.tvName.setText("主页面");
    }

}
