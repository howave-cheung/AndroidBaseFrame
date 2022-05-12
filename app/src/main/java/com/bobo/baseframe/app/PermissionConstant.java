package com.bobo.baseframe.app;

import android.Manifest;

/**
 * @ClassName PermissionConstant
 * @Description 权限管理
 */
public class PermissionConstant {

    public static final String[] PERMISSION_LIST = new String[]{
            //相机
            Manifest.permission.CAMERA,
            //读写
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            //定位
            Manifest.permission.ACCESS_FINE_LOCATION
    };
}
