package com.m520it.jdmall03.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.m520it.jdmall03.JDMallApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.activity.SettleActivity;
import com.m520it.jdmall03.adapter.ShopcarAdapter;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.bean.Shopcar;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.controller.ShopcarController;
import com.m520it.jdmall03.listener.IShopcarDeleteListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public class ShopcarFragment extends BaseFragment
        implements View.OnClickListener, AdapterView.OnItemClickListener, IShopcarDeleteListener {

    private ListView mShopcarLv;
    private CheckBox mAllCbx;
    private TextView mAllMoneyTv;
    private TextView mSettleTv;
    private ShopcarAdapter mAdapter;
    private long mDelShopcarId;

    @Override
    protected void handleUI(Message msg) {
        switch (msg.what) {
            case IDiyMessage.SHOPCAR_LIST_ACTION:
                showShopcars((List<Shopcar>) msg.obj);
                break;
            case IDiyMessage.DEL_SHOPCAR_ITEM:
                showDelShopcarResult((Result) msg.obj);
                break;
        }
    }

    private void showDelShopcarResult(Result result){
        if (result.isSuccess()){
            showToast("删除商品成功!");
            if (mDelShopcarId!=0){
                mAdapter.delItem(mDelShopcarId);
                mDelShopcarId=0;
            }
            resetCheckProductInfo();
        }else{
            showToast(result.getErrorMsg());
        }
    }

    private void resetCheckProductInfo() {
        mAllMoneyTv.setText("总额: ￥ " + mAdapter.getCheckedTotalPrice());
        mSettleTv.setText("去结算(" + mAdapter.getCheckedTotalCount() + ")");
    }

    private void showShopcars(List<Shopcar> datas) {
        mAdapter.setDatas(datas);
        mAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopcar, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        JDMallApplication app = (JDMallApplication) getActivity().getApplication();
        mController.sendAsyncMessage(IDiyMessage.SHOPCAR_LIST_ACTION, app.mUserInfo.getId());
    }

    @Override
    protected void initController() {
        mController = new ShopcarController(getActivity());
        mController.setListener(this);
    }

    private void initView(View view) {
        mShopcarLv = (ListView) view.findViewById(R.id.shopcar_lv);
        mAdapter = new ShopcarAdapter(getActivity());
        mAdapter.setListener(this);
        mShopcarLv.setAdapter(mAdapter);
        mShopcarLv.setOnItemClickListener(this);

        mAllCbx = (CheckBox) view.findViewById(R.id.all_cbx);
        mAllCbx.setOnClickListener(this);
        mAllMoneyTv = (TextView) view.findViewById(R.id.all_money_tv);
        mSettleTv = (TextView) view.findViewById(R.id.settle_tv);
        mSettleTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settle_tv://结算按钮
                Intent intent=new Intent(getActivity(),SettleActivity.class);
                //1.获取选中的商品队列==总数
                ArrayList<Shopcar> checkedListData = mAdapter.getCheckedListData();
                //2.获取选中商品的总价
                double totalPrice = mAdapter.getCheckedTotalPrice();
                intent.putExtra(SettleActivity.CHECKDATAS_KEY,checkedListData);
                intent.putExtra(SettleActivity.TOTALPRICE_KEY,totalPrice);
                startActivity(intent);
                break;
            case R.id.all_cbx:
                mAdapter.checkAll(mAllCbx.isChecked());
                resetCheckProductInfo();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.tabItem(position);
        //判断是否全部选中
        mAllCbx.setChecked(mAdapter.isAllChecked());
        resetCheckProductInfo();
    }

    @Override
    public void onShopcarDeleted(long shopcarId) {
        //开始实现网络请求      userId   id
        JDMallApplication app = (JDMallApplication) getActivity().getApplication();
        mController.sendAsyncMessage(IDiyMessage.DEL_SHOPCAR_ITEM,
                app.mUserInfo.getId(), shopcarId);
        mDelShopcarId=shopcarId;
    }
}
