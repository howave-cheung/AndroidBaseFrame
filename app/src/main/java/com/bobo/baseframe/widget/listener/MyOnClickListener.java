package com.bobo.baseframe.widget.listener;

import android.view.View;

/**
 * @ClassName 防止多次点击
 * @Description 一句话概括作用
 */
public abstract class MyOnClickListener implements View.OnClickListener {

    private static final int SPACE_TIME = 800;
    private long lastClickTime = 0;

    public boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isDoubleClick;
        isDoubleClick = currentTime - lastClickTime <= SPACE_TIME;
        lastClickTime = currentTime;
        return isDoubleClick;
    }
}
