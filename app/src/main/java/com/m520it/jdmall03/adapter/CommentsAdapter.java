package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.CommentDetails;
import com.m520it.jdmall03.ui.RatingBar;
import com.m520it.jdmall03.utils.ImageUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class CommentsAdapter extends JDBaseAdapter<CommentDetails> {

    public CommentsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.comment_item_view, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommentDetails bean = mDatas.get(position);
        ImageUtil.loadImage(mContext, bean.getUserImg(), holder.iconIv);
        holder.mRatingBar.setRating(bean.getRate());
        holder.nameTv.setText(bean.getUserName());
        holder.timeTv.setText(bean.getCommentTime());
        holder.contentTv.setText(bean.getComment());
        holder.buytimeTv.setText("购买日期:" + bean.getBuyTime());
        holder.buyversionTv.setText("型号:" + bean.getProductType());
        holder.lovecountTv.setText("喜欢(" + bean.getLoveCount() + ")");
        holder.subcommentTv.setText("回复(" + bean.getSubComment() + ")");
        initImageContainerLl(holder.mImageContainerLl, bean.getImgUrls());
        return convertView;
    }

    /**
     * 展示用户评论的图片
     */
    private void initImageContainerLl(LinearLayout container, String imgUrlJson) {
        //1.将JSON数据转换成数组
        List<String> imgUrls = JSON.parseArray(imgUrlJson, String.class);
        container.setVisibility(imgUrls.size()>0?View.VISIBLE:View.GONE);
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
        public LinearLayout mImageContainerLl;
        public View rootView;
        public RatingBar mRatingBar;
        public ImageView iconIv;
        public TextView nameTv;
        public TextView timeTv;
        public TextView contentTv;
        public LinearLayout iamgesContainer;
        public TextView buytimeTv;
        public TextView buyversionTv;
        public TextView lovecountTv;
        public TextView subcommentTv;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mRatingBar = (RatingBar) rootView.findViewById(R.id.rating_bar);
            this.mImageContainerLl = (LinearLayout) rootView.findViewById(R.id.iamges_container);
            this.iconIv = (ImageView) rootView.findViewById(R.id.icon_iv);
            this.nameTv = (TextView) rootView.findViewById(R.id.name_tv);
            this.timeTv = (TextView) rootView.findViewById(R.id.time_tv);
            this.contentTv = (TextView) rootView.findViewById(R.id.content_tv);
            this.iamgesContainer = (LinearLayout) rootView.findViewById(R.id.iamges_container);
            this.buytimeTv = (TextView) rootView.findViewById(R.id.buytime_tv);
            this.buyversionTv = (TextView) rootView.findViewById(R.id.buyversion_tv);
            this.lovecountTv = (TextView) rootView.findViewById(R.id.lovecount_tv);
            this.subcommentTv = (TextView) rootView.findViewById(R.id.subcomment_tv);
        }

    }
}
