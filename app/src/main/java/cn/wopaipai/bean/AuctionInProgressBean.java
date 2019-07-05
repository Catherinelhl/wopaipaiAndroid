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
+ description  +   正在竞拍的物品
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

public class AuctionInProgressBean extends BaseAuctionCommodityBean {
    private String startAuctionPrice;
    private String joinedPersonNumber;
    private boolean isStartAuction;
    private String currentPrice;

    public AuctionInProgressBean() {
        super();
    }

    public AuctionInProgressBean(String name, String auctionCover, String startAuctionPrice, String joinedPersonNumber
            , String auctionTime, boolean isStartAuction, String currentPrice) {
        super(name,startAuctionPrice,auctionTime,auctionCover);
        this.currentPrice = currentPrice;
        this.startAuctionPrice = startAuctionPrice;
        this.joinedPersonNumber = joinedPersonNumber;
        this.isStartAuction = isStartAuction;
    }

    public String getStartAuctionPrice() {
        return startAuctionPrice;
    }

    public void setStartAuctionPrice(String startAuctionPrice) {
        this.startAuctionPrice = startAuctionPrice;
    }

    public String getJoinedPersonNumber() {
        return joinedPersonNumber;
    }

    public void setJoinedPersonNumber(String joinedPersonNumber) {
        this.joinedPersonNumber = joinedPersonNumber;
    }


    public boolean isStartAuction() {
        return isStartAuction;
    }

    public void setStartAuction(boolean startAuction) {
        isStartAuction = startAuction;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return "AuctionInProgressBean{" +
                "startAuctionPrice='" + startAuctionPrice + '\'' +
                ", joinedPersonNumber='" + joinedPersonNumber + '\'' +
                ", isStartAuction=" + isStartAuction +
                ", currentPrice='" + currentPrice + '\'' +
                '}';
    }
}
