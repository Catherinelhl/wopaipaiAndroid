package cn.wopaipai.ui.contract

import cn.wopaipai.base.BaseContract
import cn.wopaipai.bean.AuctionCommoditiesBean

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-19 18:01
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.contract
+--------------+---------------------------------
+ description  +  竞拍
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface AuctionContract {
    interface View : BaseContract.View {
        fun getAuctionListSuccess(auctionCommoditiesBean: AuctionCommoditiesBean)
        fun getAuctionListFailure(msg: String)
        fun noMoreData()

        fun confirmTradePasswordSuccess(msg: String)
        fun confirmTradePasswordFailure(msg: String)
    }

    interface Presenter {
        //获取竞拍产品列表
        fun getAuctionList(
            MinPrice: Double,
            MaxPrice: Double,
            Type: Int,
            PageSize: Int,
            PageIndex: Int
        )

        //确认交易密码
        fun confirmTradePassword(tradePassword: String)
    }
}