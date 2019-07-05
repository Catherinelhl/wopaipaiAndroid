package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-20 12:22
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +  竞拍详情里面的商品信息
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;
import java.util.ArrayList;

public class ProductDetailBean implements Serializable {
    private String name;//产品名称

    private double price;// 价格

    private String brand;//品牌

    private String material;//  材质

    private String variety;//品种

    private String size;//大小，比如15cm x 15cm

    private float weight;//  重量，比如 200 (单位：克)

    private String color;// 颜色(必填，长度1-150个字符; 多个用,隔开，比如：蓝色,红色,黑色）

    private String introduce;// 简介

    private ArrayList<AuctionDetailImagesBean> images;//图片信息


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public ArrayList<AuctionDetailImagesBean> getImages() {
        return images;
    }

    public void setImages(ArrayList<AuctionDetailImagesBean> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "ProductDetailBean{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", material='" + material + '\'' +
                ", variety='" + variety + '\'' +
                ", size='" + size + '\'' +
                ", weight=" + weight +
                ", color='" + color + '\'' +
                ", introduce='" + introduce + '\'' +
                ", images=" + images +
                '}';
    }
}
