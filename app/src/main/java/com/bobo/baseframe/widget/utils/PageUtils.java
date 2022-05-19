package com.bobo.baseframe.widget.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.bobo.baseframe.widget.component.ImageFragment;
import com.bobo.baseframe.widget.mvp.ToolbarActivity;

/**
 * @ClassName PageUtils
 * @Description 一句话概括作用
 */
public class PageUtils {

    public static final String EXTRA_SILENT_LOGIN = "EXTRA_SILENT_LOGIN";

    /**
     * 跳转ToolbarActivity
     *
     * @param fragment Fragment.class
     */
    public static void startToolbarActivity(Activity context, Class<? extends Fragment> fragment) {
        startToolbarActivity(context, fragment, null, "", 0);
    }

    /**
     * 跳转ToolbarActivity
     *
     * @param fragment   Fragment.class
     * @param titleResId 标题资源id
     */
    public static void startToolbarActivity(Activity context, Class<? extends Fragment> fragment, @StringRes int titleResId) {
        startToolbarActivity(context, fragment, null, ResUtils.getString(titleResId), 0);
    }

    /**
     * 跳转ToolbarActivity
     *
     * @param fragment   Fragment.class
     * @param extras     数据
     * @param titleResId 标题
     */
    public static void startToolbarActivity(Activity context, Class<? extends Fragment> fragment, Bundle extras, @StringRes int titleResId) {
        startToolbarActivity(context, fragment, extras, ResUtils.getString(titleResId));
    }

    /**
     * 跳转ToolbarActivity
     *
     * @param fragment Fragment.class
     * @param title    标题
     */
    public static void startToolbarActivity(Activity context, Class<? extends Fragment> fragment, String title) {
        startToolbarActivity(context, fragment, null, title, 0);
    }

    /**
     * 跳转ToolbarActivity
     *
     * @param fragment Fragment.class
     * @param extras   数据
     * @param title    标题
     */
    public static void startToolbarActivity(Activity context, Class<? extends Fragment> fragment, Bundle extras, String title) {
        startToolbarActivity(context, fragment, extras, title, 0);
    }

    /**
     * 跳转ToolbarActivity
     *
     * @param fragment Fragment.class
     * @param extras   数据
     * @param title    标题
     */
    public static void startToolbarActivityForResult(Activity context, Class<? extends Fragment> fragment, Bundle extras, String title) {
        startToolbarActivityForResult(context, fragment, extras, title, 0);
    }

    /**
     * 跳转ToolbarActivity
     *
     * @param fragment    Fragment.class
     * @param extras      数据
     * @param title       标题
     * @param leftIconRes 左边图标资源id
     */
    public static void startToolbarActivity(Activity context, Class<? extends Fragment> fragment, Bundle extras,
                                            String title, @DrawableRes int leftIconRes) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ToolbarActivity.class);
        intent.putExtra(ToolbarActivity.EXTRA_FRAGMENT_KEY, fragment.getName());
        intent.putExtra(ToolbarActivity.EXTRA_TITLE, title);
        intent.putExtra(ToolbarActivity.EXTRA_LEFT_ICON_RES, leftIconRes);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    /**
     * 跳转ToolbarActivity
     *
     * @param fragment    Fragment.class
     * @param extras      数据
     * @param title       标题
     * @param leftIconRes 左边图标资源id
     */
    public static void startToolbarActivityForResult(Activity context, Class<? extends Fragment> fragment, Bundle extras,
                                                     String title, @DrawableRes int leftIconRes) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ToolbarActivity.class);
        intent.putExtra(ToolbarActivity.EXTRA_FRAGMENT_KEY, fragment.getName());
        intent.putExtra(ToolbarActivity.EXTRA_TITLE, title);
        intent.putExtra(ToolbarActivity.EXTRA_LEFT_ICON_RES, leftIconRes);
        if (extras != null) {
            intent.putExtras(extras);
        }
//        context.startActivity(intent);
        context.startActivityForResult(intent, 100);
    }

    /**
     * 跳转ToolbarActivity
     *
     * @param requestCode 请求码
     * @param fragment    Fragment.class
     * @param extras      数据
     * @param titleResId  标题资源id
     */
    public static void startToolbarActivity(Activity context, int requestCode, Class<? extends Fragment> fragment,
                                            Bundle extras, @StringRes int titleResId) {
        startToolbarActivity(context, requestCode, fragment, extras, ResUtils.getString(titleResId));
    }

    /**
     * 跳转ToolbarActivity
     *
     * @param requestCode 请求码
     * @param fragment    Fragment.class
     * @param extras      数据
     * @param title       标题
     */
    public static void startToolbarActivity(Activity context, int requestCode, Class<? extends Fragment> fragment,
                                            Bundle extras, String title) {
        Intent intent = new Intent(context, ToolbarActivity.class);
        intent.putExtra(ToolbarActivity.EXTRA_FRAGMENT_KEY, fragment.getName());
        intent.putExtra(ToolbarActivity.EXTRA_TITLE, title);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转图片展示
     */
    public static void startImageFragment(Activity activity, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        startToolbarActivity(activity, ImageFragment.class, bundle, "图片查看");
    }

    /**
     * 跳转登录页
     */
    public static void startLoginActivity(Activity activity) {
        startLoginActivity(activity, false);
    }

    /**
     * 跳转登录页
     *
     * @param silent 是否静默登录
     */
    public static void startLoginActivity(Activity activity, boolean silent) {
        activity.finish();
//        Intent intent = new Intent(activity, LoginActivity.class);
//        intent.putExtra(EXTRA_SILENT_LOGIN, silent);
//        activity.startActivity(intent);
    }

}
