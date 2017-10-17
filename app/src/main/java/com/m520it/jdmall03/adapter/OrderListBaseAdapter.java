package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.OrderList;
import com.m520it.jdmall03.utils.ImageUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public abstract class OrderListBaseAdapter extends JDBaseAdapter<OrderList> {

    public OrderListBaseAdapter(Context context) {
        super(context);
    }

    protected void showOrderProductIv(LinearLayout containerLl, String dataJson) {
        //先隐藏订单内部的所有商品控件
        int containerCount = containerLl.getChildCount();
        for(int index=0;index<containerCount;index++){
            containerLl.getChildAt(index).setVisibility(View.INVISIBLE);
        }
        List<String> datas = JSON.parseArray(dataJson, String.class);
        int dataCount = datas.size();
        int realCount = Math.min(containerCount, dataCount);
        for (int index = 0; index < realCount; index++) {
            ImageView iv = (ImageView) containerLl.getChildAt(index);
            ImageUtil.loadImage(mContext, datas.get(index), iv);
            iv.setVisibility(View.VISIBLE);
        }
    }

}
