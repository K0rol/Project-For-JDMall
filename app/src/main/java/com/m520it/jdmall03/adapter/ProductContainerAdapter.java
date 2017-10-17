package com.m520it.jdmall03.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.m520it.jdmall03.fragment.BaseFragment;
import com.m520it.jdmall03.fragment.ProductCommentFragment;
import com.m520it.jdmall03.fragment.ProductDetailsFragment;
import com.m520it.jdmall03.fragment.ProductInfoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class ProductContainerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments=new ArrayList<>();

    public ProductContainerAdapter(FragmentManager fm) {
        super(fm);
        mFragments.add(new ProductInfoFragment());
        mFragments.add(new ProductDetailsFragment());
        mFragments.add(new ProductCommentFragment());
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

}
