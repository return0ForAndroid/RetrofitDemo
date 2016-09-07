package com.wuxl.retrofit.retrofit.http.result;

import java.util.List;

/**
 * 请求code==200结果
 * Created by WUXL on 2016/8/15.
 */
public class HttpResult<E> {

    private String resultCode;
    private String resultMessage;
    private List<E> list;
    private E page;
    private E object;
    private boolean success;

    public HttpResult() {
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public E getPage() {
        return page;
    }

    public void setPage(E page) {
        this.page = page;
    }

    public E getObject() {
        return object;
    }

    public void setObject(E object) {
        this.object = object;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
