package com.m520it.jdmall03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.JDMallApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.Order;
import com.m520it.jdmall03.bean.PayInfo;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.bean.SMockPayParams;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.controller.AlipayController;
import com.m520it.jdmall03.listener.IPayClickListener;
import com.m520it.jdmall03.ui.pop.PayDialog;

public class AlipayActivity extends BaseActivity implements IPayClickListener {

    public static final String TN_KEY = "AlipayActivity::tn";
    private String mTn;
    private TextView mPayPriceTv;
    private TextView mOrderDescValTv;
    private TextView mDealTypeValTv;
    private TextView mDealTimeValTv;
    private TextView mDealNoValTv;
    private PayDialog mPayDialog;

    @Override
    protected void handleUI(Message msg) {
        if (msg.what == IDiyMessage.GET_PAYINFO_ACTION) {
            showPayInfoUi(msg.obj);
        }else if (msg.what==IDiyMessage.MOCK_PAY_ACTION){
            dealPayResult((Result)msg.obj);
        }
    }

    private void dealPayResult(Result result) {
        if (mPayDialog!=null&&mPayDialog.isShowing()){
            mPayDialog.dismiss();
        }
        if (result.isSuccess()){
            showToast("支付成功!");
            //解析数据
            Order order = JSON.parseObject(result.getResult(), Order.class);
            //跳转到订单详情页面
            Intent intent=new Intent(this,OrderDetailsActivity.class);
            intent.putExtra(OrderDetailsActivity.OID_KEY,order.getOid());
            startActivity(intent);
            finish();
        }else{
            showToast(result.getErrorMsg());
        }
    }

    private void showPayInfoUi(Object obj) {
        if (obj == null) {
            return;
        }
        PayInfo info = (PayInfo) obj;
        mPayPriceTv.setText("¥ " + info.getTotalPrice());
        mOrderDescValTv.setText(info.getPname());
        mDealTypeValTv.setText("担保交易");
        mDealTimeValTv.setText(info.getPayTime());
        mDealNoValTv.setText(info.getOinfo());
    }

    @Override
    protected void initView() {
        mPayPriceTv = (TextView) findViewById(R.id.pay_price_tv);
        mOrderDescValTv = (TextView) findViewById(R.id.order_desc_val_tv);
        mDealTypeValTv = (TextView) findViewById(R.id.deal_type_val_tv);
        mDealTimeValTv = (TextView) findViewById(R.id.deal_time_val_tv);
        mDealNoValTv = (TextView) findViewById(R.id.deal_no_val_tv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);
        initView();
        initData();
        initController();
        mController.sendAsyncMessage(IDiyMessage.GET_PAYINFO_ACTION,
                mTn, JDMallApplication.getUserId());
    }

    @Override
    protected void initController() {
        mController = new AlipayController(this);
        mController.setListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mTn = intent.getStringExtra(TN_KEY);
        if (mTn == null) {
            showToast("data error !");
            finish();
        }
    }

    /**
     * 立刻付款
     *
     * @param v
     */
    public void payClick(View v) {
        if (mPayDialog == null) {
            mPayDialog = new PayDialog(this);
            mPayDialog.setListener(this);
        }
        mPayDialog.show();
    }

    @Override
    public void onPayClicked(String account, String pwd, String payPwd) {
        SMockPayParams params = new SMockPayParams(account, pwd, payPwd,
                mTn, JDMallApplication.getUserId());
        mController.sendAsyncMessage(IDiyMessage.MOCK_PAY_ACTION, params);
    }
}
