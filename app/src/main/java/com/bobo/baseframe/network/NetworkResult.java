package com.bobo.baseframe.network;

import com.google.gson.annotations.SerializedName;

/**
 * @ClassName NetworkResult
 * @Description 服务器返回的数据
 */
public class NetworkResult<T> {

    private String message;
    private boolean success;

    // SerializedName 是可以解析多字段的
    @SerializedName(value = "ackCode", alternate = "code")
    private int ackCode;

    private int count;
    private int total;

    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAckCode() {
        return ackCode;
    }

    public void setAckCode(int ackCode) {
        this.ackCode = ackCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
