package com.m520it.jdmall03.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class Account extends DataSupport {

    private String name;
    private String pwd;

    public Account() {
    }

    public Account(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
