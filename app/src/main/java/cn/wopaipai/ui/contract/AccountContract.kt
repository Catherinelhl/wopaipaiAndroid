package cn.wopaipai.ui.contract

import cn.wopaipai.base.BaseContract
import cn.wopaipai.bean.ProfileBean

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-23 17:47
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.contract
+--------------+---------------------------------
+ description  +   我的
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface AccountContract {
    interface View : BaseContract.View {
        fun getProfileSuccess(profileBean: ProfileBean?)
        fun getProfileFailure(msg: String)

    }

    interface Presenter : BaseContract.Presenter {
        fun getProfile()
    }
}