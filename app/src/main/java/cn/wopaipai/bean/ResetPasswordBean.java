package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-18 14:51
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +  重设密码
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class ResetPasswordBean implements Serializable {

    private String phoneNumber;
    private int countryPhone;
    private String password;
    private String validCode;
    private String sign;

    public ResetPasswordBean(String phoneNumber, int countryPhone, String password, String validCode, String sign) {
        super();
        this.phoneNumber = phoneNumber;
        this.countryPhone = countryPhone;
        this.password = password;
        this.validCode = validCode;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "ResetPasswordBean{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", countryPhone=" + countryPhone +
                ", password='" + password + '\'' +
                ", validCode='" + validCode + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
