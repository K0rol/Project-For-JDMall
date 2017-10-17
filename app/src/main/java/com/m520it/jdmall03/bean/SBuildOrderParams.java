package com.m520it.jdmall03.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/15 0015.
 * 相互嵌套的java对象中 fastJson解释的方式是:
 * jsonStr--->Java对象(里面还有子对象)===>一层一层的去解析
 * Java对象---->jsonStr===>直接转换就可以了
 */
public class SBuildOrderParams {

    private ArrayList<OrderProduct> products;//JSON数组
    private long addrId;
    private int payWay;//0在线支付 1货到付款
    private long userId;

    public SBuildOrderParams() {
    }

    public SBuildOrderParams(ArrayList<OrderProduct> products, long addrId,
                             int payWay, long userId) {
        this.products = products;
        this.addrId = addrId;
        this.payWay = payWay;
        this.userId = userId;
    }

    public ArrayList<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<OrderProduct> products) {
        this.products = products;
    }

    public long getAddrId() {
        return addrId;
    }

    public void setAddrId(long addrId) {
        this.addrId = addrId;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static class OrderProduct {

        private int buyCount;
        private String type;//商品型号
        private long pid;

        public int getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(int buyCount) {
            this.buyCount = buyCount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getPid() {
            return pid;
        }

        public void setPid(long pid) {
            this.pid = pid;
        }
    }


}
