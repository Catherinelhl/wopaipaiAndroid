package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-20 07:54
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +  数据类：获取竞拍详情
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;
import java.util.List;

public class AuctionDetailBean implements Serializable {

    private double balance;// 余额

    private String endTime;// 结束时间【客户端倒计时算法：EndTime减去CurrentTime】

    private String currentTime;// 服务器当前时间

    private String productName;// 标题

    private double rawPrice;// 产品原价

    private double currentPrice;// 当前价格

    private int auctionPeopleCount;// 竞价人数
    private int auctionCount;//竞价次数
    private int lookerCount;// 围观人数
    private double lowestInPrice;//最低加价

    private List<String> mainImages;//主图

    private ProductDetailBean detail;


    public int getAuctionCount() {
        return auctionCount;
    }

    public void setAuctionCount(int auctionCount) {
        this.auctionCount = auctionCount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getRawPrice() {
        return rawPrice;
    }

    public void setRawPrice(double rawPrice) {
        this.rawPrice = rawPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getAuctionPeopleCount() {
        return auctionPeopleCount;
    }

    public void setAuctionPeopleCount(int auctionPeopleCount) {
        this.auctionPeopleCount = auctionPeopleCount;
    }

    public int getLookerCount() {
        return lookerCount;
    }

    public void setLookerCount(int lookerCount) {
        this.lookerCount = lookerCount;
    }

    public ProductDetailBean getDetail() {
        return detail;
    }

    public void setDetail(ProductDetailBean detail) {
        this.detail = detail;
    }


    public List<String> getMainImages() {
        return mainImages;
    }

    public void setMainImages(List<String> mainImages) {
        this.mainImages = mainImages;
    }

    public double getLowestInPrice() {
        return lowestInPrice;
    }

    public void setLowestInPrice(double lowestInPrice) {
        this.lowestInPrice = lowestInPrice;
    }

    @Override
    public String toString() {
        return "AuctionDetailBean{" +
                "balance=" + balance +
                ", endTime='" + endTime + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", productName='" + productName + '\'' +
                ", rawPrice=" + rawPrice +
                ", currentPrice=" + currentPrice +
                ", auctionPeopleCount=" + auctionPeopleCount +
                ", auctionCount=" + auctionCount +
                ", lookerCount=" + lookerCount +
                ", lowestInPrice=" + lowestInPrice +
                ", mainImages=" + mainImages +
                ", detail=" + detail +
                '}';
    }
}
