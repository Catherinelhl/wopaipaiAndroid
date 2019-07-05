package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-15 12:54
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +   数据类：即将开始竞拍
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

public class AuctionStartByAMinuteBean extends BaseAuctionCommodityBean {
    private String auctionTime;
    private String currentPrice;
    private boolean isRemind;//是否已经提醒

    public AuctionStartByAMinuteBean() {
        super();
    }

    public AuctionStartByAMinuteBean(String name, String auctionCover, String startAuctionPrice, boolean isRemind
            , String auctionTime, String currentPrice) {
        super(name, startAuctionPrice, auctionTime, auctionCover);
        this.currentPrice = currentPrice;
        this.auctionTime = auctionTime;
        this.isRemind = isRemind;
    }


    public String getAuctionTime() {
        return auctionTime;
    }

    public void setAuctionTime(String auctionTime) {
        this.auctionTime = auctionTime;
    }


    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public boolean isRemind() {
        return isRemind;
    }

    public void setRemind(boolean remind) {
        isRemind = remind;
    }

    @Override
    public String toString() {
        return "AuctionStartByAMinuteBean{" +
                "auctionTime='" + auctionTime + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", isRemind=" + isRemind +
                '}';
    }
}
