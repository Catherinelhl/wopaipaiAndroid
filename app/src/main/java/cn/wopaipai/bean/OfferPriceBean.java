package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-22 17:57
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +  出价成功返回的数据
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class OfferPriceBean implements Serializable {
    private Double balance;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "OfferPriceBean{" +
                "balance=" + balance +
                '}';
    }
}
