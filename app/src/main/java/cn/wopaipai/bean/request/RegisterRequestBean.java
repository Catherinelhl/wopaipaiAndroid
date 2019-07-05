package cn.wopaipai.bean.request;
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
+ description  +   注册信息
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class RegisterRequestBean implements Serializable {

    private String validCode;// 验证码

    private String sourceCode;// 来源推广码


    private String email;//邮箱


    private String tradePassword;// 交易密码

    private String phoneNumber;//手机号

    private int countryPhone;// 国家代码

    private String password;//密码
    private String sign;//加密

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
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
        return "RegisterRequestBean{" +
                "alidCode='" + validCode + '\'' +
                ", sourceCode='" + sourceCode + '\'' +
                ", email='" + email + '\'' +
                ", tradePassword=" + tradePassword +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", countryPhone=" + countryPhone +
                ", password='" + password + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
