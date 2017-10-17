package com.m520it.jdmall03;

import android.app.Application;

import com.m520it.jdmall03.bean.LoginResult;

import org.litepal.LitePal;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public class JDMallApplication extends Application {

    public static LoginResult mUserInfo;

    /**
     *  @return -1 代表拿不到当前登录的用户id
     * */
    public static long getUserId(){
        return mUserInfo!=null?mUserInfo.getId():-1;
    }

    //应用启动的时候调用
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        LitePal.getDatabase();
    }

}
