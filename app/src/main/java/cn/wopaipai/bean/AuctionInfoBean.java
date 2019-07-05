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
+ description  +   竞拍的物品
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class AuctionInfoBean implements Serializable {

    private int auctionId;//竞拍Id
    private String productName;//产品名称
    private Double securityMoney;//保证金【竞拍门槛，余额大于才给进场】
    private Double startingPrice;//起拍价
    private Double currentPrice;//当前价格
    private String imageUrl;//产品图片
    private int roomCount;//参加人数
    private String auctionTime;//未竞拍，开始时间【客户端倒计时算法：AuctionTime减去CurrentTime】":"2019-06-19T10:22:13.338Z",
    private String endTime;//正在竞拍结束时间【客户端倒计时算法：EndTime减去CurrentTime】":"2019-06-19T10:22:13.338Z",
    private String currentTime;//服务器当前时间":"2019-06-19T10:22:13.338Z"


    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getSecurityMoney() {
        return securityMoney;
    }

    public void setSecurityMoney(Double securityMoney) {
        this.securityMoney = securityMoney;
    }

    public Double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public String getAuctionTime() {
        return auctionTime;
    }

    public void setAuctionTime(String auctionTime) {
        this.auctionTime = auctionTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public String toString() {
        return "AuctionInfoBean{" +
                "auctionId=" + auctionId +
                ", productName='" + productName + '\'' +
                ", securityMoney=" + securityMoney +
                ", startingPrice=" + startingPrice +
                ", currentPrice=" + currentPrice +
                ", imageUrl='" + imageUrl + '\'' +
                ", roomCount=" + roomCount +
                ", auctionTime='" + auctionTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", currentTime='" + currentTime + '\'' +
                '}';
    }
}
