package com.m520it.jdmall03.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.m520it.jdmall03.R;

/**
 * Created by Administrator on 2017/8/7 0007.
 * <p>
 * 帮助我们创建布局对应的控件
 */
public class TestActivity extends AppCompatActivity {

    private TextView mAllSort;
    private TextView mNewSort;
    private TextView mCommentSort;
    private View mLeftV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_sort_pop_view);
        initView();
    }

    private void initView() {
        mAllSort = (TextView) findViewById(R.id.all_sort);
        mNewSort = (TextView) findViewById(R.id.new_sort);
        mCommentSort = (TextView) findViewById(R.id.comment_sort);
        mLeftV = (View) findViewById(R.id.left_v);


    }


}
