package com.m520it.jdmall03.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.PayInfo;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.bean.SMockPayParams;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.NetworkConst;
import com.m520it.jdmall03.utils.NetworkUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class AlipayController extends BaseController {

    public AlipayController(Context context) {
        super(context);
    }

    @Override
    public void handleMessage(int action, Object... values) {
        switch (action) {
            case IDiyMessage.GET_PAYINFO_ACTION:
                onModelChanged(action,getPayInfo((String)values[0],(Long)values[1]));
                break;
            case IDiyMessage.MOCK_PAY_ACTION:
                onModelChanged(action,mockPay((SMockPayParams)values[0]));
                break;
        }
    }

    private Result mockPay(SMockPayParams paramsBean){
        HashMap<String, String> paramsMap=new HashMap<>();
        paramsMap.put("account",paramsBean.account);
        paramsMap.put("apwd",paramsBean.pwd);
        paramsMap.put("ppwd",paramsBean.payPwd);
        paramsMap.put("tn",paramsBean.tn);
        paramsMap.put("userId",paramsBean.userId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.MOCK_PAY_URL, paramsMap);
        return JSON.parseObject(jsonStr, Result.class);
    }

    /**
     * @param tn    流水号
     * @param userId
     * @return  may be null
     */
    private PayInfo getPayInfo(String tn, long userId){
        HashMap<String, String> paramsMap=new HashMap<>();
        paramsMap.put("tn",tn);
        paramsMap.put("userId",userId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.GET_PAYINFO_URL, paramsMap);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()){
            return JSON.parseObject(resultBean.getResult(), PayInfo.class);
        }
        return null;
    }
}
