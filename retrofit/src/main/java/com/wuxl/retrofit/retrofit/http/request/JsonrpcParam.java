package com.wuxl.retrofit.retrofit.http.request;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求参数
 * Created by WUXL on 2016/8/15.
 */
public class JsonrpcParam<T> {

    private static final String REQUEST_JSONRPC = "2.0";
    private static final int REQUEST_ID = 1;

    private String jsonrpc;
    private Integer id;
    private String method;
    private List<T> params;

    private JsonrpcParam() {
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<T> getParams() {
        return params;
    }

    public void setParams(List<T> params) {
        this.params = params;
    }

    public static <T> JsonrpcParam getRequestParam(String methodName, T param) {
        JsonrpcParam<T> jsonrpcParam = new JsonrpcParam<T>();
        List<T> intList = new ArrayList<T>();
        intList.add(param);
        jsonrpcParam.setJsonrpc(REQUEST_JSONRPC);
        jsonrpcParam.setId(REQUEST_ID);
        jsonrpcParam.setMethod(methodName);
        jsonrpcParam.setParams(intList);
        return jsonrpcParam;
    }
}
