package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-15 12:54
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +   数据类：所有竞拍商品
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuctionCommoditiesBean implements Serializable {
    private int total;
    private Object attachValue;

    private ArrayList<AuctionInfoBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getAttachValue() {
        return attachValue;
    }

    public void setAttachValue(Object attachValue) {
        this.attachValue = attachValue;
    }

    public List<AuctionInfoBean> getData() {
        return data;
    }

    public void setData(ArrayList<AuctionInfoBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AuctionCommoditiesBean{" +
                "total=" + total +
                ", attachValue=" + attachValue +
                ", data=" + data +
                '}';
    }
}
