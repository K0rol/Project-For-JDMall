package com.m520it.jdmall03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.m520it.jdmall03.JDMallApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.adapter.AddressListAdapter;
import com.m520it.jdmall03.bean.ReceiverAddress;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.ReceiverAddressConstant;
import com.m520it.jdmall03.controller.AddressController;
import com.m520it.jdmall03.listener.IAddressDelListener;
import com.m520it.jdmall03.ui.FlexiListView;

import java.util.List;

/**
 * 地址列表页
 */
public class AddressListActivity extends BaseActivity implements IAddressDelListener, AdapterView.OnItemClickListener {

    private FlexiListView mAddressLv;
    private AddressListAdapter mAddressListAdapter;
    private int mSign;//如果是我的JD页面进来的标识是1 否则是0

    @Override
    protected void handleUI(Message msg) {
        switch (msg.what) {
            case IDiyMessage.ADDRESS_LIST_ACTION:
                showAddressLv((List<ReceiverAddress>) msg.obj);
                break;
            case IDiyMessage.DEL_ADDRESS_ACTION:
                dealDelAddressResult((Result) msg.obj);
                break;
        }
    }

    private void dealDelAddressResult(Result result){
        showToast(result.isSuccess()?"删除地址成功":result.getErrorMsg());
        if (result.isSuccess()){
            requestAddressData();
        }
    }

    private void showAddressLv(List<ReceiverAddress> datas) {
        mAddressListAdapter.setDatas(datas);
        mAddressListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        initData();
        initView();
        initController();
        requestAddressData();
    }

    @Override
    protected void initData() {
        mSign = getIntent().getIntExtra(ReceiverAddressConstant.START_SIGN_KEY, 0);
    }

    private void requestAddressData() {
        JDMallApplication application = (JDMallApplication) getApplication();
        mController.sendAsyncMessage(IDiyMessage.ADDRESS_LIST_ACTION,
                application.mUserInfo.getId());
    }

    @Override
    protected void initController() {
        mController = new AddressController(this);
        mController.setListener(this);
    }

    @Override
    protected void initView() {
        mAddressLv = (FlexiListView) findViewById(R.id.address_lv);
        mAddressListAdapter = new AddressListAdapter(this);
        mAddressListAdapter.setListener(this);
        mAddressLv.setAdapter(mAddressListAdapter);
        if (mSign!=1){//购物车跳进来
            mAddressLv.setOnItemClickListener(this);
        }
    }


    @Override
    public void onAddressDeleted(long addressId) {
        JDMallApplication app = (JDMallApplication) getApplication();
        mController.sendAsyncMessage(IDiyMessage.DEL_ADDRESS_ACTION,
                app.mUserInfo.getId(), addressId);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ReceiverAddress bean = mAddressListAdapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra(ReceiverAddressConstant.ADDRESS_KEY,bean);
        setResult(0,intent);
        finish();
    }

}
