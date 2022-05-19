package com.bobo.baseframe.network.request;

import com.bobo.baseframe.network.BaseNetworkRequest;
import com.bobo.baseframe.network.NetworkClient;

import io.reactivex.Observable;
import io.reactivex.Observer;


/**
 * @ClassName LoginNetworkRequest
 * @Description 登录网络请求工具
 */
public class LoginNetworkRequest extends BaseNetworkRequest {

    private static LoginNetworkRequest instance;

    public static LoginNetworkRequest get() {
        if (instance == null) {
            synchronized (LoginNetworkRequest.class) {
                if (instance == null) {
                    instance = new LoginNetworkRequest();
                }
            }
        }
        return instance;
    }

    /**
     * 登录
     */
    public void login(String loginName, String password, Observer<Object> subscribers) {
        Observable<Object> observable = NetworkClient.getInstance().mService
                .login(loginName, password)
                .map(new NetworkResultFun<>());
        toSubscribe(observable, subscribers);
    }

}
