package com.m520it.jdmall03.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.CommentCount;
import com.m520it.jdmall03.bean.CommentDetails;
import com.m520it.jdmall03.bean.CommentType;
import com.m520it.jdmall03.bean.GoodComment;
import com.m520it.jdmall03.bean.ProductInfo;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.bean.SAdd2ShopcarParams;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.NetworkConst;
import com.m520it.jdmall03.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class ProductDetailsController extends BaseController {

    public ProductDetailsController(Context context) {
        super(context);
    }

    @Override
    public void handleMessage(int action, Object... values) {
        switch (action) {
            case IDiyMessage.COMMENT_COUNT_ACTION:
                onModelChanged(action,loadCommentCount((Long)values[0]));
                break;
            case IDiyMessage.COMMENT_ACTION:
                onModelChanged(action,loadComments((Long)values[0],(Integer) values[1]));
                break;
            case IDiyMessage.PRODUCT_INFO_ACTION:
                onModelChanged(action,loadProductInfo((Long)values[0]));
                break;
            case IDiyMessage.PRODUCT_GOOD_COMMENT_ACTION:
                onModelChanged(action,loadGoodComments((Long)values[0]));
                break;
            case IDiyMessage.ADD2SHOPCAR_ACTION:
                onModelChanged(action,add2Shocar((SAdd2ShopcarParams)values[0]));
                break;
        }
    }

    private Result add2Shocar(SAdd2ShopcarParams paramsBean){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userId",paramsBean.userId+"");
        paramsMap.put("productId",paramsBean.productId+"");
        paramsMap.put("buyCount",paramsBean.buyCount+"");
        paramsMap.put("pVersion",paramsBean.pVersion);
        String jsonStr = NetworkUtil.doPost(NetworkConst.ADD2SHOPCAR_ACTION, paramsMap);
        return JSON.parseObject(jsonStr, Result.class);
    }

    private List<GoodComment> loadGoodComments(long pid){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("productId",pid+"");
        paramsMap.put("type","1");
        String jsonStr = NetworkUtil.doPost(NetworkConst.PRODUCT_GOOD_COMMENTS_URL, paramsMap);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(),GoodComment.class);
        }
        return new ArrayList<>();
    }

    private ProductInfo loadProductInfo(long pid){
        String jsonStr = NetworkUtil.doGet(NetworkConst.PRODUCT_INFO_URL + "?id=" + pid);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()) {
            return JSON.parseObject(resultBean.getResult(), ProductInfo.class);
        }
        return null;
    }

    private List<CommentDetails> loadComments(long pid, int commentType){
        HashMap<String, String> paramsMap = new HashMap<>();
        //评论的类型有:全部评论(0) 好评(1) 中评(2) 差评(3)  有图(0+hasImgCom=true)
        paramsMap.put("productId",pid+"");
        if (commentType!=CommentType.TYPE_HAS_IMG){
            paramsMap.put("type",commentType+"");
        }else{
            paramsMap.put("type",CommentType.TYPE_ALL+"");
            paramsMap.put("hasImgCom","true");
        }
        String jsonStr = NetworkUtil.doPost(NetworkConst.COMMENT_DETAILS_URL, paramsMap);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(),CommentDetails.class);
        }
        return new ArrayList<>();
    }

    private CommentCount loadCommentCount(long pid){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("productId",pid+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.COMMENT_COUNT_URL, paramsMap);
        Result resultBean = JSON.parseObject(jsonStr, Result.class);
        if (resultBean.isSuccess()){
            return JSON.parseObject(resultBean.getResult(),CommentCount.class);
        }
        return null;
    }
}
