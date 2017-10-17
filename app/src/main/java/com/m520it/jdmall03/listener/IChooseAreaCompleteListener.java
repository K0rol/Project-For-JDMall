package com.m520it.jdmall03.listener;

import com.m520it.jdmall03.controller.AddressController;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public interface IChooseAreaCompleteListener {

    void onAreaChosen(AddressController.Area tabProvince,AddressController.Area tabCity,
                       AddressController.Area tabArea);
}
