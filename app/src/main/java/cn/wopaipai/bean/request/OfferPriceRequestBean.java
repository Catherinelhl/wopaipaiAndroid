package cn.wopaipai.bean.request;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-20 11:04
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean.request
+--------------+---------------------------------
+ description  +  出价竞拍
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class OfferPriceRequestBean implements Serializable {
    private int passportId;//通行证Id

    private int auctionId;// 竞拍Id

    private String tradePassword;// 交易密码【传过来加个后缀比如：123123.x2019 其中.x2019是后缀】

    private double offerPrice;// 出价

    private String sign;// 参数签名Sign=Md5Sign(xx参数)

    public OfferPriceRequestBean(int passportId, int auctionId, String tradePassword, double offerPrice, String sign) {
        super();
        this.passportId = passportId;
        this.auctionId = auctionId;
        this.tradePassword = tradePassword;
        this.offerPrice = offerPrice;
        this.sign = sign;
    }

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    public double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(double offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "OfferPriceRequestBean{" +
                "passportId=" + passportId +
                ", auctionId=" + auctionId +
                ", tradePassword='" + tradePassword + '\'' +
                ", offerPrice=" + offerPrice +
                ", sign='" + sign + '\'' +
                '}';
    }
}
