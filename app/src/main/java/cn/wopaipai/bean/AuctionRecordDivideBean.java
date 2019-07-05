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
+ description  +  竞价分成记录
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class AuctionRecordDivideBean implements Serializable {

    private int divideRecordId;//分成记录Id

    private String nickName;//昵称

    private double amount;//  竞拍分成额度

    private int flowType;// 操作类型
    private String dateTime;// 记录时间

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getFlowType() {
        return flowType;
    }

    public void setFlowType(int flowType) {
        this.flowType = flowType;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getDivideRecordId() {
        return divideRecordId;
    }

    public void setDivideRecordId(int divideRecordId) {
        this.divideRecordId = divideRecordId;
    }

    @Override
    public String toString() {
        return "AuctionRecordDivideBean{" +
                "divideRecordId=" + divideRecordId +
                ", nickName='" + nickName + '\'' +
                ", amount=" + amount +
                ", flowType=" + flowType +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
