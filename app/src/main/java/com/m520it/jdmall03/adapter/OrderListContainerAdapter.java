package com.m520it.jdmall03.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.m520it.jdmall03.fragment.AllOrderFragment;
import com.m520it.jdmall03.fragment.BaseFragment;
import com.m520it.jdmall03.fragment.CompleteOrderFragment;
import com.m520it.jdmall03.fragment.WaitPayOrderFragment;
import com.m520it.jdmall03.fragment.WaitReceiveOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class OrderListContainerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments=new ArrayList<>();

    public OrderListContainerAdapter(FragmentManager fm) {
        super(fm);
        mFragments.add(new AllOrderFragment());
        mFragments.add(new WaitPayOrderFragment());
        mFragments.add(new WaitReceiveOrderFragment());
        mFragments.add(new CompleteOrderFragment());
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
