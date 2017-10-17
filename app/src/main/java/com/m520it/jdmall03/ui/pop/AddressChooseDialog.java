package com.m520it.jdmall03.ui.pop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.activity.AddressAddActivity;
import com.m520it.jdmall03.activity.AddressListActivity;
import com.m520it.jdmall03.constant.ReceiverAddressConstant;
import com.m520it.jdmall03.utils.ActivityUtil;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class AddressChooseDialog extends Dialog implements View.OnClickListener {

    private final Context mContext;
    private Button mChooseAddressId;
    private Button mAddAddressId;

    public AddressChooseDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_choose_dialog);
        initUI();
    }

    private void initUI() {
        mChooseAddressId = (Button) findViewById(R.id.choose_address_id);
        mAddAddressId = (Button) findViewById(R.id.add_address_id);
        mChooseAddressId.setOnClickListener(this);
        mAddAddressId.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.choose_address_id:
                Intent intent=new Intent(mContext,AddressListActivity.class);
                intent.putExtra(ReceiverAddressConstant.START_SIGN_KEY,1);
                mContext.startActivity(intent);
                break;
            case R.id.add_address_id:
                ActivityUtil.startNew(mContext, AddressAddActivity.class,false);
                break;
        }
    }
}
