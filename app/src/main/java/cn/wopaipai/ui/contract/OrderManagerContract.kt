package cn.wopaipai.ui.contract

import cn.wopaipai.base.BaseContract
import cn.wopaipai.bean.OrderManagerListBean

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-25 10:02
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.contract
+--------------+---------------------------------
+ description  +  订单管理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface OrderManagerContract {
    interface View : BaseContract.View {
        fun getAuctionObtainSuccess(orderManagerListBean:OrderManagerListBean?)
        fun getAuctionObtainFailure(msg:String)
    }
    interface Presenter : BaseContract.Presenter {
        //竞拍获得接口
        fun getAuctionObtain(PageSize:Int,PageIndex:Int)
    }
}