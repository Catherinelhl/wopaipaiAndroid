package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-16 17:48
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +  用户信息
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class UserResult implements Serializable {

    private int passportId;//通行证Id

    private String phoneNumber;//手机号

    private String countryCode;// 国家代码

    private int countryPhone;//  国家电话代码

    private Double balance;// 钱包ZBB余额

    private String qrImageUrl;// 二维码地址【不用理会，

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getCountryPhone() {
        return countryPhone;
    }

    public void setCountryPhone(int countryPhone) {
        this.countryPhone = countryPhone;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getQrImageUrl() {
        return qrImageUrl;
    }

    public void setQrImageUrl(String qrImageUrl) {
        this.qrImageUrl = qrImageUrl;
    }

    @Override
    public String toString() {
        return "UserResult{" +
                "passportId=" + passportId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryPhone=" + countryPhone +
                ", balance=" + balance +
                ", qrImageUrl='" + qrImageUrl + '\'' +
                '}';
    }
}
