package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.OrderList;
import com.m520it.jdmall03.constant.OrderStatus;
import com.m520it.jdmall03.listener.IConfirmReceiveListener;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class WaitReceiveAdapter extends OrderListBaseAdapter {

    private IConfirmReceiveListener mListener;

    public WaitReceiveAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.waitrecive_order_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final OrderList bean = mDatas.get(position);
        holder.orderNoTv.setText("订单编号:" + bean.getOrderNum());
        holder.orderStateTv.setText(OrderStatus.getOrderStatus(bean.getStatus()));
        showOrderProductIv(holder.pContainerLl, bean.getItems());
        holder.priceTv.setText("¥ " + bean.getTotalPrice());

        holder.doBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.onOrderReceived(bean.getOid());
                }
            }
        });
        return convertView;
    }

    public void setListener(IConfirmReceiveListener listener) {
        this.mListener=listener;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView orderNoTv;
        public TextView orderStateTv;
        public View divider;
        public LinearLayout pContainerLl;
        public View pDivider;
        public TextView priceTv;
        public Button doBtn;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.orderNoTv = (TextView) rootView.findViewById(R.id.order_no_tv);
            this.orderStateTv = (TextView) rootView.findViewById(R.id.order_state_tv);
            this.divider = (View) rootView.findViewById(R.id.divider);
            this.pContainerLl = (LinearLayout) rootView.findViewById(R.id.p_container_ll);
            this.pDivider = (View) rootView.findViewById(R.id.p_divider);
            this.priceTv = (TextView) rootView.findViewById(R.id.price_tv);
            this.doBtn = (Button) rootView.findViewById(R.id.do_btn);
        }
    }


}
