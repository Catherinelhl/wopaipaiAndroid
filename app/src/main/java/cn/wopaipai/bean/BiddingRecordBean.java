package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-16 10:20
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +   出价记录
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class BiddingRecordBean implements Serializable {

    private String name;// 名字
    private String currentPrice;//当前价格
    private String incrementPrice;//价格增量
    private String time;//时间
    private String currencyType;//当前的价格单位

    public BiddingRecordBean(String name, String currentPrice, String incrementPrice, String time, String currencyType) {
        super();
        this.name = name;
        this.currencyType = currencyType;
        this.currentPrice = currentPrice;
        this.incrementPrice = incrementPrice;
        this.time = time;

    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getIncrementPrice() {
        return incrementPrice;
    }

    public void setIncrementPrice(String incrementPrice) {
        this.incrementPrice = incrementPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BiddingRecordBean{" +
                "name='" + name + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", incrementPrice='" + incrementPrice + '\'' +
                ", time='" + time + '\'' +
                ", currencyType='" + currencyType + '\'' +
                '}';
    }
}
