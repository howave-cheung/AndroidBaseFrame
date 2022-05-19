package com.bobo.baseframe.widget.component.view_pager.tab;

import androidx.annotation.StringRes;

import com.bobo.baseframe.widget.mvp.BaseFragment;
import com.bobo.baseframe.widget.utils.ResUtils;

/**
 * @ClassName TabChannel
 * @Description tab
 */
public class TabChannel {

    private String name;
    private BaseFragment fragment;

    public TabChannel(@StringRes int nameRes, BaseFragment fragment) {
        this(ResUtils.getString(nameRes), fragment);
    }

    public TabChannel(String name, BaseFragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public BaseFragment getFragment() {
        return fragment;
    }

    public void setFragment(BaseFragment fragment) {
        this.fragment = fragment;
    }
}
