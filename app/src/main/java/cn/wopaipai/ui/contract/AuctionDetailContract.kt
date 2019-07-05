package cn.wopaipai.ui.contract

import cn.wopaipai.base.BaseContract
import cn.wopaipai.bean.AuctionDetailBean
import cn.wopaipai.bean.AuctionRecordBean
import cn.wopaipai.bean.OfferPriceBean

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-20 07:45
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.contract
+--------------+---------------------------------
+ description  +  竞拍详情
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface AuctionDetailContract {
    interface View : BaseContract.View {
        fun getAuctionDetailSuccess(auctionDetailBean: AuctionDetailBean)
        fun getAuctionDetailFailure(msg: String)

        fun getAuctionRecordSuccess(auctionRecordBean: AuctionRecordBean)
        fun getAuctionRecordDetailFailure(msg: String)


        fun getLatestAuctionRecordsSuccess(auctionRecordBean: AuctionRecordBean)
        fun getLatestAuctionRecordsFailure(msg: String)


        fun offerPriceSuccess(offerPriceBean: OfferPriceBean)
        fun offerPriceFailure(msg: String)

        fun unFreezeSuccess(msg: String)
        fun unFreezeFailure(msg: String)



    }

    interface Presenter : BaseContract.Presenter {
        // 获取竞拍产品详情
        fun getAuctionDetail(PassportId: Int, AuctionId: Int)

        //获取竞拍记录数据，包含出价记录和分成记录
        fun getAuctionRecord(PassportId: Int, AuctionId: Int, PageSize: Int, PageIndex: Int)

        // 获取最新竞拍记录数据，包含出价记录和分成记录，并自动更新在线时间
        fun getLatestAuctionRecords(
            PassportId: Int,
            AuctionId: Int,
            OfferRecordId: Int,
            DivideRecordId: Int
        )

        //出价竞拍
        fun offerPrice(PassportId: Int, AuctionId: Int, tradePassword: String, offerPrice: Double)

        //解除所有订阅
        fun disposableAll()

        // 解冻押金
        fun unFreeze()
    }
}