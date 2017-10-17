package com.m520it.jdmall03.controller;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.Brand;
import com.m520it.jdmall03.bean.RProductList;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.bean.SProductListParams;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.NetworkConst;
import com.m520it.jdmall03.utils.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class ProductListController extends BaseController {

    public ProductListController(Context context) {
        super(context);
    }

    @Override
    public void handleMessage(int action, Object... values) {
        switch (action) {
            case IDiyMessage.BRAND_ACTION:
                onModelChanged(action,loadBrand((Long) values[0]));
                break;
            case IDiyMessage.PRODUCT_LIST_ACTION:
                onModelChanged(action,loadProductList((SProductListParams) values[0]));
                break;
        }
    }

    private List<RProductList> loadProductList(SProductListParams paramsBean){
        HashMap<String, String> paramsMap = builtProductListParam(paramsBean);
        String jsonStr = NetworkUtil.doPost(NetworkConst.PRODUCT_LIST_URL, paramsMap);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()){
            try {
                JSONObject jsonObject = new JSONObject(resultBean.getResult());
                String rowsJson = jsonObject.getString("rows");
                return JSON.parseArray(rowsJson, RProductList.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    @NonNull
    private HashMap<String, String> builtProductListParam(SProductListParams paramsBean) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("categoryId", paramsBean.categoryId+"");
        paramsMap.put("filterType", paramsBean.filterType+"");
        if (paramsBean.sortType!=SProductListParams.SORT_NONE){
            paramsMap.put("sortType", paramsBean.sortType+"");
        }
        paramsMap.put("deliverChoose", paramsBean.deliverChoose+"");
        if (paramsBean.brandId!=0){
            paramsMap.put("brandId", paramsBean.brandId+"");
        }
        return paramsMap;
    }

    private List<Brand> loadBrand(long topCategoryId){
        String jsonStr = NetworkUtil.doGet(NetworkConst.BRAND_URL+"?categoryId="+topCategoryId);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()) {
            return JSON.parseArray(resultBean.getResult(), Brand.class);
        }
        return new ArrayList<>();
    }

}
