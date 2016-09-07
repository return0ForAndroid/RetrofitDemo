package com.wuxl.retrofit.retrofit.http;

/**
 * 请求结果回调
 * Created by WUXL on 2016/8/16.
 */
public abstract class JsonrpcCallBack<T> {
    public void onSuccess(T t) {
    }

    public void onError(String errorCode,String errorMessage) {
    }

    public void onNoResult() {
    }

    public void onNetExc() {
    }
}
