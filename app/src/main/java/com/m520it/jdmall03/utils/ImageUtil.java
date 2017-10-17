package com.m520it.jdmall03.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.m520it.jdmall03.constant.NetworkConst;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class ImageUtil {

    public static void loadImage(Context c,String subImageUrl,ImageView iv){
        Glide.with(c)
                .load(NetworkConst.BASE_URL+subImageUrl)
                .into(iv);
    }

}
