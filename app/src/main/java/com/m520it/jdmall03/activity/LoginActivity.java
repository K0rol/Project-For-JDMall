package com.m520it.jdmall03.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.JDMallApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.Account;
import com.m520it.jdmall03.bean.LoginResult;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.controller.UserController;
import com.m520it.jdmall03.mvp.view.ResetActivity;
import com.m520it.jdmall03.utils.ActivityUtil;

import static com.m520it.jdmall03.R.id.name_et;
import static com.m520it.jdmall03.R.id.pwd_et;

public class LoginActivity extends BaseActivity {

    private EditText mNameEt;
    private EditText mPwdEt;

    @Override
    protected void handleUI(Message msg) {
        if (msg.what == IDiyMessage.LOGIN_ACTION) {
            Result result = (Result) msg.obj;
            if (!result.isSuccess()) {
                showToast(result.getErrorMsg());
                return;
            }
            showToast("恭喜登录成功!");
            //保存账号密码到 数据库
            String name = mNameEt.getText().toString().trim();
            String pwd = mPwdEt.getText().toString().trim();
            mController.sendAsyncMessage(IDiyMessage.SAVE_ACCOUNT_ACTION, name, pwd);
            //将登录成功之后的信息加到内存中
            JDMallApplication app = (JDMallApplication) getApplication();
            app.mUserInfo=JSON.parseObject(result.getResult(), LoginResult.class);
            //跳转到主页
            ActivityUtil.startNew(this, MainActivity.class, true);
        }else if(msg.what==IDiyMessage.QUERY_ACCOUNT_ACTION){
            if (msg.obj!=null){
                Account account= (Account) msg.obj;
                mNameEt.setText(account.getName());
                mPwdEt.setText(account.getPwd());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initController();
        readAccount();
    }

    private void readAccount() {
        mController.sendAsyncMessage(IDiyMessage.QUERY_ACCOUNT_ACTION);
    }

    @Override
    protected void initController() {
        mController = new UserController(this);
        //如果数据返回了  就通过Listener来通知我   这里我首先需要实现这个Listener
        mController.setListener(this);
    }

    @Override
    protected void initView() {
        mNameEt = (EditText) findViewById(name_et);
        mPwdEt = (EditText) findViewById(pwd_et);
    }

    public void loginClick(View v) {
        final String name = mNameEt.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("用户名不能为null");
            return;
        }
        final String pwd = mPwdEt.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            showToast("请输入密码");
            return;
        }
        // TODO 实现网络请求  创建子线程===>实现具体的请求====>返回UI层处理结果
        mController.sendAsyncMessage(IDiyMessage.LOGIN_ACTION, name, pwd);
    }

    public void registClick(View v) {
        ActivityUtil.startNew(this, RegistActivity.class, true);
    }

    public void resetPwdClick(View v) {
        ActivityUtil.startNew(this, ResetActivity.class, true);
    }

}


