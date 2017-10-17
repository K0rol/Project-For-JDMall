package com.m520it.jdmall03.bean;

/**
 * Created by Administrator on 2017/8/4 0004.
 *
 *  秒杀商品
 */
public class SecKillBean {

    private long productId;
    private String iconUrl;
    private double pointPrice;
    private double allPrice;

    private String timeLeft;//秒杀剩余时间（分钟）
    private int type;//秒杀类型（1抢年货，2超值，3热卖）

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(double allPrice) {
        this.allPrice = allPrice;
    }

    public double getPointPrice() {
        return pointPrice;
    }

    public void setPointPrice(double pointPrice) {
        this.pointPrice = pointPrice;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SecKillBean{" +
                "productId=" + productId +
                ", iconUrl='" + iconUrl + '\'' +
                ", pointPrice=" + pointPrice +
                ", allPrice=" + allPrice +
                ", timeLeft='" + timeLeft + '\'' +
                ", type=" + type +
                '}';
    }
}
