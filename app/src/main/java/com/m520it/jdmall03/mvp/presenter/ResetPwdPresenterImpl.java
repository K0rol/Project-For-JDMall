package com.m520it.jdmall03.mvp.presenter;

import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.mvp.model.IResetPwdModel;
import com.m520it.jdmall03.mvp.model.ResetPwdModelImp;
import com.m520it.jdmall03.mvp.view.IResetView;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class ResetPwdPresenterImpl implements IResetPwdPresenter,IResetPwdModel.IPwdResetListener {

    private IResetPwdModel iModel;
    private IResetView mResetView;

    public ResetPwdPresenterImpl(IResetView resetView) {
        this.iModel = new ResetPwdModelImp(this);
        this.mResetView=resetView;
    }

    @Override
    public void resetPwd(String account) {
        //通知View层显示对话框
        mResetView.showProgressDialog();
        //通知Model层去实现网络请求
        iModel.resetPwd(account);
    }

    @Override
    public void onPwdReseted(Result resultBean) {
        //隐藏对话框
        mResetView.hideProgressDialog();
        //处理重置密码的结果
        mResetView.dealResetPwdResult(resultBean);
    }
}
