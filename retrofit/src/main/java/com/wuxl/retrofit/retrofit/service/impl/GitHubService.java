package com.wuxl.retrofit.retrofit.service.impl;

import com.wuxl.retrofit.retrofit.GovEntity;
import com.wuxl.retrofit.retrofit.http.BaseHttpService;
import com.wuxl.retrofit.retrofit.http.JsonrpcCallBack;
import com.wuxl.retrofit.retrofit.service.IGitHubService;

import java.util.List;

/**
 * Created by WUXL on 2016/8/17.
 */
public class GitHubService extends BaseHttpService {

    public GitHubService() {

    }

    public void queryLatestPhotoNews(Integer param, JsonrpcCallBack<List<GovEntity>> cusCallBack){
        requestRpcList(IGitHubService.class,"queryLatestPhotoNews",param,GovEntity.class,cusCallBack);
    }
}
