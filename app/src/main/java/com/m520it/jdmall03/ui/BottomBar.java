package com.m520it.jdmall03.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.listener.IBottomBarItemClickListener;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class BottomBar extends LinearLayout implements View.OnClickListener {

    private ImageView mFragMainIv;
    private TextView mFragMain;
    private ImageView mFragCategoryIv;
    private TextView mFragCategory;
    private ImageView mFragShopcarIv;
    private TextView mFragShopcar;
    private ImageView mFragMineIv;
    private TextView mFragMine;
    private IBottomBarItemClickListener mListener;

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //在这个控件中  如何知道子控件已经初始化完毕了?
    // 当子控件加载完毕的时候被调用  只调用一次 类似onCreate()
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews() {
        findViewById(R.id.frag_main_ll).setOnClickListener(this);
        findViewById(R.id.frag_category_ll).setOnClickListener(this);
        findViewById(R.id.frag_shopcar_ll).setOnClickListener(this);
        findViewById(R.id.frag_mine_ll).setOnClickListener(this);
        mFragMainIv = (ImageView) findViewById(R.id.frag_main_iv);
        mFragMain = (TextView) findViewById(R.id.frag_main);
        mFragCategoryIv = (ImageView) findViewById(R.id.frag_category_iv);
        mFragCategory = (TextView) findViewById(R.id.frag_category);
        mFragShopcarIv = (ImageView) findViewById(R.id.frag_shopcar_iv);
        mFragShopcar = (TextView) findViewById(R.id.frag_shopcar);
        mFragMineIv = (ImageView) findViewById(R.id.frag_mine_iv);
        mFragMine = (TextView) findViewById(R.id.frag_mine);
        //模拟用户的点击事件
        findViewById(R.id.frag_main_ll).performClick();
    }

    public void changeIndicator(View v){
        mFragMainIv.setSelected(v.getId()==R.id.frag_main_ll);
        mFragMain.setSelected(v.getId()==R.id.frag_main_ll);
        mFragCategoryIv .setSelected(v.getId()==R.id.frag_category_ll);
        mFragCategory.setSelected(v.getId()==R.id.frag_category_ll);
        mFragShopcarIv .setSelected(v.getId()==R.id.frag_shopcar_ll);
        mFragShopcar.setSelected(v.getId()==R.id.frag_shopcar_ll);
        mFragMineIv.setSelected(v.getId()==R.id.frag_mine_ll);
        mFragMine.setSelected(v.getId()==R.id.frag_mine_ll);
    }

    @Override
    public void onClick(View v) {
        changeIndicator(v);
        if (mListener!=null){
            mListener.onItemClick(v.getId());
        }
    }


    public void setListener(IBottomBarItemClickListener listener) {
        this.mListener=listener;
    }
}
