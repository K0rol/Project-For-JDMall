package com.m520it.jdmall03.bean;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class SAddAddressParams {

    public long userId;
    public String name;
    public String phone;
    public String provinceCode;
    public String cityCode;
    public String distCode;
    public String addressDetails;
    public boolean isDefault;

    public SAddAddressParams(long userId, String name, String phone,
                             String provinceCode, String cityCode, String distCode,
                             String addressDetails, boolean isDefault) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.provinceCode = provinceCode;
        this.cityCode = cityCode;
        this.distCode = distCode;
        this.addressDetails = addressDetails;
        this.isDefault = isDefault;
    }
}
