package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m520it.jdmall03.controller.AddressController;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class AreaAdapter extends JDBaseAdapter<AddressController.Area> {

    public AreaAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv=null;
        if (convertView==null){
            convertView=mInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            tv= (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(tv);
        }else{
            tv= (TextView) convertView.getTag();
        }
        tv.setText(mDatas.get(position).getName());
        return convertView;
    }
}
