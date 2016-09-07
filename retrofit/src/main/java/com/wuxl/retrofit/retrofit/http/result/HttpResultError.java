package com.wuxl.retrofit.retrofit.http.result;

/**
 * 请求错误结果
 * Created by WUXL on 2016/8/16.
 */
public class HttpResultError {

    private String code;
    private String message;

    public HttpResultError() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
