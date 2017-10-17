package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.TopCategory;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class TopCategoryAdapter extends JDBaseAdapter<TopCategory> {

    private int mCurrenTabIndex=-1;

    public TopCategoryAdapter(Context context) {
        super(context);
    }

    /**
     * 设置某个item被点击的
     * */
    public void tabItem(int tabIndex){
        mCurrenTabIndex=tabIndex;
        notifyDataSetChanged();
    }

    class ViewHolder{
        TextView nameTv;
        View rightDividerView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.top_category_item,parent,false);
            holder=new ViewHolder();
            holder.nameTv= (TextView) convertView.findViewById(R.id.name_tv);
            holder.rightDividerView= convertView.findViewById(R.id.right_divider);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        TopCategory bean = mDatas.get(position);
        holder.nameTv.setText(bean.getName());
        if (position==mCurrenTabIndex){
            holder.nameTv.setTextColor(0xFF9D000B);
            holder.rightDividerView.setVisibility(View.INVISIBLE);
            holder.nameTv.setBackgroundResource(R.drawable.tongcheng_all_bg01);
        }else{
            holder.nameTv.setTextColor(Color.BLACK);
            holder.rightDividerView.setVisibility(View.VISIBLE);
            holder.nameTv.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }
}
