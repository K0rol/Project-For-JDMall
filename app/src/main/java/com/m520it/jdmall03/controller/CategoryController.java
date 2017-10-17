package com.m520it.jdmall03.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.bean.SubCategory;
import com.m520it.jdmall03.bean.TopCategory;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.NetworkConst;
import com.m520it.jdmall03.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class CategoryController extends BaseController {

    public CategoryController(Context context) {
        super(context);
    }

    @Override
    public void handleMessage(int action, Object... values) {
        switch (action) {
            case IDiyMessage.TOP_CATEGORY_ACTION:
                onModelChanged(action,loadTopCategory());
                break;
            case IDiyMessage.SUB_CATEGORY_ACTION:
                onModelChanged(action,loadSubCategory((Long) values[0]));
                break;
        }
    }

    private List<SubCategory> loadSubCategory(long topCategoryId){
        String jsonStr = NetworkUtil.doGet(NetworkConst.CATEGORY_URL+"?parentId="+topCategoryId);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(),SubCategory.class);
        }
        return new ArrayList<>();
    }

    private List<TopCategory> loadTopCategory(){
        String jsonStr = NetworkUtil.doGet(NetworkConst.CATEGORY_URL);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(),TopCategory.class);
        }
        return new ArrayList<>();
    }
}
