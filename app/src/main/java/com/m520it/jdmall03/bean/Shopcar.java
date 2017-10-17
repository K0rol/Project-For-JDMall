package com.m520it.jdmall03.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class Shopcar implements Serializable{

    private long id;//购物车明细id
    private long pid;//商品id
    private String pimageUrl;
    private String pname;
    private double pprice;
    private int buyCount;
    private String pversion;

    private long storeId;//商店id
    private String storeName;
    private int stockCount;
    public boolean isChecked;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPimageUrl() {
        return pimageUrl;
    }

    public void setPimageUrl(String pimageUrl) {
        this.pimageUrl = pimageUrl;
    }

    public String getPversion() {
        return pversion;
    }

    public void setPversion(String pversion) {
        this.pversion = pversion;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public double getPprice() {
        return pprice;
    }

    public void setPprice(double pprice) {
        this.pprice = pprice;
    }

    @Override
    public String toString() {
        return "Shopcar{" +
                "id=" + id +
                ", pid=" + pid +
                ", pimageUrl='" + pimageUrl + '\'' +
                ", pname='" + pname + '\'' +
                ", pprice=" + pprice +
                ", buyCount=" + buyCount +
                ", pversion='" + pversion + '\'' +
                ", storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", stockCount=" + stockCount +
                '}';
    }
}
