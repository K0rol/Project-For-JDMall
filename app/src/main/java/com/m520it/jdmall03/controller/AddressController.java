package com.m520it.jdmall03.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.ReceiverAddress;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.bean.SAddAddressParams;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.NetworkConst;
import com.m520it.jdmall03.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class AddressController extends BaseController {

    public AddressController(Context context) {
        super(context);
    }

    @Override
    public void handleMessage(int action, Object... values) {
        switch (action) {
            case IDiyMessage.ADDRESS_LIST_ACTION:
                onModelChanged(action,loadAddressList((Long)values[0]));
                break;
            case IDiyMessage.DEL_ADDRESS_ACTION:
                onModelChanged(action,delAddress((Long)values[0],(Long)values[1]));
                break;
            case IDiyMessage.PROVINCE_ACTION:
                onModelChanged(action,loadProvince());
                break;
            case IDiyMessage.CITY_ACTION:
                onModelChanged(action,loadCity((String)values[0]));
                break;
            case IDiyMessage.AREA_ACTION:
                onModelChanged(action,loadArea((String)values[0]));
                break;
            case IDiyMessage.ADD_ADDRESS_ACTION:
                onModelChanged(action,addAddress((SAddAddressParams)values[0]));
                break;
        }
    }

    private Result addAddress(SAddAddressParams paramsBean){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userId", paramsBean.userId+"");
        paramsMap.put("name", paramsBean.name);
        paramsMap.put("phone", paramsBean.phone);
        paramsMap.put("provinceCode", paramsBean.provinceCode);
        paramsMap.put("cityCode", paramsBean.cityCode);
        paramsMap.put("distCode", paramsBean.distCode);
        paramsMap.put("addressDetails", paramsBean.addressDetails);
        paramsMap.put("isDefault", paramsBean.isDefault+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.ADD_ADDRESS_URL, paramsMap);
        return JSON.parseObject(jsonStr, Result.class);
    }

    private List<Area> loadArea(String cityCode) {
        String jsonStr = NetworkUtil.doGet(NetworkConst.AREA_URL+"?fcode="+cityCode);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(), Area.class);
        }
        return new ArrayList<>();
    }

    private List<Area> loadCity(String provinceCode) {
        String jsonStr = NetworkUtil.doGet(NetworkConst.CITY_URL+"?fcode="+provinceCode);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(), Area.class);
        }
        return new ArrayList<>();
    }

    private List<Area> loadProvince(){
        String jsonStr = NetworkUtil.doGet(NetworkConst.PROVINCE_URL);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(), Area.class);
        }
        return new ArrayList<>();
    }

    public static class Area{

        private String name;
        private String code;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    private Result delAddress(long userId,long addressId){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("id", addressId+"");
        paramsMap.put("userId", userId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.DEL_ADDRESS_ACTION, paramsMap);
        return JSON.parseObject(jsonStr, Result.class);
    }

    private List<ReceiverAddress> loadAddressList(long userId){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userId", userId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.ADDRESS_LIST_ACTION, paramsMap);
        Result result = parseObject(jsonStr, Result.class);
        if (result.isSuccess()){
            return JSON.parseArray(result.getResult(),ReceiverAddress.class);
        }
        return new ArrayList<>();
    }
}
