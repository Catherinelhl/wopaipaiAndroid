package cn.wopaipai.bean;

import java.io.Serializable;

public class GetWalletListBean implements Serializable {
    /**
     * tradeRecordId : 0
     * flowType : 5   1(转入) 2(转出) 3(兑换) 4(竞拍) 5(红包) 6(购买) 7(托管) 8(竞拍成功押金扣除) 9（直推会员加价奖励）
     * typeName : 竞拍红包
     * createTime : 2019-06-25 12:50:25
     * amount : 100
     * amountFormat : +100.00000000
     */

    private int tradeRecordId;
    private int flowType;
    private String typeName;
    private String createTime;
    private double amount;
    private String amountFormat;

    public int getTradeRecordId() {
        return tradeRecordId;
    }

    public void setTradeRecordId(int tradeRecordId) {
        this.tradeRecordId = tradeRecordId;
    }

    public int getFlowType() {
        return flowType;
    }

    public void setFlowType(int flowType) {
        this.flowType = flowType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAmountFormat() {
        return amountFormat;
    }

    public void setAmountFormat(String amountFormat) {
        this.amountFormat = amountFormat;
    }

    @Override
    public String toString() {
        return "GetWalletListBean{" +
                "tradeRecordId=" + tradeRecordId +
                ", flowType=" + flowType +
                ", typeName='" + typeName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", amount=" + amount +
                ", amountFormat='" + amountFormat + '\'' +
                '}';
    }
}
