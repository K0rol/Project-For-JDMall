package com.m520it.jdmall03.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m520it.jdmall03.JDMallApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.adapter.AllOrderAdapter;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.OrderStatus;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public class AllOrderFragment extends BaseOrderFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_order, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        initComponent(view,R.id.all_order_lv,AllOrderAdapter.class,R.id.all_order_null_view);
    }

    @Override
    public void onRefresh() {
        mController.sendAsyncMessage(IDiyMessage.ORDER_LIST_ACTION,
                JDMallApplication.getUserId(), OrderStatus.ALL_ORDER);
    }
}
