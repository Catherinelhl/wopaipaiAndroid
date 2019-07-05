package cn.wopaipai.requester

import cn.catherine.token.http.retrofit.RetrofitFactory
import cn.wopaipai.gson.ResponseJson
import cn.wopaipai.http.HttpApi
import io.reactivex.Observable
import okhttp3.RequestBody

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-19 01:43
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.requester
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object MainRequester {
    /**
     * 获取首页banner
     */
    fun getMainBanner(): Observable<ResponseJson?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java).getIndexBanners()
    }

    /**
     * 获取产品列表
     */
    fun getProducts(PageSize: Int, PageIndex: Int, sign: String): Observable<ResponseJson?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java).getProducts(PageSize,PageIndex,sign)
    }
}