package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.GoodComment;
import com.m520it.jdmall03.ui.RatingBar;
import com.m520it.jdmall03.utils.ImageUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class GoodCommentsAdapter extends JDBaseAdapter<GoodComment> {

    private TextView mNameTv;
    private TextView mContentTv;
    private LinearLayout mIamgesContainer;

    public GoodCommentsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fav_comment_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GoodComment bean = mDatas.get(position);
        holder.mRatingBar.setRating(bean.getRate());
        holder.mNameTv.setText(bean.getUserName());
        holder.mContentTv.setText(bean.getComment());
        initImageContainerLl(holder.mIamgesContainer,bean.getImgUrls());
        return convertView;
    }

    /**
     * 展示用户评论的图片
     */
    private void initImageContainerLl(LinearLayout container, String imgUrlJson) {
        //1.将JSON数据转换成数组
        List<String> imgUrls = JSON.parseArray(imgUrlJson, String.class);
        container.setVisibility(imgUrls.size() > 0 ? View.VISIBLE : View.GONE);
        //2.遍历每个子控件 并且给每个子控件赋值
        int childCount = container.getChildCount();
        for (int index = 0; index < childCount; index++) {
            container.getChildAt(index).setVisibility(View.INVISIBLE);
        }
        // 3.数据有多少个 就显示多少个ImageView
        for (int index = 0; index < imgUrls.size(); index++) {
            ImageView iv = (ImageView) container.getChildAt(index);
            ImageUtil.loadImage(mContext, imgUrls.get(index), iv);
            iv.setVisibility(View.VISIBLE);
        }
    }

    public static class ViewHolder {
        public View rootView;
        public TextView mNameTv;
        public TextView mContentTv;
        public LinearLayout mIamgesContainer;
        public RatingBar mRatingBar;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mNameTv = (TextView) rootView.findViewById(R.id.name_tv);
            this.mContentTv = (TextView) rootView.findViewById(R.id.content_tv);
            this.mIamgesContainer = (LinearLayout) rootView.findViewById(R.id.iamges_container);
            this.mRatingBar = (RatingBar) rootView.findViewById(R.id.rating_bar);
        }

    }
}
