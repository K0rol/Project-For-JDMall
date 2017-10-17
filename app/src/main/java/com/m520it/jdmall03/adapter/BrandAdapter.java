package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.Brand;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class BrandAdapter extends JDBaseAdapter<Brand> {

    private int mCurrenTabIndex=-1;

    public BrandAdapter(Context context) {
        super(context);
    }

    /**
     * 如果选中了 直接给品牌id  如果没选中 就返回0
     * */
    public long getCheckBrandId() {
        return mCurrenTabIndex==-1?0:mDatas.get(mCurrenTabIndex).getId();
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
        Brand bean = mDatas.get(position);
        holder.setText(bean.getName());
        holder.setSelected(position==mCurrenTabIndex);
        return convertView;
    }
}
