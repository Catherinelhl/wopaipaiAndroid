package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-15 12:52
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +  商品竞拍的基类
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class BaseAuctionCommodityBean implements Serializable {
    private String name;//名字
    private String auctionTime;//竞拍时间
    private String auctionCover;//竞拍物品cover
    private String startAuctionPrice;//竞拍物品的起步价

    public BaseAuctionCommodityBean() {
        super();
    }

    public BaseAuctionCommodityBean(String name, String startAuctionPrice, String auctionTime, String auctionCover) {
        super();
        this.name = name;
        this.startAuctionPrice = startAuctionPrice;
        this.auctionCover = auctionCover;
        this.auctionTime = auctionTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartAuctionPrice() {
        return startAuctionPrice;
    }

    public void setStartAuctionPrice(String startAuctionPrice) {
        this.startAuctionPrice = startAuctionPrice;
    }

    public String getAuctionTime() {
        return auctionTime;
    }

    public void setAuctionTime(String auctionTime) {
        this.auctionTime = auctionTime;
    }

    public String getAuctionCover() {
        return auctionCover;
    }

    public void setAuctionCover(String auctionCover) {
        this.auctionCover = auctionCover;
    }

    @Override
    public String toString() {
        return "BaseAuctionCommodityBean{" +
                "name='" + name + '\'' +
                ", auctionTime='" + auctionTime + '\'' +
                ", auctionCover='" + auctionCover + '\'' +
                ", startAuctionPrice='" + startAuctionPrice + '\'' +
                '}';
    }
}
