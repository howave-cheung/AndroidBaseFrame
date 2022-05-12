package com.bobo.baseframe.widget.mvp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bobo.baseframe.R;
import com.bobo.baseframe.widget.utils.ResUtils;


public abstract class BaseDialogFragment extends DialogFragment {

    private OnDismissListener onDismissListener;

    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        initWindow();
        rootView = inflater.inflate(getLayoutId(), null);
        initView();
        return rootView;
    }

    private void initWindow() {
        setCancelable(false);
        Dialog dialog = this.getDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
        window.setBackgroundDrawable(initBackgroud());
    }

    protected Drawable initBackgroud() {
        return new ColorDrawable(ResUtils.getColor(R.color.app_transparent));
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected void close() {
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public View getView(int id) {
        if (rootView == null) {
            return null;
        }
        return rootView.findViewById(id);
    }

    public BaseDialogFragment setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}