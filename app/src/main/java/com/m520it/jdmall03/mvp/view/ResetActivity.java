package com.m520it.jdmall03.mvp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.mvp.presenter.IResetPwdPresenter;
import com.m520it.jdmall03.mvp.presenter.ResetPwdPresenterImpl;

import static com.m520it.jdmall03.R.id.username_et;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class ResetActivity extends AppCompatActivity implements IResetView {

    private EditText mUsernameEt;
    private IResetPwdPresenter mPresenter;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        initView();
        mPresenter=new ResetPwdPresenterImpl(this);
    }

    private void initView() {
        mUsernameEt = (EditText) findViewById(username_et);
    }

    public void resetClick(View v) {
        String et = mUsernameEt.getText().toString().trim();
        if (TextUtils.isEmpty(et)) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO 实现网络请求
        mPresenter.resetPwd(et);
    }

    @Override
    public void showProgressDialog() {
        if (mDialog==null){
            mDialog =new ProgressDialog(this);
            mDialog.setMessage("重置密码中....");
        }
        mDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mDialog!=null&&mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void dealResetPwdResult(Result result) {
        if (result.isSuccess()){
            Toast.makeText(this,"重置密码为123456", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,result.getErrorMsg(), Toast.LENGTH_SHORT).show();
        }
    }
}
