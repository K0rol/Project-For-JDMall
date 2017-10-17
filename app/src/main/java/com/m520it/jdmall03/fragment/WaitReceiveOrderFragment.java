package com.m520it.jdmall03.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m520it.jdmall03.JDMallApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.adapter.WaitReceiveAdapter;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.OrderStatus;
import com.m520it.jdmall03.listener.IConfirmReceiveListener;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public class WaitReceiveOrderFragment extends BaseOrderFragment
        implements IConfirmReceiveListener {

    @Override
    protected void handleUI(Message msg) {
        super.handleUI(msg);
        if (msg.what==IDiyMessage.CONFIRM_RECEIVER_ORDER_ACTION){
            Result result= (Result) msg.obj;
            if (result.isSuccess()){
                showToast("确认收货成功");
                onRefresh();
            }else{
                showToast(result.getErrorMsg());
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wait_receive, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        initComponent(view,R.id.wait_receive_order_lv,
                WaitReceiveAdapter.class,R.id.wait_receive_null_view);
        ((WaitReceiveAdapter)mAdapter).setListener(this);
    }


    @Override
    public void onRefresh() {
        mController.sendAsyncMessage(IDiyMessage.ORDER_LIST_ACTION,
                JDMallApplication.getUserId(), OrderStatus.WAIT_RECEIVE_ORDER);
    }

    @Override
    public void onOrderReceived(long oid) {
        mController.sendAsyncMessage(IDiyMessage.CONFIRM_RECEIVER_ORDER_ACTION,
                JDMallApplication.getUserId(),oid);
    }

}
