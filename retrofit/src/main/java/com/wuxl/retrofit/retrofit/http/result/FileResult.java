package com.wuxl.retrofit.retrofit.http.result;

import java.util.List;

/**
 * Created by WUXL on 2016/9/19.
 */
public class FileResult {

    private String code;
    private String message;
    private List<FileResultUrl> fileUrlInfos;

    public FileResult() {
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

    public List<FileResultUrl> getFileUrlInfos() {
        return fileUrlInfos;
    }

    public void setFileUrlInfos(List<FileResultUrl> fileUrlInfos) {
        this.fileUrlInfos = fileUrlInfos;
    }
}
