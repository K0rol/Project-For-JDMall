package com.m520it.jdmall03.bean;

import com.m520it.jdmall03.controller.AddressController;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class ChosenArea {

    public AddressController.Area tabProvince;
    public AddressController.Area tabCity;
    public AddressController.Area tabArea;

    public ChosenArea(AddressController.Area tabProvince, AddressController.Area tabCity, AddressController.Area tabArea) {
        this.tabProvince = tabProvince;
        this.tabCity = tabCity;
        this.tabArea = tabArea;
    }
}
