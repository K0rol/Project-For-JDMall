package com.m520it.jdmall03.mvp.model;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.constant.NetworkConst;
import com.m520it.jdmall03.utils.NetworkUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class ResetPwdModelImp implements IResetPwdModel {

    private IPwdResetListener mListener;

    public ResetPwdModelImp(IPwdResetListener listener) {
        this.mListener=listener;
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (mListener!=null){
                mListener.onPwdReseted((Result) msg.obj);
            }
        }
    };

    @Override
    public void resetPwd(final String name) {
        new Thread(){
            @Override
            public void run() {
                HashMap<String, String> paramsMap=new HashMap<String, String>();
                paramsMap.put("username",name);
                String jsonStr = NetworkUtil.doPost(NetworkConst.RESET_URL, paramsMap);
                Result resultBean = JSON.parseObject(jsonStr, Result.class);
                mHandler.obtainMessage(0,resultBean).sendToTarget();
            }
        }.start();
    }


}
