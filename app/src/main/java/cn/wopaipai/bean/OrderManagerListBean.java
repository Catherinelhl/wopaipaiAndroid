package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-18 17:01
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +   竞拍所得的数据类
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;
import java.util.List;

public class OrderManagerListBean implements Serializable {

    private int total;
    private List<OrderManagerBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OrderManagerBean> getData() {
        return data;
    }

    public void setData(List<OrderManagerBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrderManagerListBean{" +
                "total=" + total +
                ", data=" + data +
                '}';
    }
}
