package com.m520it.jdmall03.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.m520it.jdmall03.controller.BaseController;
import com.m520it.jdmall03.listener.IModelChangeListener;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public abstract class BaseFragment extends Fragment implements IModelChangeListener {

    protected BaseController mController;

    public static class BaseHandler extends Handler{

        WeakReference<BaseFragment> reference;

        public BaseHandler(BaseFragment activity){
            reference=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (reference!=null&&reference.get()!=null){
                reference.get().handleUI(msg);
            }
        }
    }

    protected BaseHandler mHandler=new BaseHandler(this);

    protected void handleUI(Message msg){
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
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void log(String msg){
        Log.v("m520it",msg);
    }

}
