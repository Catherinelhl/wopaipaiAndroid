package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-20 14:47
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +  竞价出价记录
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class AuctionRecordOfferBean implements Serializable {

    private int offerRecordId;//出价记录Id

    private String nickName;//昵称

    private double offerPrice;//  出价

    private double currentPrice;// 当前价格

    public int getOfferRecordId() {
        return offerRecordId;
    }

    public void setOfferRecordId(int offerRecordId) {
        this.offerRecordId = offerRecordId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(double offerPrice) {
        this.offerPrice = offerPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return "AuctionRecordOfferBean{" +
                "offerRecordId=" + offerRecordId +
                ", nickName='" + nickName + '\'' +
                ", offerPrice=" + offerPrice +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
