package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-21 15:31
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +   首页所有的产品信息
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;
import java.util.List;

public class ProductListBean implements Serializable {
    private int total;
    private List<ProductsBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ProductsBean> getData() {
        return data;
    }

    public void setData(List<ProductsBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ProductListBean{" +
                "total=" + total +
                ", data=" + data +
                '}';
    }
}
