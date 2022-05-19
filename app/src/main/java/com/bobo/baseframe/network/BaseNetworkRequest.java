package com.bobo.baseframe.network;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import rx.functions.Func1;


/**
 * @ClassName BaseNetworkRequest
 * @Description 网络请求基础类
 */
public class BaseNetworkRequest {

    /**
     * 根据返回的状态,抛出异常或将返回整个NetworkResult<T>
     *
     * @param <T> 具体业务所需的数据类型
     */
    public class NetworkResultFun<T> implements Function<NetworkResult<T>, T> {

        @Override
        public T apply(@NonNull NetworkResult<T> tNetworkResult) throws Exception {
            if (tNetworkResult.getAckCode() != NetworkCode.CODE_SUCCESS_CRITICAL && tNetworkResult.getAckCode() != NetworkCode.CODE_SUCCESS) {
                throw new ApiException(tNetworkResult.getAckCode(), tNetworkResult.getMessage());
            }

            return tNetworkResult.getData();
        }
    }

    /**
     * 根据返回的状态,抛出异常或将返回整个NetworkResult<T>
     *
     * @param <T> 具体业务所需的数据类型
     */
    public class NetworkResultFun1<T> implements Function<NetworkResult<T>, NetworkResult<T>> {
        @Override
        public NetworkResult<T> apply(@NonNull NetworkResult<T> tNetworkResult) throws Exception {
            if (tNetworkResult.getAckCode() != NetworkCode.CODE_SUCCESS_CRITICAL && tNetworkResult.getAckCode() != NetworkCode.CODE_SUCCESS) {
                throw new ApiException(tNetworkResult.getAckCode(), tNetworkResult.getMessage());
            }

            return tNetworkResult;
        }
    }

    /**
     * 无返回码
     * @param <T> 具体业务所需的数据类型
     */
    public class NetworkResultFun2<T> implements Function<NetworkResult<T>, T> {


        @Override
        public T apply(@NotNull NetworkResult<T> tNetworkResult) throws Exception {
            if (!tNetworkResult.isSuccess()) {
                throw new ApiException(tNetworkResult.getAckCode(), tNetworkResult.getMessage());
            }

            return tNetworkResult.getData();
        }
    }

    /**
     * 切换线程,绑定观察者
     *
     * @param <T> 泛型
     * @param o   被观察者
     * @param s   观察者
     */
    protected static <T> void toSubscribe(Observable<T> o, Observer<T> s) {
        o.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 网络请求线程调动转换器
     *
     * @param <T>
     */
    public class SchedulerTransformer<T> implements ObservableTransformer<T, T> {

        @NonNull
        @Override
        public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
}
