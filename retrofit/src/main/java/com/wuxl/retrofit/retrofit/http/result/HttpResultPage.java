package com.wuxl.retrofit.retrofit.http.result;

import java.util.List;

/**
 * 分页结果
 * Created by WUXL on 2016/8/16.
 */
public class HttpResultPage<E> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer startRow;
    private Long total;
    private Long pages;
    private List<E> list;

    public HttpResultPage() {
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
