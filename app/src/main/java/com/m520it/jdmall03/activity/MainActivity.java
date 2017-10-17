package com.m520it.jdmall03.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.fragment.BaseFragment;
import com.m520it.jdmall03.fragment.CategoryFragment;
import com.m520it.jdmall03.fragment.HomeFragment;
import com.m520it.jdmall03.fragment.MyJDFragment;
import com.m520it.jdmall03.fragment.ShopcarFragment;
import com.m520it.jdmall03.listener.IBottomBarItemClickListener;
import com.m520it.jdmall03.ui.BottomBar;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class MainActivity extends BaseActivity implements IBottomBarItemClickListener {

    private BottomBar mBottomBar;
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void initView() {
        mFragments.add(new HomeFragment());
        mFragments.add(new CategoryFragment());
        mFragments.add(new ShopcarFragment());
        mFragments.add(new MyJDFragment());
        changeFragment(mFragments.get(0));
        mBottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        mBottomBar.setListener(this);
    }

    private void changeFragment(BaseFragment f) {
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        transaction.replace(R.id.top_bar, f);
        transaction.commit();
    }

    @Override
    public void onItemClick(int viewId) {
        switch (viewId) {
            case R.id.frag_main_ll:
                changeFragment(mFragments.get(0));
                break;
            case R.id.frag_category_ll:
                changeFragment(mFragments.get(1));
                break;
            case R.id.frag_shopcar_ll:
                changeFragment(mFragments.get(2));
                break;
            case R.id.frag_mine_ll:
                changeFragment(mFragments.get(3));
                break;
        }
    }


}
