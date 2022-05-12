package com.bobo.baseframe.widget.utils;

import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bobo.baseframe.R;
import com.bobo.baseframe.app.MyApplication;

/**
 * @ClassName ToastUtils
 * @Description 通用toast类
 */
public class ToastUtils {

    private static long ThreadID = android.os.Process.myTid();
    private static Handler handler;
    private static Toast toast;

    /**
     * 普通的吐司提示
     *
     * @param message 吐司内容
     */
    public static void showNormalToast(String message) {
        long id = Thread.currentThread().getId();
        if (ThreadID == id) {
            makeToast(message);
        } else {
            if (handler == null) {
                handler = new Handler();
            }
            handler.post(() -> makeToast(message));
        }
    }

    /**
     * 延迟的吐司提示
     *
     * @param message 吐司内容
     */
    public static void showDelayedToast(String message) {
        long id = Thread.currentThread().getId();
        if (ThreadID == id) {
            makeToast(message);
        } else {
            if (handler == null) {
                handler = new Handler();
            }
            handler.postDelayed(() -> makeToast(message), 300);
        }
    }

    public static void showNetworkError() {
        showNormalToast("请检查网络是否正常");
    }

    private static void makeToast(String msg) {
        cancel();
        View view = View.inflate(MyApplication.getInstance(), R.layout.view_toast, null);
        TextView toastView = view.findViewById(R.id.toast_view_text);
        toast = new Toast(MyApplication.getInstance());
        toast.setGravity(Gravity.CENTER, 1, 1);
        toast.setDuration(Toast.LENGTH_SHORT);
        toastView.setText(msg);
        toast.setView(view);
        toast.show();
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
