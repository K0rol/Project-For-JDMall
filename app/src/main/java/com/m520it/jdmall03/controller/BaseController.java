package com.m520it.jdmall03.controller;

import android.content.Context;

import com.m520it.jdmall03.listener.IModelChangeListener;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/8/3 0003.
 *
 *  因为每个子Controller实现网络请求 都需要自己去创建一个线程
 */
public abstract class BaseController {

    protected IModelChangeListener mListener;
    protected Context mContext;

    public BaseController(Context context) {
        mContext = context;
    }

    public void setListener(IModelChangeListener listener) {
        mListener = listener;
    }

    public static class HandleThread extends Thread{

        private final int mAction;
        private final Object[] values;
        WeakReference<BaseController> mReference;

        public HandleThread(BaseController baseController, int action, Object[] values) {
            mReference=new WeakReference<BaseController>(baseController);
            this.mAction=action;
            this.values=values;
        }

        @Override
        public void run() {
            if (mReference!=null&&mReference.get()!=null){
                //调用子类做具体的业务
                mReference.get().handleMessage(mAction,values);
            }
        }

    }

    public void sendAsyncMessage(final int action, final Object... values){
        new HandleThread(this,action,values).start();
    }

    public abstract void handleMessage(int action,Object... values);

    protected void onModelChanged(int action,Object result){
        if (mListener!=null){
            mListener.onModelChanged(action,result);
        }
    }

}
