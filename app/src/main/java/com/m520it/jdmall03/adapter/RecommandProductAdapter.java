package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.RecommandProduct;
import com.m520it.jdmall03.utils.ImageUtil;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class RecommandProductAdapter extends JDBaseAdapter<RecommandProduct> {

    public RecommandProductAdapter(Context context) {
        super(context);
    }

    class ViewHolder{
        ImageView productIv;
        TextView productNameTv;
        TextView priceTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.recommend_gv_item,parent,false);
            holder=new ViewHolder();
            holder.productIv= (ImageView) convertView.findViewById(R.id.product_iv);
            holder.productNameTv= (TextView) convertView.findViewById(R.id.name_tv);
            holder.priceTv= (TextView) convertView.findViewById(R.id.price_tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        RecommandProduct bean = mDatas.get(position);
        ImageUtil.loadImage(mContext,bean.getIconUrl(),holder.productIv);
        holder.productNameTv.setText(bean.getName());
        holder.priceTv.setText("¥ "+bean.getPrice());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return mDatas!=null?mDatas.get(position).getProductId():null;
    }
}
