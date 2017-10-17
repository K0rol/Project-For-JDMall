package com.m520it.jdmall03.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class ActivityUtil {

    public static void startNew(Context c, Class<? extends AppCompatActivity> clazz,boolean ifFinish){
        Intent intent=new Intent(c,clazz);
        c.startActivity(intent);
        if (ifFinish){
            ((Activity)c).finish();
        }
    }

}
