package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public abstract class JDBaseAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected List<T> mDatas;
    protected Context mContext;

    public JDBaseAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setDatas(List<T> datas) {
        this.mDatas=datas;
    }

    @Override
    public int getCount() {
        return mDatas!=null?mDatas.size():0;
    }

    @Override
    public T getItem(int position) {
        return mDatas!=null?mDatas.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
