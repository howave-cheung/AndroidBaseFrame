package com.bobo.baseframe.network;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * @ClassName NetworkApiService
 * @Description api接口
 */
public interface NetworkApiService {

    /**
     * 登陆
     * {designer}
     * @Path("designer") String approval
     */
    @FormUrlEncoded
    @POST("/Login/login")
    Observable<NetworkResult<Object>> login(@Field("loginName") String loginName, @Field("password") String password);

}
