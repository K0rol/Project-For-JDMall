package com.m520it.jdmall03.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.activity.ProductDetailsActivity;
import com.m520it.jdmall03.adapter.CommentsAdapter;
import com.m520it.jdmall03.bean.CommentCount;
import com.m520it.jdmall03.bean.CommentDetails;
import com.m520it.jdmall03.bean.CommentType;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.controller.ProductDetailsController;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public class ProductCommentFragment extends BaseFragment implements View.OnClickListener {

    private TextView mAllCommentTv;
    private LinearLayout mAllCommentLl;
    private TextView mPositiveCommentTv;
    private LinearLayout mPositiveCommentLl;
    private TextView mCenterCommentTv;
    private LinearLayout mCenterCommentLl;
    private TextView mNagetiveCommentTv;
    private LinearLayout mNagetiveCommentLl;
    private TextView mHasImageCommentTv;
    private LinearLayout mHasImageCommentLl;
    private ListView mCommentLv;
    private CommentsAdapter mCommentsAdapter;

    @Override
    protected void handleUI(Message msg) {
        switch (msg.what) {
            case IDiyMessage.COMMENT_COUNT_ACTION:
                showCommentCount(msg.obj);
                break;
            case IDiyMessage.COMMENT_ACTION:
                showCommentsLv((List<CommentDetails>) msg.obj);
                break;
        }
    }

    private void showCommentsLv(List<CommentDetails> datas){
        mCommentsAdapter.setDatas(datas);
        mCommentsAdapter.notifyDataSetChanged();
    }

    private void showCommentCount(Object o) {
        if (o == null) {
            return;
        }
        CommentCount bean = (CommentCount) o;
        mAllCommentTv.setText(bean.getAllComment() + "");
        mPositiveCommentTv.setText(bean.getPositiveCom() + "");
        mCenterCommentTv.setText(bean.getModerateCom() + "");
        mNagetiveCommentTv.setText(bean.getNegativeCom() + "");
        mHasImageCommentTv.setText(bean.getHasImgCom() + "");
        if (bean.getAllComment()>0){
            mAllCommentLl.performClick();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_comment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        ProductDetailsActivity activity = (ProductDetailsActivity) getActivity();
        mController.sendAsyncMessage(IDiyMessage.COMMENT_COUNT_ACTION, activity.mPid);
    }

    @Override
    protected void initController() {
        mController = new ProductDetailsController(getActivity());
        mController.setListener(this);
    }

    private void initView(View view) {
        mAllCommentTv = (TextView) view.findViewById(R.id.all_comment_tv);
        mAllCommentLl = (LinearLayout) view.findViewById(R.id.all_comment_ll);
        mPositiveCommentTv = (TextView) view.findViewById(R.id.positive_comment_tv);
        mPositiveCommentLl = (LinearLayout) view.findViewById(R.id.positive_comment_ll);
        mCenterCommentTv = (TextView) view.findViewById(R.id.center_comment_tv);
        mCenterCommentLl = (LinearLayout) view.findViewById(R.id.center_comment_ll);
        mNagetiveCommentTv = (TextView) view.findViewById(R.id.nagetive_comment_tv);
        mNagetiveCommentLl = (LinearLayout) view.findViewById(R.id.nagetive_comment_ll);
        mHasImageCommentTv = (TextView) view.findViewById(R.id.has_image_comment_tv);
        mHasImageCommentLl = (LinearLayout) view.findViewById(R.id.has_image_comment_ll);

        mAllCommentLl.setOnClickListener(this);
        mPositiveCommentLl.setOnClickListener(this);
        mCenterCommentLl.setOnClickListener(this);
        mNagetiveCommentLl.setOnClickListener(this);
        mHasImageCommentLl.setOnClickListener(this);

        mCommentLv = (ListView) view.findViewById(R.id.comment_lv);
        mCommentsAdapter=new CommentsAdapter(getActivity());
        mCommentLv.setAdapter(mCommentsAdapter);

    }

    @Override
    public void onClick(View v) {
        ProductDetailsActivity activity = (ProductDetailsActivity) getActivity();
        switch (v.getId()) {
            case R.id.all_comment_ll:
                mController.sendAsyncMessage(IDiyMessage.COMMENT_ACTION,
                        activity.mPid, CommentType.TYPE_ALL);
                break;
            case R.id.positive_comment_ll:
                mController.sendAsyncMessage(IDiyMessage.COMMENT_ACTION,
                        activity.mPid, CommentType.TYPE_POSI);
                break;
            case R.id.center_comment_ll:
                mController.sendAsyncMessage(IDiyMessage.COMMENT_ACTION,
                        activity.mPid, CommentType.TYPE_MODE);
                break;
            case R.id.nagetive_comment_ll:
                mController.sendAsyncMessage(IDiyMessage.COMMENT_ACTION,
                        activity.mPid, CommentType.TYPE_NEG);
                break;
            case R.id.has_image_comment_ll:
                mController.sendAsyncMessage(IDiyMessage.COMMENT_ACTION,
                        activity.mPid, CommentType.TYPE_HAS_IMG);
                break;
        }
    }
}
