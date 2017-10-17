package com.m520it.jdmall03.ui.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.adapter.AreaAdapter;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.controller.AddressController;
import com.m520it.jdmall03.listener.IChooseAreaCompleteListener;
import com.m520it.jdmall03.listener.IModelChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/6 0006
 * 选择省市区的弹出框
 */
public class ChooseAreaPop extends JDBasePop
        implements IModelChangeListener, AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView mProvinceLv;
    private ListView mCityLv;
    private ListView mAreaLv;
    private AreaAdapter mProvinceAdapter;
    private AreaAdapter mCityAdapter;
    private AreaAdapter mAreaAdapter;
    private AddressController.Area mTabProvince;
    private AddressController.Area mTabCity;
    private AddressController.Area mTabArea;

    private AddressController mController;
    private TextView mTipTv;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IDiyMessage.PROVINCE_ACTION:
                    showProvinceLv((List<AddressController.Area>) msg.obj);
                    break;
                case IDiyMessage.CITY_ACTION:
                    showCityLv((List<AddressController.Area>) msg.obj);
                    break;
                case IDiyMessage.AREA_ACTION:
                    showAresLv((List<AddressController.Area>) msg.obj);
                    break;
            }
        }
    };
    private TextView mSubmitTv;
    private IChooseAreaCompleteListener mListener;

    private void showProvinceLv(List<AddressController.Area> datas) {
        mProvinceAdapter.setDatas(datas);
        mProvinceAdapter.notifyDataSetChanged();
    }

    private void showCityLv(List<AddressController.Area> datas) {
        mCityAdapter.setDatas(datas);
        mCityAdapter.notifyDataSetChanged();
    }

    private void showAresLv(List<AddressController.Area> datas) {
        mAreaAdapter.setDatas(datas);
        mAreaAdapter.notifyDataSetChanged();
    }

    public ChooseAreaPop(Context c) {
        super(c);
        initController();
        requesetProvinceData();
    }

    private void initController() {
        mController = new AddressController(mContext);
        mController.setListener(this);
    }

    /**
     * 创建一个PopupWindow
     */
    @Override
    protected void initViews() {
        View contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.address_pop_view, null);
        initViews(contentView);
        // MATCH_PARENT=-1   WRAP_CONTENT= -2
        mPopupWindow = new PopupWindow(contentView, -1, -1);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.update();//刷新当前页面的所有设置
    }

    private void initViews(View rootView) {
        mProvinceLv = (ListView) rootView.findViewById(R.id.province_lv);
        mProvinceAdapter = new AreaAdapter(mContext);
        mProvinceLv.setAdapter(mProvinceAdapter);
        mProvinceLv.setOnItemClickListener(this);

        mCityLv = (ListView) rootView.findViewById(R.id.city_lv);
        mCityAdapter = new AreaAdapter(mContext);
        mCityLv.setAdapter(mCityAdapter);
        mCityLv.setOnItemClickListener(this);

        mAreaLv = (ListView) rootView.findViewById(R.id.area_lv);
        mAreaAdapter = new AreaAdapter(mContext);
        mAreaLv.setAdapter(mAreaAdapter);
        mAreaLv.setOnItemClickListener(this);

        mTipTv = (TextView) rootView.findViewById(R.id.tip_tv);
        mSubmitTv= (TextView) rootView.findViewById(R.id.submit_tv);
        mSubmitTv.setOnClickListener(this);
    }

    private void requesetProvinceData() {
        mController.sendAsyncMessage(IDiyMessage.PROVINCE_ACTION);
    }

    private void requesetCityData(String provinceCode) {
        mController.sendAsyncMessage(IDiyMessage.CITY_ACTION, provinceCode);
    }

    private void requesetAreaData(String cityCode) {
        mController.sendAsyncMessage(IDiyMessage.AREA_ACTION, cityCode);
    }

    @Override
    public void onShow(View anchorView) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void onModelChanged(int action, Object returnBean) {
        mHandler.obtainMessage(action, returnBean).sendToTarget();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.province_lv:
                requesetCityData(mProvinceAdapter.getItem(position).getCode());
                //清除区的数据
                showAresLv(new ArrayList<AddressController.Area>());
                //记录选中的对象
                mTabProvince = mProvinceAdapter.getItem(position);
                mTabCity = null;
                mTabArea = null;
                break;
            case R.id.city_lv:
                requesetAreaData(mCityAdapter.getItem(position).getCode());
                mTabCity = mCityAdapter.getItem(position);
                mTabArea = null;
                break;
            case R.id.area_lv:
                mTabArea = mAreaAdapter.getItem(position);
                break;
        }
        showCurrenTabInfo();
    }

    private void showCurrenTabInfo() {
        String tipStr = "";
        if (mTabProvince != null) {
            tipStr += mTabProvince.getName();
        }
        if (mTabCity != null) {
            tipStr += mTabCity.getName();
        }
        if (mTabArea != null) {
            tipStr += mTabArea.getName();
        }
        mTipTv.setText(tipStr);
    }

    /**
     * 确认按钮
     * */
    @Override
    public void onClick(View v) {
        if (mTabProvince==null||mTabCity==null||mTabArea==null){
            Toast.makeText(mContext,"请选择完整的省市区信息",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mListener!=null){
            mListener.onAreaChosen(mTabProvince,mTabCity,mTabArea);
        }
    }

    public void setListener(IChooseAreaCompleteListener listener) {
        this.mListener=listener;
    }
}
