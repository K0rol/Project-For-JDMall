package com.m520it.jdmall03.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m520it.jdmall03.JDMallApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.adapter.WaitPayAdapter;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.OrderStatus;
import com.m520it.jdmall03.listener.IOrderCancelListener;
import com.m520it.jdmall03.ui.pop.LoadingDialog;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public class WaitPayOrderFragment extends BaseOrderFragment
        implements IOrderCancelListener {

    private LoadingDialog mLoadingDialog;

    @Override
    protected void handleUI(Message msg) {
        super.handleUI(msg);
        if (msg.what==IDiyMessage.CANCEL_ORDER){
            if (mLoadingDialog!=null){
                mLoadingDialog.dismiss();
            }
            Result resultBean= (Result) msg.obj;
            if (resultBean.isSuccess()){
                showToast("取消订单成功");
                onRefresh();
            }else{
                showToast(resultBean.getErrorMsg());
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wait_pay, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLoadingDialog = new LoadingDialog(getActivity());
        initComponent(view, R.id.wait_pay_order_lv, WaitPayAdapter.class,
                R.id.wait_pay_null_view);
        ((WaitPayAdapter) mAdapter).setListener(this);
    }

    @Override
    public void onRefresh() {
        mController.sendAsyncMessage(IDiyMessage.ORDER_LIST_ACTION,
                JDMallApplication.getUserId(), OrderStatus.WAIT_PAY_ORDER);
    }

    @Override
    public void onOrderCanceled(long oid) {
        //1.弹出一个加载数据的对话框
        mLoadingDialog.show();
        //2.发送一个异步请求
        mController.sendAsyncMessage(IDiyMessage.CANCEL_ORDER,
                JDMallApplication.getUserId(), oid);
    }

}
