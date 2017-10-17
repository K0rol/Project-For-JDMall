package com.m520it.jdmall03.constant;

/**
 * Created by Administrator on 2017/8/13 0013.
 */

public class OrderStatus {

    public static final int ALL_ORDER = -2;//全部订单
    public static final int CANCEL_ORDER = -1;//取消订单
    public static final int WAIT_PAY_ORDER = 0;//等待支付
    public static final int WAIT_DELIVER_ORDER = 1;//等待发货
    public static final int WAIT_RECEIVE_ORDER = 2;//等待收货
    public static final int COMPLETE_ORDER = 3;//已完成订单

    public static String getOrderStatus(int status) {
        switch (status) {
            case ALL_ORDER:
                return "全部订单";
            case CANCEL_ORDER:
                return "取消订单";
            case WAIT_PAY_ORDER:
                return "等待支付";
            case WAIT_DELIVER_ORDER:
                return "等待发货";
            case WAIT_RECEIVE_ORDER:
                return "等待收货";
            case COMPLETE_ORDER:
                return "已完成";
        }
        return "";
    }

}
