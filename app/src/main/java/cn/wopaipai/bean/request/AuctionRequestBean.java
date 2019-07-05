package cn.wopaipai.bean.request;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-19 20:03
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +  竞拍请求的类
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;

public class AuctionRequestBean implements Serializable {
    private double MinPrice;// 筛选条件，最低价

    private double MaxPrice;//private double筛选条件，最高价
    private int Type;// 类型【1：正在竞拍，2：即将开始】
    private int PageSize;//  页大小
    private int PageIndex;//   页索引
    private String Sign;


    public AuctionRequestBean(double MinPrice, double MaxPrice, int Type, int PageSize, int PageIndex, String Sign) {
        super();
        this.MinPrice = MinPrice;
        this.MaxPrice = MaxPrice;
        this.Type = Type;
        this.PageSize = PageSize;
        this.PageIndex = PageIndex;
        this.Sign = Sign;

    }

    public double getMinPrice() {
        return MinPrice;
    }

    public void setMinPrice(double minPrice) {
        MinPrice = minPrice;
    }

    public double getMaxPrice() {
        return MaxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        MaxPrice = maxPrice;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    @Override
    public String toString() {
        return "AuctionRequestBean{" +
                "MinPrice=" + MinPrice +
                ", MaxPrice=" + MaxPrice +
                ", Type=" + Type +
                ", PageSize=" + PageSize +
                ", PageIndex=" + PageIndex +
                ", Sign='" + Sign + '\'' +
                '}';
    }
}
