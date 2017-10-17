package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.m520it.jdmall03.bean.BannerBean;
import com.m520it.jdmall03.constant.NetworkConst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class BannerAdapter extends PagerAdapter {

    private List<ImageView> mChilds;
    private Context mContext;

    public BannerAdapter(Context c) {
        this.mContext = c;
    }

    public void setDatas(List<BannerBean> datas) {
        mChilds = new ArrayList<>(datas.size());
        for (int i = 0; i < datas.size(); i++) {
            mChilds.add(buildItemView(datas.get(i)));
        }
    }

    @NonNull
    private ImageView buildItemView(BannerBean data) {
        ImageView iv = new ImageView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        iv.setLayoutParams(params);
        Glide
            .with(mContext)
            .load(NetworkConst.BASE_URL + data.getAdUrl())
            .into(iv);
        return iv;
    }

    @Override
    public int getCount() {
        return mChilds!=null?mChilds.size():0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = mChilds.get(position);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView iv = mChilds.get(position);
        container.removeView(iv);
    }

}
