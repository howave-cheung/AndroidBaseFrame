package com.bobo.baseframe.widget.component.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.bobo.baseframe.R;
import com.bobo.baseframe.widget.utils.ToastUtils;


/**
 * @ClassName LoadingDialog
 * @Description loading加载圈
 */
public class LoadingDialog {

    private Dialog loadingDialog;
    private Runnable runnable;
    private Runnable delayRunnable;
    private View view;
    private Handler handler;
    private long timeOut = 30000;

    public LoadingDialog(Activity activity) {
        init(activity);
        initTask();
    }

    public void init(Activity context) {
        // 首先得到整个View
        view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        loadingDialog = new Dialog(context, R.style.TransparentDialogStyles);
        loadingDialog.getWindow().setDimAmount(0f);
        handler = new Handler();
        setCanceledOnTouchOutside(true);
    }

    /**
     * 显示dialog
     */
    public void show() {
        handler.postDelayed(delayRunnable, 50);
    }

    private void showLoading() {
        loadingDialog.show();
        handler.postDelayed(runnable, timeOut);
    }

    /**
     * 隐藏
     */
    public void dismiss() {
        long id = Thread.currentThread().getId();
        if (id == android.os.Process.myTid()) {
            loadingDialog.dismiss();
        } else {
            handler.post(() -> loadingDialog.dismiss());
        }
        handler.removeCallbacks(runnable);
        handler.removeCallbacks(delayRunnable);
    }

    /**
     * 超时
     */
    private void timeoutCancel() {
        boolean showing = loadingDialog.isShowing();
        ToastUtils.showNetworkError();
        if (showing) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 设置超时
     */
    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    private void initTask() {
        runnable = this::timeoutCancel;
        delayRunnable = this::showLoading;
    }

    /**
     * 设置返回键无效
     */
    public void setCanceledOnTouchOutside(boolean isClick) {
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT));
        loadingDialog.setCancelable(isClick);
    }

    public void onDestroy() {
        loadingDialog.dismiss();
        handler.removeCallbacks(runnable);
    }
}
