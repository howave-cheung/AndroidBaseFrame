package com.bobo.baseframe.widget.component.view_pager.tab;

import android.os.Parcelable;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bobo.baseframe.widget.mvp.BaseFragment;

import java.util.ArrayList;

/**
 * @ClassName TabPagerAdapter
 * @Description 指针 TabPagerAdapter
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<TabChannel> tabChannelList = new ArrayList<>();

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return tabChannelList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return tabChannelList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabChannelList.get(position).getName();
    }

    public void setNewData(ArrayList<TabChannel> channels) {
        this.tabChannelList = channels;
        notifyDataSetChanged();
    }

    public void addTab(@StringRes int tabNameRes, BaseFragment baseFragment) {
        tabChannelList.add(new TabChannel(tabNameRes, baseFragment));
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public ArrayList<TabChannel> getTabChannelList() {
        return tabChannelList;
    }
}
