package com.wuxl.retrofit.service;

import com.wuxl.retrofit.entity.NewsEntity;
import com.wuxl.retrofit.retrofit.http.request.JsonrpcParam;
import com.wuxl.retrofit.retrofit.http.result.HttpResultRpc;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by WUXL on 2016/8/15.
 */
public interface IJsonService {
    @POST("/route/jsonrpc/INewsArticleSvc.json")
    public Observable<HttpResultRpc<NewsEntity>> queryLatestPhotoNews(@Body JsonrpcParam jsonrpcParam);
}
