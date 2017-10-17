package com.m520it.jdmall03.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.m520it.jdmall03.JDMallApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.activity.LoginActivity;
import com.m520it.jdmall03.activity.OrderListActivity;
import com.m520it.jdmall03.bean.LoginResult;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.NetworkConst;
import com.m520it.jdmall03.controller.UserController;
import com.m520it.jdmall03.ui.pop.AddressChooseDialog;
import com.m520it.jdmall03.utils.ActivityUtil;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public class MyJDFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mUserIconIv;
    private TextView mUserNameTv;
    private TextView mUserLevelTv;
    private TextView mWaitPayTv;
    private TextView mWaitReceiveTv;
    private Button mLogoutBtn;
    private LinearLayout mAddressId;
    private AddressChooseDialog mAddressChooseDialog;
    private LinearLayout mMimeOrder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myjd, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fillUserInfo();
        initController();
    }

    @Override
    protected void initController() {
        mController = new UserController(getActivity());
        mController.setListener(this);
    }

    private void fillUserInfo() {
        JDMallApplication app = (JDMallApplication) getActivity().getApplication();
        LoginResult userInfo = app.mUserInfo;
        if (userInfo != null) {
            mUserNameTv.setText(userInfo.getUserName());
            mUserLevelTv.setText(userInfo.getUserLeveStr());
            mWaitPayTv.setText(userInfo.getWaitPayCount() + "");
            mWaitReceiveTv.setText(userInfo.getWaitReceiveCount() + "");
            Glide
                    .with(this).
                    load(NetworkConst.BASE_URL + userInfo.getUserIcon())
                    .into(mUserIconIv);
        }
    }

    private void initView(View view) {
        mUserIconIv = (ImageView) view.findViewById(R.id.user_icon_iv);
        mUserNameTv = (TextView) view.findViewById(R.id.user_name_tv);
        mUserLevelTv = (TextView) view.findViewById(R.id.user_level_tv);
        mWaitPayTv = (TextView) view.findViewById(R.id.wait_pay_tv);
        mWaitReceiveTv = (TextView) view.findViewById(R.id.wait_receive_tv);
        mLogoutBtn = (Button) view.findViewById(R.id.logout_btn);
        mAddressId = (LinearLayout) view.findViewById(R.id.address_id);

        mWaitPayTv.setOnClickListener(this);
        mWaitReceiveTv.setOnClickListener(this);
        mLogoutBtn.setOnClickListener(this);
        mAddressId.setOnClickListener(this);
        mMimeOrder = (LinearLayout) view.findViewById(R.id.mime_order);
        mMimeOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mime_order:
                ActivityUtil.startNew(getActivity(), OrderListActivity.class,false);
                break;
            case R.id.wait_pay_tv:

                break;
            case R.id.wait_receive_tv:

                break;
            case R.id.logout_btn:
                logout();
                break;
            case R.id.address_id:
                if (mAddressChooseDialog == null) {
                    mAddressChooseDialog = new AddressChooseDialog(getActivity());
                }
                mAddressChooseDialog.show();
                break;
        }
    }

    private void logout() {
        mController.sendAsyncMessage(IDiyMessage.CLEAR_ACCOUNT_ACTION);
        ActivityUtil.startNew(getActivity(), LoginActivity.class, true);
    }
}
