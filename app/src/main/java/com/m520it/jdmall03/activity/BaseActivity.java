package com.m520it.jdmall03.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.m520it.jdmall03.controller.BaseController;
import com.m520it.jdmall03.listener.IModelChangeListener;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class BaseActivity extends AppCompatActivity implements IModelChangeListener {

    protected BaseController mController;

    public static class BaseHandler extends Handler{

        WeakReference<BaseActivity> reference;

        public BaseHandler(BaseActivity activity){
            reference=new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (reference!=null&&reference.get()!=null){
                reference.get().handleUI(msg);
            }
        }
    }

    protected BaseHandler mHandler=new BaseHandler(this);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    protected void handleUI(Message msg){
        //default empty implements
    }

    protected void initData(){
        //default empty implements
    }

    protected void initView() {
       //default empty implements
    }

    protected void initController() {
        //default empty implements
    }


    @Override
    public void onModelChanged(int action, Object returnBean) {
        //mHandler.sendMessage(msg);
        mHandler.obtainMessage(action,returnBean).sendToTarget();
    }

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void log(String msg){
        Log.v("m520it",msg);
    }

    public void goBack(View v){
        finish();
    }

}
