package com.m520it.jdmall03.bean;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class PayInfo {
    private String oinfo;//JD+订单号
    private String tn;//流水号
    private String pname;//京东商品
    private String payTime;//购买时间
    private double totalPrice;//总金额

    public String getOinfo() {
        return oinfo;
    }

    public void setOinfo(String oinfo) {
        this.oinfo = oinfo;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
