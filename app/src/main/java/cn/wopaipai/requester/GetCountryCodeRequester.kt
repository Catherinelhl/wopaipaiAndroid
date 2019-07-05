package cn.wopaipai.requester

import cn.catherine.token.http.retrofit.RetrofitFactory
import cn.wopaipai.bean.CountryCodeBean
import cn.wopaipai.gson.ResponseAnyJson
import cn.wopaipai.gson.ResponseJson
import cn.wopaipai.http.HttpApi
import io.reactivex.Observable

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-15 18:29
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.requester
+--------------+---------------------------------
+ description  +  获取城市区号列表
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object GetCountryCodeRequester {

    /**
     * 获取城市区号列表
     *
     * @param body
     */
    fun getCountryCode(): Observable<ResponseAnyJson<List<CountryCodeBean.CountryCode>>?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java).getCountryCode()
    }

}