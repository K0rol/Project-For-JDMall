package com.m520it.jdmall03.controller;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.OrderDetailsBean;
import com.m520it.jdmall03.bean.OrderList;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.NetworkConst;
import com.m520it.jdmall03.constant.OrderStatus;
import com.m520it.jdmall03.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/13 0013.
 */

public class OrderController extends BaseController {

    public OrderController(Context context) {
        super(context);
    }

    @Override
    public void handleMessage(int action, Object... values) {
        switch (action) {
            case IDiyMessage.ORDER_LIST_ACTION:
                onModelChanged(action, loadOrderList((Long) values[0], (Integer) values[1]));
                break;
            case IDiyMessage.ORDER_DETAILS_ACTION:
                onModelChanged(action, loadOrderDetails((Long) values[0], (Long) values[1]));
                break;
            case IDiyMessage.CONFIRM_RECEIVER_ORDER_ACTION:
                onModelChanged(action, confirmReceiveOrder((Long) values[0], (Long) values[1]));
                break;
            case IDiyMessage.CANCEL_ORDER:
                onModelChanged(action, cancelOrder((Long) values[0], (Long) values[1]));
                break;
        }
    }

    private Result cancelOrder(long userId,long oid){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userId", userId + "");
        paramsMap.put("oid", oid + "");
        String jsonStr = NetworkUtil.doPost(NetworkConst.CANCEL_ORDER_URL, paramsMap);
        return JSON.parseObject(jsonStr, Result.class);
    }

    private Result confirmReceiveOrder(long userId, long oid) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userId", userId + "");
        paramsMap.put("oid", oid + "");
        String jsonStr = NetworkUtil.doPost(NetworkConst.CONFIRM_ORDER_URL, paramsMap);
        return JSON.parseObject(jsonStr, Result.class);
    }

    private OrderDetailsBean loadOrderDetails(long userId, long oid) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userId", userId + "");
        paramsMap.put("id", oid + "");
        String jsonStr = NetworkUtil.doPost(NetworkConst.ORDER_DETAILS_URL, paramsMap);
        Log.i("lean", jsonStr);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()) {
            return JSON.parseObject(resultBean.getResult(), OrderDetailsBean.class);
        }
        return null;
    }

    private List<OrderList> loadOrderList(long userId, int orderstatus) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userId", userId + "");
        if (orderstatus != OrderStatus.ALL_ORDER) {
            paramsMap.put("status", orderstatus + "");
        }
        String jsonStr = NetworkUtil.doPost(NetworkConst.ORDER_LIST_URL, paramsMap);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()) {
            return JSON.parseArray(resultBean.getResult(), OrderList.class);
        }
        return new ArrayList<OrderList>();
    }

}
