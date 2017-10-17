package com.m520it.jdmall03.ui.pop;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.listener.IPayClickListener;

import static com.m520it.jdmall03.R.id.pwd_et;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class PayDialog extends Dialog implements View.OnClickListener {
    private final Context mContext;
    private EditText mAccountEt;
    private EditText mPwdEt;
    private EditText mPayPwdEt;
    private Button mCancelBtn;
    private Button mPayBtn;
    private IPayClickListener mListener;

    public PayDialog(@NonNull Context context) {
        super(context);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_dialog);
        initView();

    }

    private void initView() {
        mAccountEt = (EditText) findViewById(R.id.account_et);
        mPwdEt = (EditText) findViewById(pwd_et);
        mPayPwdEt = (EditText) findViewById(R.id.pay_pwd_et);
        mCancelBtn = (Button) findViewById(R.id.cancel_btn);
        mPayBtn = (Button) findViewById(R.id.pay_btn);
        mCancelBtn.setOnClickListener(this);
        mPayBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                dismiss();
                break;
            case R.id.pay_btn:
                String account = mAccountEt.getText().toString();
                String pwd = mPwdEt.getText().toString();
                String payPwd = mPayPwdEt.getText().toString();
                if (TextUtils.isEmpty(account)||
                        TextUtils.isEmpty(pwd)||
                        TextUtils.isEmpty(payPwd))
                    Toast.makeText(mContext,"请输入支付的信息",Toast.LENGTH_SHORT).show();
                if (mListener!=null){
                    mListener.onPayClicked(account,pwd,payPwd);
                }
                break;
        }
    }

    public void setListener(IPayClickListener listener) {
        this.mListener=listener;
    }
}
