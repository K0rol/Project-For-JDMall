package com.m520it.jdmall03.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.m520it.jdmall03.R;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class BannerIndicatorUtil {

    public static void initItemViews(int totalSize, Context c,LinearLayout mAdIndicator){
        for (int i = 0; i < totalSize; i++) {
            ImageView iv = getImageView(c);
            mAdIndicator.addView(iv);
        }
    }

    @NonNull
    private static ImageView getImageView(Context c) {
        ImageView iv = new ImageView(c);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
        params.setMargins(15, 0, 0, 0);
        iv.setLayoutParams(params);
        iv.setBackgroundResource(R.drawable.ad_indicator_bg);
        return iv;
    }

    public static void changeBannerIndicator(LinearLayout mAdIndicator, int newIndex) {
        int childCount = mAdIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            mAdIndicator.getChildAt(i).setSelected(i == newIndex);
        }
    }



}
