package com.m520it.jdmall03.ui.pop;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public abstract class JDBasePop {

    protected Context mContext;
    protected PopupWindow mPopupWindow;

    public JDBasePop(Context c) {
        mContext =c;
        initViews();
    }

    protected abstract void initViews();

    public abstract void onShow(View anchorView);

    public void onDismiss(){
        if (mPopupWindow!=null&&mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }
}
