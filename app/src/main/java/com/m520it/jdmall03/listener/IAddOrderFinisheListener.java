package com.m520it.jdmall03.listener;

import com.m520it.jdmall03.bean.RAddOrder;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public interface IAddOrderFinisheListener {

    void onOrderCanel(long oid);

    void onPayWhenReceiveConfirmed(long oid);

    void onPayOnlineConfirmed(RAddOrder mBean);

}
