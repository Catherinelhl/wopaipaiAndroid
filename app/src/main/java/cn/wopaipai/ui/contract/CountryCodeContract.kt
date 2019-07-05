package cn.wopaipai.ui.contract

import cn.wopaipai.base.BaseContract
import cn.wopaipai.bean.CountryCodeBean
import cn.wopaipai.bean.CountryCodeLocalBean
import java.util.ArrayList

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 17:47
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.contract
+--------------+---------------------------------
+ description  +   获取城市区号协议
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface CountryCodeContract {
    interface View : BaseContract.View {
        fun getCountryCodeLocalSuccess(countryCodesLocal: ArrayList<CountryCodeLocalBean.CountryCode>)
        fun getCountryCodeSuccess(countryCodeBean: ArrayList<CountryCodeBean.CountryCode>)
        fun getCountryCodeFailure(msg: String)
    }

    interface Presenter {
        fun getCountryCode(Language: String)
    }
}