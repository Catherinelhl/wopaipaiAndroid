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
+ description  +   登录信息
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class LoginResultBean implements Serializable {

    private String phoneNumber;//手机号

    private int countryPhone;// 国家代码

    private String password;//密码
    private String mobileInfo;//手机设备信息
    private String sign;//加密

    public LoginResultBean(String phoneNumber, int countryPhone, String password, String mobileInfo, String sign) {
        super();
        this.phoneNumber = phoneNumber;
        this.countryPhone = countryPhone;
        this.password = password;
        this.mobileInfo = mobileInfo;
        this.sign = sign;

    }

    public String getMobileInfo() {
        return mobileInfo;
    }

    public void setMobileInfo(String mobileInfo) {
        this.mobileInfo = mobileInfo;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "LoginResultBean{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", countryPhone=" + countryPhone +
                ", password='" + password + '\'' +
                ", mobileInfo='" + mobileInfo + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
