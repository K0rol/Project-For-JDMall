package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.SecKillBean;
import com.m520it.jdmall03.constant.NetworkConst;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class SecondKillAdapter extends RecyclerView.Adapter<SecondKillAdapter.SecKillViewHolder> {

    private final Context mContext;
    private List<SecKillBean> mDatas;

    public void setDatas(List<SecKillBean> datas) {
        this.mDatas=datas;
    }

    public SecondKillAdapter(Context c) {
        mContext =c;
    }

    // 管理SecKillViewHolder还有布局
    @Override
    public SecKillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SecKillViewHolder holder = new SecKillViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.home_seckill_item, parent,false));
        return holder;
    }

    // 为每个item的子控件赋值
    @Override
    public void onBindViewHolder(SecKillViewHolder holder, int position) {
        Glide.with(mContext)
                .load(NetworkConst.BASE_URL+mDatas.get(position).getIconUrl())
                .into(holder.iconIv);
        holder.nowPriceTv.setText("¥ "+mDatas.get(position).getPointPrice());
        holder.originalPriceTv.setText(" ¥ "+mDatas.get(position).getAllPrice()+"  ");
        holder.originalPriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public int getItemCount() {
        return mDatas!=null?mDatas.size():0;
    }

    static class SecKillViewHolder extends RecyclerView.ViewHolder{

        ImageView iconIv;
        TextView nowPriceTv;
        TextView originalPriceTv;

        public SecKillViewHolder(View view)
        {
            super(view);
            iconIv = (ImageView) view.findViewById(R.id.image_iv);
            nowPriceTv = (TextView) view.findViewById(R.id.nowprice_tv);
            originalPriceTv = (TextView) view.findViewById(R.id.normalprice_tv);
        }
    }

}
