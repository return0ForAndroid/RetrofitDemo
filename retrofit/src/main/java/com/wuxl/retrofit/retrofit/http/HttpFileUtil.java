package com.wuxl.retrofit.retrofit.http;

import java.io.File;
import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by WUXL on 2016/8/19.
 */
public interface HttpFileUtil {

    @POST("/route/file/upload?fileServer=1")
    public void UploadFile(@PartMap Map<String,File> fileMap);
}
