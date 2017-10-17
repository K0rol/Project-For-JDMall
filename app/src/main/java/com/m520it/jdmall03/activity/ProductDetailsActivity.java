package com.m520it.jdmall03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.m520it.jdmall03.JDMallApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.adapter.ProductContainerAdapter;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.bean.SAdd2ShopcarParams;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.controller.ProductDetailsController;
import com.m520it.jdmall03.listener.OnPageChangeListenerAdapter;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class ProductDetailsActivity extends BaseActivity implements View.OnClickListener {

    public static final String PID_KEY = "ProductDetailsActivity::pid";
    public long mPid;
    public int mBuyCount;
    public String mPVersion;
    private View mDetailsView;
    private LinearLayout mDetailsLl;
    private View mIntroduceView;
    private LinearLayout mIntroduceLl;
    private View mCommentTv;
    private LinearLayout mCommentLl;
    private ViewPager mContainerVp;

    @Override
    protected void handleUI(Message msg) {
        if (msg.what==IDiyMessage.ADD2SHOPCAR_ACTION){
            Result resultBean= (Result) msg.obj;
            if (resultBean.isSuccess()){
                showToast("加入购物车成功!");
                finish();
            }else{
                showToast("加入购物车失败:"+resultBean.getErrorMsg());
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        initData();
        initController();
        initView();
    }

    @Override
    protected void initController() {
        mController=new ProductDetailsController(this);
        mController.setListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mPid = intent.getLongExtra(PID_KEY, 0);
        if (mPid == 0) {
            showToast("数据异常...");
            finish();
        }
    }

    @Override
    protected void initView() {
        mDetailsView = (View) findViewById(R.id.details_view);
        mDetailsLl = (LinearLayout) findViewById(R.id.details_ll);
        mDetailsLl.setOnClickListener(this);
        mIntroduceView = (View) findViewById(R.id.introduce_view);
        mIntroduceLl = (LinearLayout) findViewById(R.id.introduce_ll);
        mIntroduceLl.setOnClickListener(this);
        mCommentTv = (View) findViewById(R.id.comment_tv);
        mCommentLl = (LinearLayout) findViewById(R.id.comment_ll);
        mCommentLl.setOnClickListener(this);
        mContainerVp = (ViewPager) findViewById(R.id.vp);
        mContainerVp.setAdapter(new ProductContainerAdapter(getSupportFragmentManager()));
        mContainerVp.addOnPageChangeListener(new OnPageChangeListenerAdapter() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        changeIndicator(mIntroduceView);
                        break;
                    case 1:
                        changeIndicator(mDetailsView);
                        break;
                    case 2:
                        changeIndicator(mCommentTv);
                        break;
                }
            }
        });
    }

    private void changeIndicator(View v) {
        mIntroduceView.setVisibility(View.INVISIBLE);
        mDetailsView.setVisibility(View.INVISIBLE);
        mCommentTv.setVisibility(View.INVISIBLE);
        v.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.introduce_ll:
                mContainerVp.setCurrentItem(0, true);
                break;
            case R.id.details_ll:
                mContainerVp.setCurrentItem(1, true);
                break;
            case R.id.comment_ll:
                mContainerVp.setCurrentItem(2, true);
                break;
        }
    }

    public void add2ShopCar(View v){
        log(mPVersion+"   "+mBuyCount);
        if (mPVersion==null){
            showToast("请选择购买的型号");
            return;
        }
        if (mBuyCount==0){
            showToast("请设置需要购买的商品数量");
            return;
        }
        //开始实现网络请求...
        JDMallApplication app= (JDMallApplication) getApplication();
        SAdd2ShopcarParams params=new SAdd2ShopcarParams(
                app.mUserInfo.getId(),mPid,mBuyCount,mPVersion);
        mController.sendAsyncMessage(IDiyMessage.ADD2SHOPCAR_ACTION,params);
    }
}
