package com.m520it.jdmall03.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.Shopcar;
import com.m520it.jdmall03.listener.IShopcarDeleteListener;
import com.m520it.jdmall03.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class ShopcarAdapter extends JDBaseAdapter<Shopcar> {

    private IShopcarDeleteListener mListener;

    @Override
    public void setDatas(List<Shopcar> datas) {
        super.setDatas(datas);
    }

    public ShopcarAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.shopcars_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Shopcar bean = mDatas.get(position);
        ImageUtil.loadImage(mContext, bean.getPimageUrl(), holder.productIv);
        holder.nameTv.setText(bean.getPname());
        holder.priceTv.setText("¥ " + bean.getPprice());
        holder.buyCountTv.setText("x"+bean.getBuyCount());
        holder.cbx.setChecked(bean.isChecked);

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bean.getId()  userId===>发送一个网络请求  mController  回调监听器  Handler
                if (mListener!=null){
                    mListener.onShopcarDeleted(bean.getId());
                }
            }
        });
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return mDatas != null ? mDatas.get(position).getId() : null;
    }

    /**
     * 用户点击某个item
     * */
    public void tabItem(int position) {
        Shopcar bean = mDatas.get(position);
        bean.isChecked= !bean.isChecked;
        mDatas.set(position,bean);
        notifyDataSetChanged();
    }

    /**
     * 判断是否每个item都显示
     * */
    public boolean isAllChecked() {
        //假设一开始我们认为全部选中
        boolean flag=true;
        for(Shopcar bean:mDatas){
            //假如在遍历的过程中 有的item没被选中 就认为不是全选
            if (!bean.isChecked){
                flag=false;
            }
        }
        return flag;
    }

    /**
     * 设置所有的item是否全选
     * @param  checked 这个参数是用来告诉用户 全选按钮的选中状态的
     * */
    public void checkAll(boolean checked) {
        for(Shopcar bean:mDatas){
            bean.isChecked=checked;
        }
        notifyDataSetChanged();
    }

    /**
     * 获取选中商品的总价
     * */
    public double getCheckedTotalPrice(){
        double result=0;
        for(Shopcar bean:mDatas){
            if (bean.isChecked){
                result+=bean.getPprice()*bean.getBuyCount();
            }
        }
        return result;
    }

    /**
     * 获取选中商品的个数
     * */
    public int getCheckedTotalCount(){
        int result=0;
        for(Shopcar bean:mDatas){
            if (bean.isChecked){
                result++;
            }
        }
        return result;
    }

    /**
     * 获取选中商品列表
     * */
    public ArrayList<Shopcar> getCheckedListData(){
        ArrayList<Shopcar> result=new ArrayList<>();
        for(Shopcar bean:mDatas){
            if (bean.isChecked){
                result.add(bean);
            }
        }
        return result;
    }

    public void setListener(IShopcarDeleteListener listener) {
        this.mListener=listener;
    }

    /**
     * 删除某个对象
     * */
    public void delItem(long delShopcarId) {
        Shopcar currenDelBean=null;
        for(Shopcar bean:mDatas){
            if (bean.getId()==delShopcarId){
                currenDelBean=bean;
            }
        }
        if (currenDelBean!=null){
            mDatas.remove(currenDelBean);
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder {
        public View rootView;
        public CheckBox cbx;
        public ImageView productIv;
        public TextView nameTv;
        public TextView versionTv;
        public TextView priceTv;
        public TextView buyCountTv;
        public TextView deleteProduct;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.cbx = (CheckBox) rootView.findViewById(R.id.cbx);
            this.productIv = (ImageView) rootView.findViewById(R.id.product_iv);
            this.nameTv = (TextView) rootView.findViewById(R.id.name_tv);
            this.versionTv = (TextView) rootView.findViewById(R.id.version_tv);
            this.priceTv = (TextView) rootView.findViewById(R.id.price_tv);
            this.buyCountTv = (TextView) rootView.findViewById(R.id.buyCount_tv);
            this.deleteProduct = (TextView) rootView.findViewById(R.id.delete_product);
        }

    }
}
