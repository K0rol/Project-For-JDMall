package com.m520it.jdmall03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.JDMallApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.ChosenArea;
import com.m520it.jdmall03.bean.ReceiverAddress;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.bean.SAddAddressParams;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.ReceiverAddressConstant;
import com.m520it.jdmall03.controller.AddressController;
import com.m520it.jdmall03.listener.IChooseAreaCompleteListener;
import com.m520it.jdmall03.ui.pop.ChooseAreaPop;

import static com.m520it.jdmall03.R.id.name_et;

public class AddressAddActivity extends BaseActivity
        implements View.OnClickListener,IChooseAreaCompleteListener {

    private EditText mNameEt;
    private EditText mPhoneEt;
    private TextView mChoseAreaBtn;
    private EditText mAddressDetailsEt;
    private CheckBox mDefaultCbx;
    private LinearLayout mParentView;
    private ChooseAreaPop mChooseAreaPop;
    private ChosenArea mChosenArea;

    @Override
    protected void handleUI(Message msg) {
        if (msg.what==IDiyMessage.ADD_ADDRESS_ACTION){
            Result result= (Result) msg.obj;
            if (!result.isSuccess()){
                showToast(result.getErrorMsg());
                return;
            }
            showToast("添加地址成功!");
            ReceiverAddress bean = JSON.parseObject(result.getResult(), ReceiverAddress.class);
            Intent intent=new Intent();
            intent.putExtra(ReceiverAddressConstant.ADDRESS_KEY,bean);
            setResult(0,intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add);
        initController();
        initView();
    }

    @Override
    protected void initController() {
        mController=new AddressController(this);
        mController.setListener(this);
    }

    @Override
    protected void initView() {
        mNameEt = (EditText) findViewById(name_et);
        mPhoneEt = (EditText) findViewById(R.id.phone_et);
        mChoseAreaBtn = (TextView) findViewById(R.id.choose_area_tv);
        mAddressDetailsEt = (EditText) findViewById(R.id.address_details_et);
        mDefaultCbx = (CheckBox) findViewById(R.id.default_cbx);
        mParentView = (LinearLayout) findViewById(R.id.parent_view);
        mChoseAreaBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mChooseAreaPop==null){
            mChooseAreaPop = new ChooseAreaPop(this);
            mChooseAreaPop.setListener(this);
        }
        mChooseAreaPop.onShow(findViewById(R.id.parent_view));
    }

    /**
     * 添加一条新地址
     * */
    public void saveAddress(View v) {
        // validate
        String name = mNameEt.getText().toString().trim();
        String phone = mPhoneEt.getText().toString().trim();
        String addressDetails = mAddressDetailsEt.getText().toString().trim();
        if (TextUtils.isEmpty(name)
                ||TextUtils.isEmpty(phone)
                ||TextUtils.isEmpty(addressDetails)) {
            showToast("请输入完整的地址信息");
            return;
        }
        if (mChosenArea==null){
            showToast("请选择完整的省市区");
            return;
        }
        boolean isDefault = mDefaultCbx.isChecked();
        SAddAddressParams params = buildAddAddressSendParams(name, phone, mChosenArea, addressDetails, isDefault);
        mController.sendAsyncMessage(IDiyMessage.ADD_ADDRESS_ACTION,params);
    }

    private SAddAddressParams buildAddAddressSendParams(String name, String phone, ChosenArea chosenArea,
                                                        String addressDetails,
                                                        boolean isDefault) {
        JDMallApplication app = (JDMallApplication) getApplication();
        return new SAddAddressParams(app.mUserInfo.getId(),name,phone,
                chosenArea.tabProvince.getCode(),chosenArea.tabCity.getCode(),chosenArea.tabArea.getCode(),
                addressDetails,isDefault);
    }

    @Override
    public void onAreaChosen(AddressController.Area tabProvince,
                              AddressController.Area tabCity,
                              AddressController.Area tabArea) {
        //弹出框确认按钮点击的时候调用的方法
        if (mChooseAreaPop!=null){
            mChooseAreaPop.onDismiss();
        }
        mChoseAreaBtn.setText(tabProvince.getName()+tabCity.getName()+tabArea.getName());
        mChosenArea =new ChosenArea(tabProvince,tabCity,tabArea);
    }
}
