package com.m520it.jdmall03.bean;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class SProductListParams {

    public long categoryId;//(必须) 分类id
    public long brandId;//(可选) 品牌id
    public int filterType;//排序类型（1-综合 2-新品 3-评价）
    public int sortType;//排序条件（1-销量 2-价格高到低 3-价格低到高）
    public int deliverChoose;//0-代表无选择 1代表京东配送 2-代表货到付款 4-代表仅看有货...

    public static final int FILTER_ALL=1;
    public static final int FILTER_NEW=2;
    public static final int FILTER_COMMENT=3;

    public static final int SORT_NONE=0;
    public static final int SORT_SALE=1;
    public static final int SORT_PRICE_TOP=2;
    public static final int SORT_PRICE_DOWN=3;

    public static final int DELIVER_NONE=0;
    public static final int DELIVER_JDDELIVER=1;
    public static final int DELIVER_PAYWHENRECEIVE=2;
    public static final int DELIVER_HAS_STOCK=4;


    public SProductListParams(long categoryId, long brandId, int filterType, int sortType, int deliverChoose) {
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.filterType = filterType;
        this.sortType = sortType;
        this.deliverChoose = deliverChoose;
    }
}
