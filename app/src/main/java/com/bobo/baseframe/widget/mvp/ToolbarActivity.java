package com.bobo.baseframe.widget.mvp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bobo.baseframe.R;
import com.bobo.baseframe.widget.utils.ResUtils;
import com.bobo.baseframe.widget.utils.StatusBarUtils;
import com.bobo.baseframe.widget.utils.ValidatorUtils;


/**
 * @ClassName ToolbarActivity
 * @Description 一句话概括作用
 */
public class ToolbarActivity extends BaseReturnActivity {

    public static final String EXTRA_FRAGMENT_KEY = "ExtraFragmentKey";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_LEFT_ICON_RES = "ExtraLeftDrawableRes";
    private String title;
    private int leftIconRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setColor(this, ResUtils.getColor(R.color.app_blue));
        StatusBarUtils.setLightMode(this);
    }

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        this.title = intent.getStringExtra(EXTRA_TITLE);
        this.leftIconRes = intent.getIntExtra(EXTRA_LEFT_ICON_RES, 0);
        String className = intent.getStringExtra(EXTRA_FRAGMENT_KEY);
        if (ValidatorUtils.isNotBlank(className)) {
            Fragment fragment;
            try {
                fragment = (Fragment) Class.forName(className).newInstance();
                fragment.setArguments(intent.getExtras());
                return fragment;
            } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new Fragment();
    }

    public void replaceFragment(@NonNull Fragment fragment) {
        replaceFragmentToBackStack(R.id.base_return_activity_container, fragment);
    }

    @Override
    protected int getLeftIconRes() {
        return leftIconRes;
    }

    @Override
    protected String getTitleText() {
        return title;
    }

}