package com.wuxl.retrofit.retrofit.http;

import com.wuxl.retrofit.retrofit.http.request.JsonrpcParam;
import com.wuxl.retrofit.retrofit.http.result.HttpResultPage;
import com.wuxl.retrofit.retrofit.http.result.HttpResultRpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 通用http请求
 * Created by WUXL on 2016/8/16.
 */
public class BaseHttpService {

    private static final String URL_BASE = "http://www.bdx-aidp.com:8280/route/jsonrpc/";

    public BaseHttpService() {
    }

    protected <V, T, E> void requestRpcObject(Class<V> iserviceCls, String methodName, T param, final Class<E> resultEntityCls, final JsonrpcCallBack<E> cusCallBack) {
        requestRpc(iserviceCls, methodName, param, resultEntityCls, Object.class, cusCallBack);
    }

    protected <V, T, E> void requestRpcList(Class<V> iserviceCls, String methodName, T param, final Class<E> resultEntityCls, final JsonrpcCallBack<List<E>> cusCallBack) {
        requestRpc(iserviceCls, methodName, param, resultEntityCls, List.class, cusCallBack);
    }

    protected <V, T, E> void requestRpcPage(Class<V> iserviceCls, String methodName, T param, final Class<E> resultEntityCls, final JsonrpcCallBack<HttpResultPage<E>> cusCallBack) {
        requestRpc(iserviceCls, methodName, param, resultEntityCls, HttpResultPage.class, cusCallBack);
    }

    private <V, T, E> void requestRpc(Class<V> iserviceCls, String methodName, T param, final Class<E> resultEntityCls, final Class resultTypeCls, final JsonrpcCallBack cusCallBack) {
        Observable<HttpResultRpc<E>> repos = null;
        try {
            JsonrpcParam<T> jsonrpcParam = JsonrpcParam.getRequestParam(methodName, param);

            V iService = getService(iserviceCls);
            Method method = iserviceCls.getMethod(methodName, JsonrpcParam.class);
            repos = (Observable<HttpResultRpc<E>>) method.invoke(iService, jsonrpcParam);

            parserResult(repos, resultTypeCls, cusCallBack);
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

    private void requestFile(String filePath) {

    }

    private <V> V getService(Class<V> iserviceCls) {
        OkHttpClient okHttpClient = new OkHttpClient();//自定义okhttp
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)//rest api的基路由
                .addConverterFactory(JacksonConverterFactory.create(
                        JacksonParser.getInstance().getObjectMapper()))//解析工厂
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//rxJava 编程
                .build();
        V iService = retrofit.create(iserviceCls);
        return iService;
    }

    private <E> void parserResult(Observable<HttpResultRpc<E>> repos, final Class resultTypeCls, final JsonrpcCallBack cusCallBack) {
        repos.subscribeOn(Schedulers.newThread())//这里需要注意的是，网络请求在非ui线程。如果返回结果是依赖于Rxjava的，则需要变换线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultRpc<E>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        cusCallBack.onError("", e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultRpc<E> httpResultRpc) {
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
                });
    }

}
