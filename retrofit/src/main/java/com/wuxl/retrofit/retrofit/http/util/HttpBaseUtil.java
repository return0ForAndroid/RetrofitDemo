package com.wuxl.retrofit.retrofit.http.util;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by WUXL on 2016/9/20.
 */
public class HttpBaseUtil {

    protected HttpBaseUtil(){}

    protected <V> V getService(String baseUrl,Class<V> iserviceCls) {
        OkHttpClient okHttpClient = new OkHttpClient();//自定义okhttp
        okHttpClient.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.i("intercept", "intercept=" + request.url());
                return null;
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)//rest api的基路由
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create(
                        JacksonParser.getInstance().getObjectMapper()))//解析工厂
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//rxJava 编程
                .build();
        V iService = retrofit.create(iserviceCls);
        return iService;
    }
}
