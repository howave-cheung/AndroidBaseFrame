package com.bobo.baseframe.network;

import com.bobo.baseframe.app.AppManager;
import com.bobo.baseframe.widget.utils.PageUtils;
import com.bobo.baseframe.widget.utils.ToastUtils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @ClassName NetworkCallback
 * @Description 网络返回的回调接口，可以在此对异常数据进行处理
 */
public abstract class NetworkCallback<T> implements Observer<T> {

    public abstract void onSuccess(T data);

    public abstract void onFailure(int code, String msg);

    @Override
    public void onSubscribe(@NotNull Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    /**
     * 对错误进行统一处理
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            if (apiException.resultCode == 40000) { // token 过期
                ToastUtils.showNormalToast(apiException.getMessage());
                PageUtils.startLoginActivity(AppManager.currentActivity());
            } else {
                onFailure(apiException.resultCode, apiException.getMessage());
            }
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            onFailure(httpException.code(), httpException.getMessage());
            e.printStackTrace();
        } else {
            onFailure(NetworkCode.CODE_NETWORK_FAILURE, NetworkCode.CODE_NETWORK_FAILURE + "错误");
            e.printStackTrace();
        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }
}

