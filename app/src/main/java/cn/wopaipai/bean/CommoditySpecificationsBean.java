package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-14 10:30
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +  数据类：商品规格
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;
import java.util.List;

public class CommoditySpecificationsBean implements Serializable {
    private String name;
    private String cover;
    private String price;
    private String inventoryNumber;//库存数量
    private List<String> commodityColors;

    public CommoditySpecificationsBean(String name, String cover, String price, String inventoryNumber, List<String> commodityColors) {
        super();
        this.name = name;
        this.cover = cover;
        this.price = price;
        this.inventoryNumber = inventoryNumber;
        this.commodityColors = commodityColors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public List<String> getCommodityColors() {
        return commodityColors;
    }

    public void setCommodityColors(List<String> commodityColors) {
        this.commodityColors = commodityColors;
    }

    @Override
    public String toString() {
        return "CommoditySpecificationsBean{" +
                "name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", price='" + price + '\'' +
                ", inventoryNumber='" + inventoryNumber + '\'' +
                ", commodityColors=" + commodityColors +
                '}';
    }
}
