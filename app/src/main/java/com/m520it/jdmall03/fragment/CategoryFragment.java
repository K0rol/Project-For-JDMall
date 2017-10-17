package com.m520it.jdmall03.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.adapter.TopCategoryAdapter;
import com.m520it.jdmall03.bean.TopCategory;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.controller.CategoryController;
import com.m520it.jdmall03.ui.SubCategoryView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public class CategoryFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ListView mTopCategoryLv;
    private TopCategoryAdapter mTopCategoryAdapter;
    private SubCategoryView mSubCategoryView;

    @Override
    protected void handleUI(Message msg) {
        switch (msg.what) {
            case IDiyMessage.TOP_CATEGORY_ACTION:
                loadTopCategory((List<TopCategory>) msg.obj);
                break;
        }
    }

    private void loadTopCategory(List<TopCategory> datas){
        mTopCategoryAdapter.setDatas(datas);
        mTopCategoryAdapter.notifyDataSetChanged();
        if (datas.size()>0){
            mTopCategoryLv.performItemClick(null,0,0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        mController.sendAsyncMessage(IDiyMessage.TOP_CATEGORY_ACTION);
    }

    @Override
    protected void initController() {
        mController = new CategoryController(getContext());
        mController.setListener(this);
    }

    private void initView(View view) {
        mTopCategoryLv = (ListView) view.findViewById(R.id.top_category_lv);
        mTopCategoryAdapter = new TopCategoryAdapter(getActivity());
        mTopCategoryLv.setAdapter(mTopCategoryAdapter);
        mTopCategoryLv.setOnItemClickListener(this);

        mSubCategoryView = (SubCategoryView) view.findViewById(R.id.subcategory);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mTopCategoryAdapter.tabItem(position);
        TopCategory bean = mTopCategoryAdapter.getItem(position);
        mSubCategoryView.onShow(bean);
    }
}
