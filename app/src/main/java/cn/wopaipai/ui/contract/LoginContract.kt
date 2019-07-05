package cn.wopaipai.ui.contract

import cn.wopaipai.base.BaseContract
import cn.wopaipai.bean.UserResultBean

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
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface LoginContract {
    interface View : BaseContract.View {
        fun loginSuccess(userResultBean: UserResultBean?)
        fun loginFailure(msg: String)
    }

    interface Presenter {
        fun login(phoneNumber: String, countryCode: Int, password: String)
    }
}