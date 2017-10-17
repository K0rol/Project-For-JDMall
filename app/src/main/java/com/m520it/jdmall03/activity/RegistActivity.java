package com.m520it.jdmall03.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.controller.UserController;
import com.m520it.jdmall03.utils.ActivityUtil;

import static com.m520it.jdmall03.R.id.pwd_et;
import static com.m520it.jdmall03.R.id.surepwd_et;
import static com.m520it.jdmall03.R.id.username_et;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class RegistActivity extends BaseActivity {

    private EditText mUsernameEt;
    private EditText mPwdEt;
    private EditText mSurepwdEt;

    @Override
    protected void handleUI(Message msg) {
        if (msg.what==IDiyMessage.REGIST_ACTION){
            handleRegistResult((Result) msg.obj);
        }
    }

    private void handleRegistResult(Result resultBean){
        if (resultBean.isSuccess()){
            showToast("注册成功了!!!");
            ActivityUtil.startNew(this,LoginActivity.class,true);
        }else{
            showToast(resultBean.getErrorMsg());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initController();
        initView();
    }

    @Override
    protected void initController() {
        mController=new UserController(this);
        mController.setListener(this);
    }

    @Override
    protected void initView() {
        mUsernameEt = (EditText) findViewById(username_et);
        mPwdEt = (EditText) findViewById(pwd_et);
        mSurepwdEt = (EditText) findViewById(surepwd_et);
    }

    public void registClick(View v) {
        String name = mUsernameEt.getText().toString().trim();
        String pwd = mPwdEt.getText().toString().trim();
        String surePwd = mSurepwdEt.getText().toString().trim();
        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)||TextUtils.isEmpty(surePwd)) {
            showToast("请输入完整的信息!");
            return;
        }
        if (!pwd.equals(surePwd)) {
            showToast("两次密码不一致!");
            return;
        }
        // TODO 实现网络请求
        mController.sendAsyncMessage(IDiyMessage.REGIST_ACTION,name,pwd);
    }
}
