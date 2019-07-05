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
+ description  +   获取竞拍记录数据，包含出价记录和分成记录
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class AuctionRecordBean implements Serializable {

    private String endTime;//当前竞拍品剩余的结束时间
    private String currentTime;//服务器当前的时间

    private boolean isAuctionEnd;//竞拍是否结束，返回true，要停止请求
    private String nickName;//成功竞拍到的用户昵称

    private int auctionCount;//竞价次数
    private int lookerCount;// 围观人数

    private AuctionRecordListBean<AuctionRecordOfferBean> offerRecordList;
    private AuctionRecordListBean<AuctionRecordDivideBean> divideRecordList;

    public AuctionRecordListBean<AuctionRecordOfferBean> getOfferRecordList() {
        return offerRecordList;
    }

    public void setOfferRecordList(AuctionRecordListBean<AuctionRecordOfferBean> offerRecordList) {
        this.offerRecordList = offerRecordList;
    }

    public AuctionRecordListBean<AuctionRecordDivideBean> getDivideRecordList() {
        return divideRecordList;
    }

    public void setDivideRecordList(AuctionRecordListBean<AuctionRecordDivideBean> divideRecordList) {
        this.divideRecordList = divideRecordList;
    }


    public int getAuctionCount() {
        return auctionCount;
    }

    public void setAuctionCount(int auctionCount) {
        this.auctionCount = auctionCount;
    }

    public int getLookerCount() {
        return lookerCount;
    }

    public void setLookerCount(int lookerCount) {
        this.lookerCount = lookerCount;
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

    public boolean isAuctionEnd() {
        return isAuctionEnd;
    }

    public void setAuctionEnd(boolean auctionEnd) {
        isAuctionEnd = auctionEnd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    @Override
    public String toString() {
        return "AuctionRecordBean{" +
                "endTime='" + endTime + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", isAuctionEnd=" + isAuctionEnd +
                ", nickName='" + nickName + '\'' +
                ", auctionCount=" + auctionCount +
                ", lookerCount=" + lookerCount +
                ", offerRecordList=" + offerRecordList +
                ", divideRecordList=" + divideRecordList +
                '}';
    }
}
