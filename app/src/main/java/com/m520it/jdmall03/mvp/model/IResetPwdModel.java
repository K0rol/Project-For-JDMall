package com.m520it.jdmall03.mvp.model;

import com.m520it.jdmall03.bean.Result;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public interface IResetPwdModel {

    public void resetPwd(String name);

    public static interface IPwdResetListener{

        public void onPwdReseted(Result result);

    }

}
