package com.wuxl.retrofit.retrofit;

import java.util.Date;

/**
 * Created by WUXL on 2016/8/15.
 */
public class GovEntity {
    private String title;
    private String firstImgUrl;
    private String articleUrl;
    private Date publishTime;

    public GovEntity() {
    }

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstImgUrl() {
        return firstImgUrl;
    }

    public void setFirstImgUrl(String firstImgUrl) {
        this.firstImgUrl = firstImgUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
