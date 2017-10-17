package com.m520it.jdmall03.controller;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.RAddOrder;
import com.m520it.jdmall03.bean.ReceiverAddress;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.bean.SBuildOrderParams;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.NetworkConst;
import com.m520it.jdmall03.utils.NetworkUtil;

import java.util.HashMap;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class BuildOrderController extends BaseController {

    public BuildOrderController(Context context) {
        super(context);
    }

    @Override
    public void handleMessage(int action, Object... values) {
        switch (action) {
            case IDiyMessage.DEFAULT_RECEIVER_ACTION:
                onModelChanged(action,defaultReceiver((Long)values[0]));
                break;
            case IDiyMessage.ADD_ORDER_ACTION:
                onModelChanged(action,buildOrder((SBuildOrderParams)values[0]));
                break;
            case IDiyMessage.CANCEL_ORDER:
                onModelChanged(action,cancelOrder((Long)values[0],(Long)values[1]));
                break;
        }
    }

    private Result cancelOrder(long userId,long oid){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userId",userId+"");
        paramsMap.put("oid",oid+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.CANCEL_ORDER_URL, paramsMap);
        return JSON.parseObject(jsonStr, Result.class);
    }

    /**
     * 创建了一笔订单
     * @param paramsBean
     * @return  maybu null
     */
    private RAddOrder buildOrder(SBuildOrderParams paramsBean){
        HashMap<String, String> paramsMap = new HashMap<>();
        String paramsJson = JSON.toJSONString(paramsBean);
        Log.i("lean",paramsJson);
        paramsMap.put("detail", paramsJson);
        String jsonStr = NetworkUtil.doPost(NetworkConst.ADD_ORDER_URL, paramsMap);
        Result result = JSON.parseObject(jsonStr, Result.class);
        if (result.isSuccess()){
            return JSON.parseObject(result.getResult(), RAddOrder.class);
        }
        return null;
    }

    private ReceiverAddress defaultReceiver(long userId){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userId", userId+"");
        paramsMap.put("isDefault", "true");
        String jsonStr = NetworkUtil.doPost(NetworkConst.ADDRESS_LIST_ACTION, paramsMap);
        Result result = parseObject(jsonStr, Result.class);
        if (result.isSuccess()){
            List<ReceiverAddress> datas = JSON.parseArray(result.getResult(), ReceiverAddress.class);
            if (datas.size()>0){
                return datas.get(0);
            }
        }
        return null;
    }
}
