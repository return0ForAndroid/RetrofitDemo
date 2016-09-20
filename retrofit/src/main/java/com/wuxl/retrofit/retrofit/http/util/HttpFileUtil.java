package com.wuxl.retrofit.retrofit.http.util;

import android.text.TextUtils;
import android.util.Log;

import com.wuxl.retrofit.retrofit.http.JsonrpcCallBack;
import com.wuxl.retrofit.retrofit.http.result.FileResult;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by WUXL on 2016/9/19.
 */
public class HttpFileUtil extends HttpBaseUtil {

    private static HttpFileUtil httpUtil = null;

    private HttpFileUtil() {
        super();
    }

    public static synchronized HttpFileUtil getInstance() {
        if (httpUtil == null) {
            httpUtil = new HttpFileUtil();
        }
        return httpUtil;
    }

    public <V> void requestFile(String basrUrl, Class<V> iserviceCls, String methodName, final File file, final JsonrpcCallBack cusCallBack) {
        try {
            List<File> fileList = new ArrayList<>();
            fileList.add(file);
            requestFiles(basrUrl, iserviceCls, methodName, fileList, cusCallBack);
        } catch (Exception e) {
            e.printStackTrace();
            cusCallBack.onError("", e.getMessage());
        }
    }

    public <V> void requestFiles(String baseUrl, Class<V> iserviceCls, String methodName, final List<File> fileList, final JsonrpcCallBack cusCallBack) {
        try {
            V iService = HttpJsonUtil.getInstance().getService(baseUrl, iserviceCls);
            Method method = iserviceCls.getMethod(methodName, MultipartBody.class);
            Observable<FileResult> repos = (Observable<FileResult>) method.invoke(iService, filesToMultipartBody(fileList));
            parserFileResult(repos, cusCallBack);
        } catch (Exception e) {
            e.printStackTrace();
            cusCallBack.onError("", e.getMessage());
        }
    }

    private void parserFileResult(final Observable<FileResult> repos, final JsonrpcCallBack cusCallBack) {
        repos.subscribeOn(Schedulers.newThread())//这里需要注意的是，网络请求在非ui线程。如果返回结果是依赖于Rxjava的，则需要变换线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FileResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("parserFileResult", "parserFileResult" + e.getMessage());
                        if (cusCallBack != null) {
                            cusCallBack.onError("", e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(FileResult fileResult) {
                        Log.i("parserFileResult", "parserFileResult11");
                        if (cusCallBack != null) {
                            //判断返回结果
                            if (!TextUtils.isEmpty(fileResult.getCode())) {
                                if (fileResult.getCode().equals("0")) {
                                    //成功
                                    cusCallBack.onSuccess(fileResult.getFileUrlInfos());
                                } else {
                                    //失败
                                    cusCallBack.onError(fileResult.getCode(), fileResult.getMessage());
                                }
                            } else {
                                cusCallBack.onError("", "");
                            }
                        }
                    }
                });
    }

    private MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MultipartBody.FORM, file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }


}
