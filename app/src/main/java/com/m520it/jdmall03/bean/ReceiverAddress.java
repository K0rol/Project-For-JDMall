package com.m520it.jdmall03.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/10 0010.
 *      Parcelable
 */
public class ReceiverAddress implements Serializable{

    private long id;
    private boolean isDefault;//默认地址
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * FastJson解析需要
     * */
    public boolean getIsDefault() {
        return isDefault;
    }
    /**
     * FastJson解析需要
     * */
    public void setIsDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
}
