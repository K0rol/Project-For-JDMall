package com.m520it.jdmall03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.JDMallApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.adapter.OrderProductAdapter;
import com.m520it.jdmall03.bean.OrderDetailsBean;
import com.m520it.jdmall03.bean.OrderProductBean;
import com.m520it.jdmall03.bean.ReceiverAddress;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.OrderStatus;
import com.m520it.jdmall03.controller.OrderController;
import com.m520it.jdmall03.utils.FixedViewUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class OrderDetailsActivity extends BaseActivity {

    public static final String OID_KEY = "OrderDetailsActivity::oid";
    private TextView mOrderNOTv;
    private TextView mStatusTv;
    private TextView mReceiveNameTv;
    private TextView mReceivePhoneTv;
    private TextView mReceiveAddressTv;
    private ListView mProductsLv;
    private TextView mTotalPriceValTv;
    private TextView mTakePriceValTv;
    private TextView mActualPriceValTv;
    private TextView mOrderTimeTv;
    private OrderProductAdapter mAdapter;
    private long mOid;

    @Override
    protected void handleUI(Message msg) {
        if (msg.what==IDiyMessage.ORDER_DETAILS_ACTION){
            showOrderDetailsPage(msg.obj);
        }
    }

    private void showOrderDetailsPage(Object obj) {
        if (obj==null){
            return;
        }
        OrderDetailsBean bean= (OrderDetailsBean) obj;
        mOrderNOTv.setText("订单编号:"+bean.getOrderNum());
        mStatusTv.setText(OrderStatus.getOrderStatus(bean.getStatus()));
        showReceiverAddress(bean.getAddress());
        showOrderProducts(bean.getItems());
        mTotalPriceValTv.setText("¥ "+(bean.getTotalPrice()-bean.getFreight()));
        mTakePriceValTv.setText("¥ "+bean.getFreight());
        mActualPriceValTv.setText("¥ "+bean.getTotalPrice());//用户应该付款的价格
        mOrderTimeTv.setText("下单时间:"+bean.getBuyTime());
    }

    private void showOrderProducts(String productsJson) {
        List<OrderProductBean> datas = JSON.parseArray(productsJson, OrderProductBean.class);
        mAdapter.setDatas(datas);
        mAdapter.notifyDataSetChanged();
        FixedViewUtil.setListViewHeightBasedOnChildren(mProductsLv);
    }

    private void showReceiverAddress(String addressJson) {
        ReceiverAddress bean = JSON.parseObject(addressJson, ReceiverAddress.class);
        mReceiveNameTv.setText(bean.getReceiverName());
        mReceivePhoneTv.setText(bean.getReceiverPhone());
        mReceiveAddressTv.setText(bean.getReceiverAddress());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        initData();
        initView();
        initController();
        mController.sendAsyncMessage(IDiyMessage.ORDER_DETAILS_ACTION,
                JDMallApplication.getUserId(),mOid);
    }

    @Override
    protected void initController() {
        mController=new OrderController(this);
        mController.setListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mOid = intent.getLongExtra(OID_KEY,0);
        if (mOid == 0) {
            showToast("data error!");
            finish();
        }
    }

    protected void initView() {
        mOrderNOTv = (TextView) findViewById(R.id.order_no_tv);
        mStatusTv = (TextView) findViewById(R.id.status_tv);
        mReceiveNameTv = (TextView) findViewById(R.id.receive_name_tv);
        mReceivePhoneTv = (TextView) findViewById(R.id.receive_phone_tv);
        mReceiveAddressTv = (TextView) findViewById(R.id.receive_address_tv);
        mProductsLv = (ListView) findViewById(R.id.products_lv);
        mAdapter=new OrderProductAdapter(this);
        mProductsLv.setAdapter(mAdapter);
        mTotalPriceValTv = (TextView) findViewById(R.id.total_price_val_tv);
        mTakePriceValTv = (TextView) findViewById(R.id.take_price_val_tv);
        mActualPriceValTv = (TextView) findViewById(R.id.actual_price_val_tv);
        mOrderTimeTv = (TextView) findViewById(R.id.order_time_tv);
    }
}
