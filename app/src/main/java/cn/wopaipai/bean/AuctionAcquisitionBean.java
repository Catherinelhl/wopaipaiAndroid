package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-18 17:01
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +   竞拍所得的数据类
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class AuctionAcquisitionBean implements Serializable {

    private String name;//产品名称
    private String auctionPrice;//竞拍价格
    private String time;//时间
    private String currencyType;//当前币种

    private String cover;

    public AuctionAcquisitionBean(String name, String auctionPrice, String time, String currencyType) {
        super();
        this.name = name;
        this.auctionPrice = auctionPrice;
        this.time = time;
        this.currencyType = currencyType;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuctionPrice() {
        return auctionPrice;
    }

    public void setAuctionPrice(String auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    @Override
    public String toString() {
        return "AuctionAcquisitionBean{" +
                "name='" + name + '\'' +
                ", auctionPrice='" + auctionPrice + '\'' +
                ", time='" + time + '\'' +
                ", currencyType='" + currencyType + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
