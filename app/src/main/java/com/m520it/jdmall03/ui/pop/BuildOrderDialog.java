package com.m520it.jdmall03.ui.pop;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.RAddOrder;
import com.m520it.jdmall03.listener.IAddOrderFinisheListener;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class BuildOrderDialog extends Dialog implements View.OnClickListener {
    private RAddOrder mBean;
    private TextView mOrderNoTv;
    private TextView mTotalPriceTv;
    private TextView mFreightTv;
    private TextView mActualPriceTv;
    private Button mCancalBtn;
    private Button mSureBtn;
    private IAddOrderFinisheListener mListener;

    public BuildOrderDialog(@NonNull Context context,@NonNull RAddOrder bean) {
        super(context, R.style.CustomDialog);
        mBean=bean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.build_order_dialog);
        initView();

    }

    private void initView() {
        mOrderNoTv = (TextView) findViewById(R.id.order_no_tv);
        mOrderNoTv.setText("订单编号:"+mBean.getOrderNum());
        mTotalPriceTv = (TextView) findViewById(R.id.total_price_tv);
        mTotalPriceTv.setText("总价:¥ "+mBean.getAllPrice());
        mFreightTv = (TextView) findViewById(R.id.freight_tv);
        //如果这个商品低于某个价格 就需要自负运费(5元  15元)
        mFreightTv.setText("运费:¥ "+mBean.getFreight());
        mActualPriceTv = (TextView) findViewById(R.id.actual_price_tv);
        mActualPriceTv.setText("实付款:¥ "+mBean.getTotalPrice());
        mCancalBtn = (Button) findViewById(R.id.cancal_btn);
        mSureBtn = (Button) findViewById(R.id.sure_btn);
        mCancalBtn.setOnClickListener(this);
        mSureBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener==null){
            return;
        }
        switch (v.getId()) {
            case R.id.cancal_btn:
                //取消订单 网络请求
                mListener.onOrderCanel(mBean.getOid());
                break;
            case R.id.sure_btn:
                int payWay=mBean.getPayWay();
                if (payWay==0){//1.如果是在线支付--->支付页面
                    mListener.onPayOnlineConfirmed(mBean);
                }else if (payWay==1) {//2.如果是货到付款--->提醒用户订单已经创建(打开订单的详情页面)
                    mListener.onPayWhenReceiveConfirmed(mBean.getOid());
                }
                break;
        }
        dismiss();
    }

    public void setListener(IAddOrderFinisheListener listener) {
        this.mListener=listener;
    }
}
