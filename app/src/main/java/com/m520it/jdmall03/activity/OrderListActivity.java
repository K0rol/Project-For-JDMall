package com.m520it.jdmall03.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.adapter.OrderListContainerAdapter;
import com.m520it.jdmall03.listener.OnPageChangeListenerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/13 0013.
 */
public class OrderListActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<View> mIndicators=new ArrayList<>();
    private ArrayList<LinearLayout> mIndicatorsContainer=new ArrayList<>();
    private ViewPager mContainerVp;
    private OrderListContainerAdapter mContainerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initView();
    }

    @Override
    protected void initView() {
        //看到的下划线
        mIndicators.add(findViewById(R.id.all_order_view));
        mIndicators.add(findViewById(R.id.wait_pay_view));
        mIndicators.add(findViewById(R.id.wait_receive_view));
        mIndicators.add(findViewById(R.id.wait_sure_view));
        //整体的item指示器容器
        mIndicatorsContainer.add((LinearLayout) findViewById(R.id.all_order_ll));
        mIndicatorsContainer.add((LinearLayout) findViewById(R.id.wait_pay_ll));
        mIndicatorsContainer.add((LinearLayout) findViewById(R.id.wait_receive_ll));
        mIndicatorsContainer.add((LinearLayout) findViewById(R.id.wait_sure_ll));
        for (LinearLayout itemContainer:mIndicatorsContainer){
            itemContainer.setOnClickListener(this);
        }

        mContainerVp = (ViewPager) findViewById(R.id.vp);
        mContainerAdapter = new OrderListContainerAdapter(getSupportFragmentManager());
        mContainerVp.setAdapter(mContainerAdapter);
        mContainerVp.addOnPageChangeListener(new OnPageChangeListenerAdapter() {
            @Override
            public void onPageSelected(int position) {
                changeIndicator(mIndicators.get(position));
            }
        });
    }

    private void changeIndicator(View v) {
        for (View indicator:mIndicators){
            indicator.setVisibility(View.INVISIBLE);
        }
        v.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        mContainerVp.setCurrentItem(mIndicatorsContainer.indexOf(v), true);
    }

}
