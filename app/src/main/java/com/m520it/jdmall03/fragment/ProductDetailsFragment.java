package com.m520it.jdmall03.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.activity.ProductDetailsActivity;
import com.m520it.jdmall03.constant.NetworkConst;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public class ProductDetailsFragment extends BaseFragment {

    private WebView mProductWv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View view) {
        //空气巧克力才有数据
        mProductWv = (WebView) view.findViewById(R.id.product_wv);
        mProductWv.getSettings().setJavaScriptEnabled(true);
        ProductDetailsActivity activity = (ProductDetailsActivity) getActivity();
        mProductWv.loadUrl(NetworkConst.PRODUCT_DETAILS_URL+"?productId="+activity.mPid);
    }
}
