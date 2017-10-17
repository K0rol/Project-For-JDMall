package com.m520it.jdmall03.bean;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class OrderProductBean {

    private double amount;
    private String piconUrl;
    private String pname;
    private String version;
    private Long pid;
    private int buyCount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPiconUrl() {
        return piconUrl;
    }

    public void setPiconUrl(String piconUrl) {
        this.piconUrl = piconUrl;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }
}
