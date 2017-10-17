package com.m520it.jdmall03.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.bean.Shopcar;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.NetworkConst;
import com.m520it.jdmall03.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class ShopcarController extends BaseController {

    public ShopcarController(Context context) {
        super(context);
    }

    @Override
    public void handleMessage(int action, Object... values) {
        switch (action) {
            case IDiyMessage.SHOPCAR_LIST_ACTION:
                onModelChanged(action,loadShopcar((Long)values[0]));
                break;
            case IDiyMessage.DEL_SHOPCAR_ITEM:
                Result ifDelShopcar = delShopcar((Long) values[0], (Long) values[1]);
                onModelChanged(action,ifDelShopcar);
                break;
        }
    }

    private Result  delShopcar(long userId,long shopcarId){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userId", userId+"");
        paramsMap.put("id", shopcarId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.DEL_SHOPCAR_ACTION, paramsMap);
        return JSON.parseObject(jsonStr, Result.class);
    }

    private List<Shopcar> loadShopcar(long userId){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userId", userId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.SHOPCAR_LIST_ACTION, paramsMap);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()){
            return  JSON.parseArray(resultBean.getResult(),Shopcar.class);
        }
        return new ArrayList<>();
    }


}
