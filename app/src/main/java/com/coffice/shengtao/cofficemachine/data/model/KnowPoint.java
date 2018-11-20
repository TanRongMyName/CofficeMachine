package com.coffice.shengtao.cofficemachine.data.model;

/**
 * 知识点   用来 Recycleview 显示数据的
 */
public class KnowPoint {
    private String title;
    private String discript;
    private String url;
    private Class<?> cls;//activity 要跳转的class



    public KnowPoint(String title, String discript, String url) {
        this.title = title;
        this.discript = discript;
        this.url = url;
    }

    public KnowPoint(String title, String discript, String url, Class<?> cls) {
        this.title = title;
        this.discript = discript;
        this.url = url;
        this.cls = cls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscript() {
        return discript;
    }

    public void setDiscript(String discript) {
        this.discript = discript;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }
}
