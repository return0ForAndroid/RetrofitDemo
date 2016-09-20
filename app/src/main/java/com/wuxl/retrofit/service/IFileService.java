package com.wuxl.retrofit.service;

import com.wuxl.retrofit.retrofit.http.result.FileResult;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 文件服务
 * Created by WUXL on 2016/9/19.
 */
public interface IFileService {

    @POST("/route/file/upload?fileServer=1")
    public Observable<FileResult> uploadFile(@Body MultipartBody multipartBody);
}
