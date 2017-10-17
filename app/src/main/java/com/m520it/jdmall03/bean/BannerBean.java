package com.m520it.jdmall03.bean;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class BannerBean {

    private long id;
    private String adUrl;//图片路径
    private int type;//跳转类型（1跳转到网页，2跳转到商品详情，3跳转到分类去）
    private String webUrl;//如果是跳转网页类型，则返回网页地址
    private int adKind;//广告类型（1为导航banner，2为广告banner）

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public int getAdKind() {
        return adKind;
    }

    public void setAdKind(int adKind) {
        this.adKind = adKind;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "id=" + id +
                ", adUrl='" + adUrl + '\'' +
                ", type=" + type +
                ", webUrl='" + webUrl + '\'' +
                ", adKind=" + adKind +
                '}';
    }
}
