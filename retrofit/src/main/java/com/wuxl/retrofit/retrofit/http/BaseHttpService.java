package com.wuxl.retrofit.retrofit.http;

import com.wuxl.retrofit.retrofit.http.result.HttpResultPage;
import com.wuxl.retrofit.retrofit.http.util.HttpFileUtil;
import com.wuxl.retrofit.retrofit.http.util.HttpJsonUtil;

import java.io.File;
import java.util.List;

import rx.Observable;

/**
 * 通用http请求
 * Created by WUXL on 2016/8/16.
 */
public class BaseHttpService {

    private String baseUrl = null;

    private BaseHttpService() {
    }

    protected BaseHttpService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    protected <V, T, E> void requestRpcObject(Class<V> iserviceCls, String methodName, T param, final Class<E> resultEntityCls, final JsonrpcCallBack<E> cusCallBack) {
        HttpJsonUtil.getInstance().requestRpc(baseUrl, iserviceCls, methodName, param, resultEntityCls, Object.class, cusCallBack);
    }

    protected <V, T, E> void requestRpcList(Class<V> iserviceCls, String methodName, T param, final Class<E> resultEntityCls, final JsonrpcCallBack<List<E>> cusCallBack) {
        HttpJsonUtil.getInstance().requestRpc(baseUrl, iserviceCls, methodName, param, resultEntityCls, List.class, cusCallBack);
    }

    protected <V, T, E> void requestRpcPage(Class<V> iserviceCls, String methodName, T param, final Class<E> resultEntityCls, final JsonrpcCallBack<HttpResultPage<E>> cusCallBack) {
        HttpJsonUtil.getInstance().requestRpc(baseUrl, iserviceCls, methodName, param, resultEntityCls, HttpResultPage.class, cusCallBack);
    }

    protected <V> void requestFile(Class<V> iserviceCls, String methodName, final File file, final JsonrpcCallBack cusCallBack) {
        try {
            HttpFileUtil.getInstance().requestFile(baseUrl, iserviceCls, methodName, file, cusCallBack);
        } catch (Exception e) {
            e.printStackTrace();
            cusCallBack.onError("", e.getMessage());
        }
    }

    protected <V> void requestFiles(Class<V> iserviceCls, String methodName, final List<File> fileList, final JsonrpcCallBack cusCallBack) {
        try {
            HttpFileUtil.getInstance().requestFiles(baseUrl, iserviceCls, methodName, fileList, cusCallBack);
        } catch (Exception e) {
            e.printStackTrace();
            cusCallBack.onError("", e.getMessage());
        }
    }

}
