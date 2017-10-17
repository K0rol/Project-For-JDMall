package com.m520it.jdmall03.ui.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.listener.IProductSorChangeListener;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class ProductSortPop extends JDBasePop implements View.OnClickListener {

    private TextView mAllSort;
    private TextView mNewSort;
    private TextView mCommentSort;
    private View mLeftV;
    private IProductSorChangeListener mListener;

    public ProductSortPop(Context c) {
        super(c);
    }

    /**
     * 创建一个PopupWindow
     */
    @Override
    protected void initViews() {
        View contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.product_sort_pop_view, null);
        mAllSort = (TextView) contentView.findViewById(R.id.all_sort);
        mNewSort = (TextView) contentView.findViewById(R.id.new_sort);
        mCommentSort = (TextView) contentView.findViewById(R.id.comment_sort);
        mLeftV = (View) contentView.findViewById(R.id.left_v);

        mAllSort.setOnClickListener(this);
        mNewSort.setOnClickListener(this);
        mCommentSort.setOnClickListener(this);
        mLeftV.setOnClickListener(this);

        // MATCH_PARENT=-1   WRAP_CONTENT= -2
        mPopupWindow = new PopupWindow(contentView, -1, -1);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.update();//刷新当前页面的所有设置
    }

    @Override
    public void onClick(View v) {
        onDismiss();
        switch (v.getId()) {
            case R.id.all_sort:
            case R.id.new_sort:
            case R.id.comment_sort:
                if (mListener!=null){
                    mListener.onSortChanged(v.getId());
                }
                break;
        }
    }

    @Override
    public void onShow(View anchorView) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(anchorView, 0, 2);
        }
    }

    public void setListener(IProductSorChangeListener listener) {
        this.mListener=listener;
    }
}
