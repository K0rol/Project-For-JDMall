package com.m520it.jdmall03.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.m520it.jdmall03.activity.OrderDetailsActivity;
import com.m520it.jdmall03.adapter.OrderListBaseAdapter;
import com.m520it.jdmall03.bean.OrderList;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.controller.OrderController;
import com.m520it.jdmall03.ui.xlistview.XListView;

import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public abstract class BaseOrderFragment extends BaseFragment
        implements XListView.IXListViewListener, AdapterView.OnItemClickListener {

    protected XListView mListView;
    protected OrderListBaseAdapter mAdapter;
    protected View mNullView;

    @Override
    protected void handleUI(Message msg) {
        if (msg.what== IDiyMessage.ORDER_LIST_ACTION){
            showOrderList((List<OrderList>) msg.obj);
        }
    }

    private void showOrderList(List<OrderList> datas){
        mAdapter.setDatas(datas);
        mAdapter.notifyDataSetChanged();
        mListView.stopRefresh();
        mListView.setVisibility(!datas.isEmpty()?View.VISIBLE:View.GONE);
        mNullView.setVisibility(datas.isEmpty()?View.VISIBLE:View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        onRefresh();
    }

    @Override
    protected void initController() {
        mController=new OrderController(getActivity());
        mController.setListener(this);
    }

    protected String getCurrentTime(){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return formatter.format(new Date());
    }


    @Override
    public void onLoadMore() {

    }

    protected void initComponent(View containerView, int listViewId,
                               Class<? extends OrderListBaseAdapter> adapterClazz,
                               int nullViewId){
        mListView = (XListView) containerView.findViewById(listViewId);
        mListView.setRefreshTime(getCurrentTime());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setOnItemClickListener(this);
        //1.拿到构造器 //2.根据构造器创建对象
        try {
            Constructor<? extends OrderListBaseAdapter> constructor =
                    adapterClazz.getDeclaredConstructor(Context.class);
            mAdapter=constructor.newInstance(getActivity());
            mListView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mNullView = containerView.findViewById(nullViewId);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position!=0){
            Intent intent=new Intent(getActivity(), OrderDetailsActivity.class);
            OrderList bean = mAdapter.getItem(position - 1);
            intent.putExtra(OrderDetailsActivity.OID_KEY, bean.getOid());
            startActivity(intent);
        }
    }

}
