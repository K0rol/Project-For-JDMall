package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m520it.jdmall03.R;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class ProductVersionAdapter extends JDBaseAdapter<String> {

    private int mCurrenTabIndex=-1;

    public ProductVersionAdapter(Context context) {
        super(context);
    }

    /**
     * 设置某个item被点击的
     * */
    public void tabItem(int tabIndex){
        mCurrenTabIndex=tabIndex;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView holder=null;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.brand_item_layout,parent,false);
            holder= (TextView) convertView.findViewById(R.id.brand_tv);
            convertView.setTag(holder);
        }else{
            holder= (TextView) convertView.getTag();
        }
        String bean = mDatas.get(position);
        holder.setText(bean);
        holder.setSelected(position==mCurrenTabIndex);
        return convertView;
    }
}
