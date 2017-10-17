package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.OrderProductBean;
import com.m520it.jdmall03.utils.ImageUtil;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class OrderProductAdapter extends JDBaseAdapter<OrderProductBean> {

    public OrderProductAdapter(Context context) {
        super(context);
    }

    class ViewHolder{
        ImageView pIconIv;
        TextView pNameTv;
        TextView buyCountTv;
        TextView priceTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.order_details_products_item,parent,false);
            holder=new ViewHolder();
            holder.pIconIv= (ImageView) convertView.findViewById(R.id.p_icon_iv);
            holder.pNameTv= (TextView) convertView.findViewById(R.id.p_name_iv);
            holder.buyCountTv= (TextView) convertView.findViewById(R.id.p_buycount_iv);
            holder.priceTv= (TextView) convertView.findViewById(R.id.p_price_iv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        OrderProductBean bean = mDatas.get(position);
        ImageUtil.loadImage(mContext,bean.getPiconUrl(),holder.pIconIv);
        holder.pNameTv.setText(bean.getPname());
        holder.buyCountTv.setText("x "+bean.getBuyCount());
        holder.priceTv.setText("¥ "+bean.getAmount());
        return convertView;
    }
}
