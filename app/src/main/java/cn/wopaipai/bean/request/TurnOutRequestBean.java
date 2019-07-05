package cn.wopaipai.bean.request;

import java.io.Serializable;

public class TurnOutRequestBean implements Serializable {

    private int passportId;    // int
    private String coinCode;    // 转出的币种
    private String to;  //转到谁那去
    private String tradePassword;    // 交易密码【传过来加个后缀比如：123123.x2019 其中.x2019是后缀】
    private double amount;    // 数量
    private String remark;    // 备注
    private String sign;    // 参数签名Sign=Md5Sign(xx参数)

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "TurnOutRequestBean{" +
                "passportId=" + passportId +
                ", coinCode='" + coinCode + '\'' +
                ", to='" + to + '\'' +
                ", tradePassword='" + tradePassword + '\'' +
                ", amount=" + amount +
                ", remark='" + remark + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
