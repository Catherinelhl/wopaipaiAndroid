package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-18 14:30
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +  发送重置密码验证码
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class ResetPasswordVerifyCodeBean implements Serializable {

    private String phoneNumber;
    private int countryPhone;
    private String ipAddress;
    private String sign;
    public ResetPasswordVerifyCodeBean(String phoneNumber, int countryPhone, String ipAddress, String sign) {
        super();
        this.phoneNumber = phoneNumber;
        this.countryPhone = countryPhone;
        this.ipAddress = ipAddress;
        this.sign = sign;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCountryPhone() {
        return countryPhone;
    }

    public void setCountryPhone(int countryPhone) {
        this.countryPhone = countryPhone;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "ResetPasswordVerifyCodeBean{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", countryPhone=" + countryPhone +
                ", ipAddress='" + ipAddress + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
