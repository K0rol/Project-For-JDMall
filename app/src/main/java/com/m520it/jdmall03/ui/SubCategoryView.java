package com.m520it.jdmall03.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.activity.ProductListActivity;
import com.m520it.jdmall03.bean.SubCategory;
import com.m520it.jdmall03.bean.TopCategory;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.controller.BaseController;
import com.m520it.jdmall03.controller.CategoryController;
import com.m520it.jdmall03.listener.IModelChangeListener;
import com.m520it.jdmall03.utils.ImageUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class SubCategoryView extends ScrollView implements IModelChangeListener {

    private LinearLayout mContainerLl;
    private TopCategory mTopCategory;
    private ImageView mBannerIv;
    private BaseController mController;
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == IDiyMessage.SUB_CATEGORY_ACTION) {
                showSubCategoryModule((List<SubCategory>) msg.obj);
            }
        }
    };

    public SubCategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //1.要拿到LinearLayout 才可以将图片控件放进去
        mContainerLl = (LinearLayout) findViewById(R.id.child_container_ll);
        //2.创建Banner模板
        createBannerView();
        //3.创建一次控制器
        initController();
    }

    private void initController() {
        mController = new CategoryController(getContext());
        mController.setListener(this);
    }

    /**
     * 每次点击一级分类都会调用这个方法
     */
    public void onShow(TopCategory bean) {
        mTopCategory = bean;
        removeLastAddedComponent();
        //2.创建一个图片控件 并且添加到mContainerLl容器中
        showNewestBanner();
        //3.开始发送网络请求==>2级分类的数据
        mController.sendAsyncMessage(IDiyMessage.SUB_CATEGORY_ACTION, mTopCategory.getId());
    }

    private void showNewestBanner() {
        ImageUtil.loadImage(getContext(), mTopCategory.getBannerUrl(), mBannerIv);
        mContainerLl.addView(mBannerIv);
    }

    /**
     * 移除上一次添加的组件
     */
    private void removeLastAddedComponent() {
        mContainerLl.removeAllViews();
    }

    /**
     * 创建顶部的图片控件
     */
    private void createBannerView() {
        mBannerIv = new ImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 100);
        mBannerIv.setLayoutParams(params);
        mBannerIv.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    private static final int MAX_COUNT_PERLINE = 3;

    /**
     * 展示所有的二级分类组件
     *      二级分类=标题+三级分类列表
     */
    private void showSubCategoryModule(List<SubCategory> secondCategoriesDatas) {
        for (SubCategory item : secondCategoriesDatas) {
            showSecondCategoryTitle(item.getName());
            // 显示3级分类
            List<TopCategory> thirdCategories = getThirdCategoryArr(item);
            //1.计算行数
            int datasCount = thirdCategories.size();
            int lines = calculateThirdCategoryLinesCount(datasCount);
            //2.创建行的容器
            for (int lineIndex = 0; lineIndex < lines; lineIndex++) {
                LinearLayout lineContainerLl = initThirdCategoryLines();
                //3.在行里面 计算是否要显示列
                if (lineIndex * MAX_COUNT_PERLINE <= datasCount - 1) {
                    //要显示第一列
                    addColumns(thirdCategories.get(lineIndex * MAX_COUNT_PERLINE), lineContainerLl);
                }
                if (lineIndex * MAX_COUNT_PERLINE + 1 <= datasCount - 1) {
                    //要显示第二列
                    addColumns(thirdCategories.get(lineIndex * MAX_COUNT_PERLINE + 1), lineContainerLl);
                }
                if (lineIndex * MAX_COUNT_PERLINE + 2 <= datasCount - 1) {
                    //要显示第三列
                    addColumns(thirdCategories.get(lineIndex * MAX_COUNT_PERLINE + 2), lineContainerLl);
                }
            }
        }
    }

    @NonNull
    private LinearLayout initThirdCategoryLines() {
        LinearLayout lineContainerLl = new LinearLayout(getContext());
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lineParams.setMargins(0, 10, 0, 0);
        lineContainerLl.setLayoutParams(lineParams);
        mContainerLl.addView(lineContainerLl);
        return lineContainerLl;
    }

    private int calculateThirdCategoryLinesCount(int datasCount) {
        int lines = datasCount / MAX_COUNT_PERLINE;
        lines = datasCount % MAX_COUNT_PERLINE == 0 ? lines : (lines + 1);
        return lines;
    }

    private List<TopCategory> getThirdCategoryArr(SubCategory item) {
        return JSON
                        .parseArray(item.getThirdCategory(), TopCategory.class);
    }

    /**
     * 显示二级分类的标题
     * */
    private void showSecondCategoryTitle(String titleName) {
        TextView secondTitleTv = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 10, 0, 0);
        secondTitleTv.setLayoutParams(params);
        secondTitleTv.getPaint().setFakeBoldText(true);
        secondTitleTv.setText(titleName);
        mContainerLl.addView(secondTitleTv);
    }

    private void addColumns(TopCategory thirdCategory, LinearLayout lineContainerLl) {
        //1.创建一个列的LinearLayout
        LinearLayout columnsLl = initThirdCategoryColumnsContainer(thirdCategory.getId());
        //2.创建一个图片
        initThirdCategoryIv(thirdCategory.getBannerUrl(), columnsLl);
        //3.创建一个文本
        initThirdCategoryTitleTv(thirdCategory.getName(), columnsLl);
        //4.再将大的容器添加到行里面去
        lineContainerLl.addView(columnsLl);
    }

    private void initThirdCategoryTitleTv(String titleName, LinearLayout columnsLl) {
        TextView thirdCategoryTitleTv = new TextView(getContext());
        LinearLayout.LayoutParams thirdCategoryTitleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        thirdCategoryTitleTv.setLayoutParams(thirdCategoryTitleParams);
        thirdCategoryTitleTv.setGravity(Gravity.CENTER);
        thirdCategoryTitleTv.setText(titleName);
        columnsLl.addView(thirdCategoryTitleTv);
    }

    private void initThirdCategoryIv(String bannerUrl, LinearLayout columnsLl) {
        ImageView categoryIv = new ImageView(getContext());
        LinearLayout.LayoutParams categoryIvParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                mContainerLl.getWidth() / 3
        );
        categoryIv.setLayoutParams(categoryIvParams);
        ImageUtil.loadImage(getContext(), bannerUrl, categoryIv);
        columnsLl.addView(categoryIv);
    }

    @NonNull
    private LinearLayout initThirdCategoryColumnsContainer(final long thirdCategoryId) {
        LinearLayout columnsLl = new LinearLayout(getContext());
        LinearLayout.LayoutParams columnsParams = new LinearLayout.LayoutParams(
                mContainerLl.getWidth() / 3,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        columnsLl.setOrientation(LinearLayout.VERTICAL);
        columnsLl.setLayoutParams(columnsParams);
        columnsLl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ProductListActivity.class);
                intent.putExtra(ProductListActivity.ThirdCategoryId,thirdCategoryId);
                intent.putExtra(ProductListActivity.TopCategoryId,mTopCategory.getId());
                getContext().startActivity(intent);
            }
        });
        return columnsLl;
    }

    @Override
    public void onModelChanged(int action, Object returnBean) {
        mHandler.obtainMessage(action, returnBean).sendToTarget();
    }
}
