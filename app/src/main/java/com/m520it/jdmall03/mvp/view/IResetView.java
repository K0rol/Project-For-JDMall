package com.m520it.jdmall03.mvp.view;

import com.m520it.jdmall03.bean.Result;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public interface IResetView {

    public void showProgressDialog();

    public void hideProgressDialog();

    public void dealResetPwdResult(Result result);

}
