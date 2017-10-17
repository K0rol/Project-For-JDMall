package com.m520it.jdmall03.utils;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FixedViewUtil {

	/**
	 * 根据子项目的个数重新设置GridViewHeight
	 * @param gv
	 * @param col
	 */
	public static void setGridViewHeightBasedOnChildren(GridView gv,int col) {

		ListAdapter listAdapter = gv.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i += col) {
			View listItem = listAdapter.getView(i, null, gv);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
			totalHeight += gv.getVerticalSpacing();
			if (i==listAdapter.getCount()-1) {
				totalHeight += gv.getVerticalSpacing();
			}
		}
		LayoutParams params = gv.getLayoutParams();
		params.height = totalHeight;
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		gv.setLayoutParams(params);
	}

	/**
	 * 根据子项目的个数重新设置ListViewHeight
	 */
	public static void setListViewHeightBasedOnChildren(ListView lv){
		//1.找到所对应的Adapter
		ListAdapter listAdapter = lv.getAdapter();
		// 2.最后计算的高度
	    int listViewHeight = 0;
		// 3.获取列表当前的数据
	    int adaptCount = listAdapter.getCount();
		// 4.遍历获取每个item的高度
	    for(int i=0;i<adaptCount;i++){  
	        View temp = listAdapter.getView(i,null,lv);  
	        temp.measure(0,0);  
	        listViewHeight += temp.getMeasuredHeight();
			listViewHeight += 10;
	    }
		listViewHeight -= 10;
	    //5.将计算好的高度重新设置到ListView里面
	    LayoutParams layoutParams = lv.getLayoutParams();  
	    layoutParams.width = LayoutParams.MATCH_PARENT;  
	    layoutParams.height = listViewHeight;  
	    lv.setLayoutParams(layoutParams);  
	}

}
