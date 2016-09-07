package com.wuxl.retrofit.retrofit.http.result;

/**
 * 请求结果（可是成功可是失败）
 * Created by WUXL on 2016/8/16.
 */
public class HttpResultRpc<E> {

    private String jsonrpc;

    private Integer id;

    private HttpResult<E> result;

    private HttpResultError error;

    public HttpResultRpc() {
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


    public HttpResultError getError() {
        return error;
    }

    public void setError(HttpResultError error) {
        this.error = error;
    }

    public HttpResult<E> getResult() {
        return result;
    }

    public void setResult(HttpResult<E> result) {
        this.result = result;
    }
}
