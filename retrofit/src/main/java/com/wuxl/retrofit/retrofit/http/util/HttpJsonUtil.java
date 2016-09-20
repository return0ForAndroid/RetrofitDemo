package com.wuxl.retrofit.retrofit.http.util;

import com.wuxl.retrofit.retrofit.http.JsonrpcCallBack;
import com.wuxl.retrofit.retrofit.http.request.JsonrpcParam;
import com.wuxl.retrofit.retrofit.http.result.HttpResultPage;
import com.wuxl.retrofit.retrofit.http.result.HttpResultRpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by WUXL on 2016/9/19.
 */
public class HttpJsonUtil extends HttpBaseUtil{

    private static HttpJsonUtil httpUtil=null;

    private HttpJsonUtil(){
        super();
    }

    public static synchronized HttpJsonUtil getInstance(){
        if (httpUtil==null){
            httpUtil=new HttpJsonUtil();
        }
        return httpUtil;
    }

    public <V, T, E> void requestRpc(String baseUrl,Class<V> iserviceCls, String methodName, T param, final Class<E> resultEntityCls, final Class resultTypeCls, final JsonrpcCallBack cusCallBack) {
        try {
            JsonrpcParam<T> jsonrpcParam = JsonrpcParam.getRequestParam(methodName, param);

            V iService = HttpJsonUtil.getInstance().getService(baseUrl,iserviceCls);
            Method method = iserviceCls.getMethod(methodName, JsonrpcParam.class);
            Observable<HttpResultRpc<E>> repos = (Observable<HttpResultRpc<E>>) method.invoke(iService, jsonrpcParam);

            parserJsonResult(repos, resultTypeCls, cusCallBack);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            cusCallBack.onError("", e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            cusCallBack.onError("", e.getMessage());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            cusCallBack.onError("", e.getMessage());
        }
    }

    private  <E> void parserJsonResult(Observable<HttpResultRpc<E>> repos, final Class resultTypeCls, final JsonrpcCallBack cusCallBack) {
        repos.subscribeOn(Schedulers.newThread())//这里需要注意的是，网络请求在非ui线程。如果返回结果是依赖于Rxjava的，则需要变换线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultRpc<E>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (cusCallBack != null) {
                            cusCallBack.onError("", e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(HttpResultRpc<E> httpResultRpc) {
                        if (cusCallBack != null) {
                            if (httpResultRpc.getError() != null) {
                                cusCallBack.onError(httpResultRpc.getError().getCode(), httpResultRpc.getError().getCode());
                            } else if (httpResultRpc.getResult() != null) {
                                if (httpResultRpc.getResult().isSuccess()) {
                                    if (resultTypeCls.equals(List.class)) {
                                        //返回list
                                        cusCallBack.onSuccess(httpResultRpc.getResult().getList());
                                    } else if (resultTypeCls.equals(HttpResultPage.class)) {
                                        //返回page
                                        cusCallBack.onSuccess(httpResultRpc.getResult().getPage());
                                    } else {
                                        //返回object
                                        cusCallBack.onSuccess(httpResultRpc.getResult().getObject());
                                    }
                                } else {
                                    //失败
                                    cusCallBack.onError(httpResultRpc.getResult().getResultCode(), httpResultRpc.getResult().getResultMessage());
                                }
                            }
                        }
                    }
                });
    }
}
