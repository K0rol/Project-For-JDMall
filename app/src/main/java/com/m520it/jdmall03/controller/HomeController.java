package com.m520it.jdmall03.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.BannerBean;
import com.m520it.jdmall03.bean.RecommandProduct;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.bean.SecKillBean;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.NetworkConst;
import com.m520it.jdmall03.utils.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public class HomeController extends BaseController {

    public HomeController(Context context) {
        super(context);
    }

    @Override
    public void handleMessage(int action, Object... values) {
        switch (action) {
            case IDiyMessage.BANNER_ACTION:
                onModelChanged(action, loadBanner((Integer) values[0]));
                break;
            case IDiyMessage.SECOND_KILL_ACTION:
                onModelChanged(action, loadSecondKill());
                break;
            case IDiyMessage.RECOMMAND_PRODUCT_ACTION:
                onModelChanged(action, loadRecommandProduct());
                break;
        }
    }

    private  List<RecommandProduct> loadRecommandProduct(){
        List<RecommandProduct> datas = new ArrayList<>();
        String jsonStr = NetworkUtil.doGet(NetworkConst.RECOMMAND_PRODUCT_URL);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()) {
            try {
                JSONObject jsonObject = new JSONObject(resultBean.getResult());
                String rowsJson = jsonObject.getString("rows");
                datas = JSON.parseArray(rowsJson, RecommandProduct.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return datas;


    }

    private List<SecKillBean> loadSecondKill() {
        List<SecKillBean> datas = new ArrayList<>();
        String jsonStr = NetworkUtil.doGet(NetworkConst.SECKILL_URL);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()) {
            try {
                JSONObject jsonObject = new JSONObject(resultBean.getResult());
                String rowsJson = jsonObject.getString("rows");
                datas = JSON.parseArray(rowsJson, SecKillBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return datas;
    }

    private List<BannerBean> loadBanner(int type) {
        List<BannerBean> datas = new ArrayList<>();
        if (type != 1 && type != 2) {
            return datas;
        }
        String jsonStr = NetworkUtil.doGet(NetworkConst.BANNER_URL + "?adKind=" + type);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()) {
            datas = JSON.parseArray(resultBean.getResult(), BannerBean.class);
        }
        return datas;
    }

}
