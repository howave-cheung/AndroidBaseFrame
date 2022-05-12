package com.bobo.baseframe.widget.mvp;

/**
 * @ClassName BasePresenter
 * @Description mvp的p的通用处理类
 */
public class BasePresenter<V> {
    public V mView;

    public BasePresenter(V view) {
        this.mView = view;
    }

    public boolean isSafe(){
        return mView != null;
    }

    public void resetView(V view) {
        this.mView = view;
    }

    public void destroy() {
        mView = null;
    }
}
