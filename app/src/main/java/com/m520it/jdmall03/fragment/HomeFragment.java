package com.m520it.jdmall03.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.activity.ProductDetailsActivity;
import com.m520it.jdmall03.adapter.BannerAdapter;
import com.m520it.jdmall03.adapter.RecommandProductAdapter;
import com.m520it.jdmall03.adapter.SecondKillAdapter;
import com.m520it.jdmall03.bean.BannerBean;
import com.m520it.jdmall03.bean.RecommandProduct;
import com.m520it.jdmall03.bean.SecKillBean;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.controller.HomeController;
import com.m520it.jdmall03.listener.OnPageChangeListenerAdapter;
import com.m520it.jdmall03.utils.BannerIndicatorUtil;
import com.m520it.jdmall03.utils.FixedViewUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ViewPager mAdVp;
    private LinearLayout mAdIndicator;
    private RelativeLayout mAdRl;
    private BannerAdapter mBannerAdapter;
    private Timer mTimer;
    private RecyclerView mSecondKillView;
    private SecondKillAdapter mSecondKillAdapter;
    private GridView mRecommendProductGv;
    private RecommandProductAdapter mRecommandProductAdapter;

    @Override
    protected void handleUI(Message msg) {
        switch (msg.what) {
            case IDiyMessage.BANNER_ACTION:
                handleBannerResult((List<BannerBean>) msg.obj);
                break;
            case IDiyMessage.SECOND_KILL_ACTION:
                handleSeckillResult((List<SecKillBean>) msg.obj);
                break;
            case IDiyMessage.RECOMMAND_PRODUCT_ACTION:
                handleRecommandProductResult((List<RecommandProduct>) msg.obj);
                break;
        }
    }

    private void handleRecommandProductResult(List<RecommandProduct> datas){
        mRecommandProductAdapter.setDatas(datas);
        mRecommandProductAdapter.notifyDataSetChanged();
        FixedViewUtil.setGridViewHeightBasedOnChildren(mRecommendProductGv,2);
    }

    private void handleSeckillResult(List<SecKillBean> datas) {
        mSecondKillAdapter.setDatas(datas);
        mSecondKillAdapter.notifyDataSetChanged();
    }

    private void handleBannerResult(final List<BannerBean> datas) {
        //1.展示广告信息
        mBannerAdapter.setDatas(datas);
        mBannerAdapter.notifyDataSetChanged();
        mAdRl.setVisibility(datas.size() > 0 ? View.VISIBLE : View.GONE);
        //2.创建指示器
        BannerIndicatorUtil.initItemViews(datas.size(), getActivity(), mAdIndicator);
        BannerIndicatorUtil.changeBannerIndicator(mAdIndicator, 0);
        //3.让广告动起来
        if (datas.size() <= 0) {
            return;
        }
        startScrollBanner(datas);
    }

    private void startScrollBanner(final List<BannerBean> datas) {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            //在子线程中运行
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        changeCurrentBannerIndex(datas);
                    }
                });
            }
        }, 3 * 1000, 3 * 1000);
    }

    private void changeCurrentBannerIndex(List<BannerBean> datas) {
        int lastIndex = mAdVp.getCurrentItem();
        lastIndex++;
        if (lastIndex > datas.size() - 1) {
            lastIndex = 0;
        }
        mAdVp.setCurrentItem(lastIndex, true);
    }

    private void stopScrollBanner() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopScrollBanner();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        mController.sendAsyncMessage(IDiyMessage.BANNER_ACTION, 1);
        mController.sendAsyncMessage(IDiyMessage.SECOND_KILL_ACTION);
        mController.sendAsyncMessage(IDiyMessage.RECOMMAND_PRODUCT_ACTION);
    }

    @Override
    protected void initController() {
        mController = new HomeController(getActivity());
        mController.setListener(this);
    }

    private void initView(View view) {
        mAdVp = (ViewPager) view.findViewById(R.id.ad_vp);
        mBannerAdapter = new BannerAdapter(getActivity());
        mAdVp.setAdapter(mBannerAdapter);
        mAdVp.addOnPageChangeListener(new OnPageChangeListenerAdapter() {
            @Override
            public void onPageSelected(int position) {
                BannerIndicatorUtil.changeBannerIndicator(mAdIndicator, position);
            }
        });
        mAdIndicator = (LinearLayout) view.findViewById(R.id.ad_indicator);
        mAdRl = (RelativeLayout) view.findViewById(R.id.ad_rl);

        mSecondKillView = (RecyclerView) view.findViewById(R.id.second_kill_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSecondKillView.setLayoutManager(layoutManager);
        mSecondKillAdapter = new SecondKillAdapter(getActivity());
        mSecondKillView.setAdapter(mSecondKillAdapter);
        mSecondKillView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.HORIZONTAL));

        mRecommendProductGv = (GridView) view.findViewById(R.id.recommend_gv);
        mRecommandProductAdapter = new RecommandProductAdapter(getActivity());
        mRecommendProductGv.setAdapter(mRecommandProductAdapter);
        mRecommendProductGv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(),ProductDetailsActivity.class);
        intent.putExtra(ProductDetailsActivity.PID_KEY,mRecommandProductAdapter.getItemId(position));
        startActivity(intent);
    }
}
