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
+ since        +   2019-06-19 19:20
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.requester
+--------------+---------------------------------
+ description  +  竞拍
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object AuctionRequester {
    /**
     *
     *确认交易密码
     * @param body
     */
    fun confirmTradePassword(body: RequestBody): Observable<ResponseJson?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java)
            .confirmTradePwd(body)
    }

    /**
     *
     *获取竞拍产品列表
     * @param body
     */
    fun getAuctionList(
        MinPrice: Double,
        MaxPrice: Double,
        Type: Int,
        PageSize: Int,
        PageIndex: Int, sign: String
    ): Observable<ResponseJson?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java)
            .getAuctionList(MinPrice, MaxPrice, Type, PageSize, PageIndex, sign)
    }

    /**
     *获取竞拍产品详情
     */
    fun getAuctionDetail(
        PassportId: Int, AuctionId: Int, sign: String
    ): Observable<ResponseJson?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java)
            .getAuctionDetail(PassportId, AuctionId, sign)
    }

    /**
     * 获取竞拍记录数据，包含出价记录和分成记录
     */
    fun getAuctionRecord(
        PassportId: Int, AuctionId: Int, PageSize: Int,
        PageIndex: Int, sign: String
    ): Observable<ResponseJson?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java)
            .getAuctionRecord(PassportId, AuctionId, PageSize, PageIndex, sign)
    }

    /**
     * 获取最新竞拍记录数据，包含出价记录和分成记录，并自动更新在线时间
     */
    fun getLatestAuctionRecords(
        PassportId: Int, AuctionId: Int, OfferRecordId: Int, DivideRecordId: Int, sign: String
    ): Observable<ResponseJson?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java)
            .getLatestAuctionRecords(PassportId, AuctionId, OfferRecordId, DivideRecordId, sign)
    }

    /**
     *出价竞拍
     */
    fun offerPrice(
        body: RequestBody
    ): Observable<ResponseJson?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java)
            .offerPrice(body)
    }

    /**
     *解冻押金
     */
    fun unfreeze(
        body: RequestBody
    ): Observable<ResponseJson?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java)
            .unFreeze(body)
    }

}