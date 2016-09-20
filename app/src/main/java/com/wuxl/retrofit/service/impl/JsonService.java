package com.wuxl.retrofit.service.impl;

import com.wuxl.retrofit.entity.NewsEntity;
import com.wuxl.retrofit.retrofit.http.BaseHttpService;
import com.wuxl.retrofit.retrofit.http.JsonrpcCallBack;
import com.wuxl.retrofit.service.IJsonService;

import java.util.List;

/**
 * Created by WUXL on 2016/8/17.
 */
public class JsonService extends BaseHttpService {

    public JsonService() {
        super("http://www.bdx-aidp.com:8280");
    }

    public void queryLatestPhotoNews(Integer param, JsonrpcCallBack<List<NewsEntity>> cusCallBack){
        requestRpcList(IJsonService.class,"queryLatestPhotoNews",param,NewsEntity.class,cusCallBack);
    }

}
