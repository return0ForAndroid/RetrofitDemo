package com.wuxl.retrofit.retrofit.service;

import com.wuxl.retrofit.retrofit.GovEntity;
import com.wuxl.retrofit.retrofit.http.result.HttpResultRpc;
import com.wuxl.retrofit.retrofit.http.request.JsonrpcParam;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by WUXL on 2016/8/15.
 */
public interface IGitHubService {
    @POST("INewsArticleSvc.json")
    public Observable<HttpResultRpc<GovEntity>> queryLatestPhotoNews(@Body JsonrpcParam jsonrpcParam);
}
