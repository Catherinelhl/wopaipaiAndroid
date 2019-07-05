package cn.wopaipai.requester

import cn.catherine.token.http.retrofit.RetrofitFactory
import cn.wopaipai.gson.ResponseJson
import cn.wopaipai.http.HttpApi
import io.reactivex.Observable

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-25 10:28
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.requester
+--------------+---------------------------------
+ description  +  订单管理相关
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object OrderManagerRequester {

    /**
     * 竞拍获得接口
     */
    fun getAuctionObtain(
        PassportId: Int,
        PageSize: Int,
        PageIndex: Int,
        sign: String
    ): Observable<ResponseJson?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java).getAuctionObtain(PassportId,PageSize,PageIndex,sign)
    }
}