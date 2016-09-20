package com.wuxl.retrofit.retrofit.http.result;

/**
 * Created by WUXL on 2016/9/19.
 */
public class FileResultUrl {
    private String fileType;
    private String name;
    private String accessKey;
    private String size;
    private String accessUrl;

    public FileResultUrl() {
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    @Override
    public String toString() {
        return getAccessUrl();
    }
}
