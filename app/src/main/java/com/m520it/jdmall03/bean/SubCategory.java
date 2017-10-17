package com.m520it.jdmall03.bean;

/**
 * Created by Administrator on 2017/8/6 0006.
 *  二级分类
 */
public class SubCategory {

    private long id;
    private String name;
    private String thirdCategory;//三级分类的JSON数组

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThirdCategory() {
        return thirdCategory;
    }

    public void setThirdCategory(String thirdCategory) {
        this.thirdCategory = thirdCategory;
    }
}
