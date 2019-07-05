package cn.wopaipai.ui.contract

import cn.wopaipai.base.BaseContract
import cn.wopaipai.bean.WalletResponsesBean

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-28 09:12
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.contract
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface GetUserContract {

    interface View : BaseContract.View {
        fun getUserWalletSuccess(walletResponsesBean: WalletResponsesBean)
        fun getUserWalletFailure(msg: String)
    }

    interface Presenter : BaseContract.Presenter {
        fun getUserWallet()
    }
}