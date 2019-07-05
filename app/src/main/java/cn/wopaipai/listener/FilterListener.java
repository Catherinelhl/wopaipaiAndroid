package cn.wopaipai.listener;

import java.util.List;

import cn.wopaipai.bean.CountryCodeBean;


/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 21:38
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.listener
+--------------+---------------------------------
+ description  +  回调监听：输入框过滤器
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/
public interface FilterListener {
    void getFilterData(List<CountryCodeBean.CountryCode> countryCodes);
}
