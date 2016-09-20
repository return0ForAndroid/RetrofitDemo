package com.wuxl.retrofit.service.impl;

import com.wuxl.retrofit.retrofit.http.BaseHttpService;
import com.wuxl.retrofit.retrofit.http.JsonrpcCallBack;
import com.wuxl.retrofit.retrofit.http.util.BitmapUtil;
import com.wuxl.retrofit.service.IFileService;

import java.io.File;


/**
 * Created by WUXL on 2016/9/20.
 */
public class FileService extends BaseHttpService{

    public FileService() {
        super("http://www.bdx-aidp.com:8280");
    }

    public void uploadPic(String imgPath, JsonrpcCallBack callBack) {
        File file=BitmapUtil.compressBmpToFile(imgPath);
        requestFile(IFileService.class,"uploadFile",file,callBack);
    }

    public void uploadFile(String filePath, JsonrpcCallBack callBack) {
        File file=new File(filePath);
        requestFile(IFileService.class,"uploadFile",file,callBack);
    }
}
