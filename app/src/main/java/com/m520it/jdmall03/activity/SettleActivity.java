package com.m520it.jdmall03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.m520it.jdmall03.JDMallApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.RAddOrder;
import com.m520it.jdmall03.bean.ReceiverAddress;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.bean.SBuildOrderParams;
import com.m520it.jdmall03.bean.Shopcar;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.ReceiverAddressConstant;
import com.m520it.jdmall03.controller.BuildOrderController;
import com.m520it.jdmall03.listener.IAddOrderFinisheListener;
import com.m520it.jdmall03.ui.pop.BuildOrderDialog;
import com.m520it.jdmall03.utils.ImageUtil;

import java.util.ArrayList;

/**
 * 结算页面
 */
public class SettleActivity extends BaseActivity
        implements View.OnClickListener,IAddOrderFinisheListener {

    private TextView mNameTv;
    private TextView mPhoneTv;
    private TextView mAddressTv;
    private RelativeLayout mHasReceiverRl;
    private RelativeLayout mNoReceiverRl;
    private LinearLayout mProductContainerLl;
    private TextView mTotalPsizeTv;
    private TextView mAllPriceValTv;

    public static final String CHECKDATAS_KEY = "SettleActivity::dataList";
    public static final String TOTALPRICE_KEY = "SettleActivity::totalPrice";
    private ArrayList<Shopcar> mCheckedDatas;
    private double mTotalPrice;
    private ReceiverAddress mReceiverAddress;

    @Override
    protected void handleUI(Message msg) {
        switch (msg.what) {
            case IDiyMessage.DEFAULT_RECEIVER_ACTION:
                showDefaultReceiver(msg.obj);
                break;
            case IDiyMessage.ADD_ORDER_ACTION:
                dealAddOrderResult(msg.obj);
                break;
            case IDiyMessage.CANCEL_ORDER:
                dealCancelOrderResult((Result) msg.obj);
                break;
        }
    }

    private void dealCancelOrderResult(Result result){
        showToast(result.isSuccess()?"取消订单成功":result.getErrorMsg());
        finish();
    }

    private void dealAddOrderResult(Object obj){
        if (obj==null){
            showToast("网络请求异常");
        return;
        }
        showToast("下单成功!");
        BuildOrderDialog buildOrderDialog = new BuildOrderDialog(this,(RAddOrder) obj);
        buildOrderDialog.setListener(this);
        buildOrderDialog.show();
    }

    /**
     * 显示默认的收货人
     */
    private void showDefaultReceiver(Object obj) {
        mHasReceiverRl.setVisibility(obj != null ? View.VISIBLE : View.GONE);
        mNoReceiverRl.setVisibility(obj == null ? View.VISIBLE : View.GONE);
        if (obj != null) {//有收货人地址
            mReceiverAddress = (ReceiverAddress) obj;
            mNameTv.setText(mReceiverAddress.getReceiverName());
            mPhoneTv.setText(mReceiverAddress.getReceiverPhone());
            mAddressTv.setText(mReceiverAddress.getReceiverAddress());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);
        initData();
        initView();
        initController();
        requestDefaultReceiverAddress();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mCheckedDatas = (ArrayList<Shopcar>) intent.getSerializableExtra(CHECKDATAS_KEY);
        mTotalPrice = intent.getDoubleExtra(TOTALPRICE_KEY, 0);
        if (mCheckedDatas.size() <= 0 || mTotalPrice == 0) {
            showToast("error get datas !");
            finish();
        }
    }

    private void requestDefaultReceiverAddress() {
        mController.sendAsyncMessage(IDiyMessage.DEFAULT_RECEIVER_ACTION,
                JDMallApplication.getUserId());
    }

    @Override
    protected void initController() {
        mController = new BuildOrderController(this);
        mController.setListener(this);
    }

    @Override
    protected void initView() {
        mNameTv = (TextView) findViewById(R.id.name_tv);
        mPhoneTv = (TextView) findViewById(R.id.phone_tv);
        mAddressTv = (TextView) findViewById(R.id.address_tv);
        mHasReceiverRl = (RelativeLayout) findViewById(R.id.has_receiver_rl);
        mNoReceiverRl = (RelativeLayout) findViewById(R.id.no_receiver_rl);
        mProductContainerLl = (LinearLayout) findViewById(R.id.product_container_ll);
        mTotalPsizeTv = (TextView) findViewById(R.id.total_psize_tv);
        mAllPriceValTv = (TextView) findViewById(R.id.all_price_val_tv);
        showBuyProductList();
        mTotalPsizeTv.setText("共" + mCheckedDatas.size() + "件");
        mAllPriceValTv.setText("¥ " + mTotalPrice);
        findViewById(R.id.pay_whenget_tv).setOnClickListener(this);
        findViewById(R.id.pay_online_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        findViewById(R.id.pay_whenget_tv).setSelected(v.getId() == R.id.pay_whenget_tv);
        findViewById(R.id.pay_online_tv).setSelected(v.getId() == R.id.pay_online_tv);
    }

    /**
     * 显示要购买的商品
     */
    private void showBuyProductList() {
        // 1.首先要计算出到底要显示几个item===>取 容器个数与数据个数的最小值
        // 容器只能显示5个
        // 如果数据有6个  ===>5
        // 如果数据有3个  ===>3
        // 2.for循环遍历最小值 找到容器循环给每个item的图片/文本控件赋值
        int containerCount = mProductContainerLl.getChildCount();
        int dataCount = mCheckedDatas.size();
        int realCount = Math.min(containerCount, dataCount);
        for (int index = 0; index < realCount; index++) {
            View itemContainer = mProductContainerLl.getChildAt(index);
            Shopcar itemData = mCheckedDatas.get(index);
            ImageView pIv = (ImageView) itemContainer.findViewById(R.id.piv);
            TextView psizeTv = (TextView) itemContainer.findViewById(R.id.psize);
            ImageUtil.loadImage(this, itemData.getPimageUrl(), pIv);
            psizeTv.setText("x " + itemData.getBuyCount());
        }
    }

    /**
     * 选择地址
     */
    public void chooseAddress(View v) {
        Intent intent = new Intent(this, AddressListActivity.class);
        startActivityForResult(intent, ReceiverAddressConstant.CHOOSE_ADDRESS_EQR);
    }

    /**
     * 添加新地址
     */
    public void addAddress(View v) {
        Intent intent = new Intent(this, AddressAddActivity.class);
        startActivityForResult(intent, ReceiverAddressConstant.ADD_ADDRESS_EQR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == ReceiverAddressConstant.CHOOSE_ADDRESS_EQR ||
                    requestCode == ReceiverAddressConstant.ADD_ADDRESS_EQR) {
                ReceiverAddress bean = (ReceiverAddress) data
                        .getSerializableExtra(ReceiverAddressConstant.ADDRESS_KEY);
                showDefaultReceiver(bean);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void submitClick(View v) {
        if (mReceiverAddress == null) {
            showToast("请选择收货人地址");
            return;
        }
        TextView payWhenReceived = (TextView) findViewById(R.id.pay_whenget_tv);
        TextView payOnline = (TextView) findViewById(R.id.pay_online_tv);
        if (!payWhenReceived.isSelected() && !payOnline.isSelected()) {
            showToast("请选择支付方式");
            return;
        }
        //开始实现网络请求(构建发送请求的参数)
        SBuildOrderParams params = buildAddOrderParams(payOnline);
        mController.sendAsyncMessage(IDiyMessage.ADD_ORDER_ACTION,params);
    }

    private SBuildOrderParams buildAddOrderParams(TextView payOnline) {
        ArrayList<SBuildOrderParams.OrderProduct> products = new ArrayList<>();
        for (Shopcar items : mCheckedDatas) {
            SBuildOrderParams.OrderProduct newBean = new SBuildOrderParams.OrderProduct();
            newBean.setPid(items.getPid());
            newBean.setType(items.getPversion());
            newBean.setBuyCount(items.getBuyCount());
            products.add(newBean);
        }
        int payWay = payOnline.isSelected() ? 0 : 1;
        return new SBuildOrderParams(products, mReceiverAddress.getId(),
                payWay, JDMallApplication.getUserId());
    }

    @Override
    public void onOrderCanel(long oid) {//取消订单
        mController.sendAsyncMessage(IDiyMessage.CANCEL_ORDER,
                JDMallApplication.getUserId(),oid);
    }

    @Override
    public void onPayWhenReceiveConfirmed(long oid) {//货到付款确认
        Log.i("lean",oid+"  "+JDMallApplication.getUserId());
        Intent intent=new Intent(this,OrderDetailsActivity.class);
        intent.putExtra(OrderDetailsActivity.OID_KEY,oid);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPayOnlineConfirmed(RAddOrder bean) {//在线支付弹出框的确认按钮
        //点击该按钮 就进入支付的页面 一般情况下直接调用第三方支付功能
        Intent intent=new Intent(this,AlipayActivity.class);
        intent.putExtra(AlipayActivity.TN_KEY,bean.getTn());
        startActivity(intent);
        finish();
    }
}
