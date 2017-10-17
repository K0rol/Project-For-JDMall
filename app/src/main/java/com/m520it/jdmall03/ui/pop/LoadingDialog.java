package com.m520it.jdmall03.ui.pop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.m520it.jdmall03.R;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class LoadingDialog extends Dialog  {

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog_view);
        ImageView iv= (ImageView) findViewById(R.id.loading_iv);
        AnimationDrawable drawable = (AnimationDrawable) iv.getDrawable();
        drawable.start();
    }


}
