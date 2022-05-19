package com.bobo.baseframe.network;

/**
 * @ClassName ApiException
 * @Description 异常数据实体类
 * @Date 2018/6/15
 * @History 2018/6/15 author: description:
 */
public class ApiException extends RuntimeException {

    public int resultCode;

    public ApiException(int resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }
}
