package cn.wopaipai.bean.request;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-21 14:23
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean.request
+--------------+---------------------------------
+ description  +   解冻押金请求
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class UnFreezeRequestBean implements Serializable {
    private int passportId;
    private String sign;

    public UnFreezeRequestBean(int passportId, String sign) {
        super();
        this.passportId = passportId;
        this.sign = sign;
    }

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "UnFreezeRequestBean{" +
                "passportId=" + passportId +
                ", sign='" + sign + '\'' +
                '}';
    }
}
