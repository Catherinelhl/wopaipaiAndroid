package cn.wopaipai.bean.request;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-20 17:41
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean.request
+--------------+---------------------------------
+ description  +  确认交易密码
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class TradePasswordRequestBean implements Serializable {
    private int passportId;
    private String tradePassword;
    private String sign;


    public TradePasswordRequestBean(int passportId, String tradePassword, String sign) {
        super();
        this.passportId = passportId;
        this.tradePassword = tradePassword;
        this.sign = sign;
    }

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "TradePasswordRequestBean{" +
                "passportId=" + passportId +
                ", tradePassword='" + tradePassword + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
