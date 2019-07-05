package cn.wopaipai.bean;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-13 23:30
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +  数据类：产品参数
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.io.Serializable;
import java.util.List;

public class ProductParamsBean implements Serializable {
    private String name;
    private List<String> paramsInfo;

    public ProductParamsBean(String name, List<String> paramsInfo) {
        super();
        this.name = name;
        this.paramsInfo = paramsInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParamsInfo() {
        return paramsInfo;
    }

    public void setParamsInfo(List<String> paramsInfo) {
        this.paramsInfo = paramsInfo;
    }

    @Override
    public String toString() {
        return "ProductParamsBean{" +
                "name='" + name + '\'' +
                ", paramsInfo=" + paramsInfo +
                '}';
    }
}
