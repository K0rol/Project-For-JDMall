package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.RProductList;
import com.m520it.jdmall03.utils.ImageUtil;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class ProductListAdapter extends JDBaseAdapter<RProductList> {

    public ProductListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.product_lv_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RProductList bean = mDatas.get(position);
        ImageUtil.loadImage(mContext,bean.getIconUrl(),holder.mProductIv);
        holder.mNameTv.setText(bean.getName());
        holder.mPriceTv.setText("¥ "+bean.getPrice());
        holder.mCommrateTv.setText(bean.getCommentCount()+"条评价  好评率"+bean.getFavcomRate()+"%");
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return mDatas!=null?mDatas.get(position).getId():null;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView mProductIv;
        public TextView mNameTv;
        public TextView mCommrateTv;
        public TextView mPriceTv;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mProductIv = (ImageView) rootView.findViewById(R.id.product_iv);
            this.mNameTv = (TextView) rootView.findViewById(R.id.name_tv);
            this.mCommrateTv = (TextView) rootView.findViewById(R.id.commrate_tv);
            this.mPriceTv = (TextView) rootView.findViewById(R.id.price_tv);
        }

    }
}
