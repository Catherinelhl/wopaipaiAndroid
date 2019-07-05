package cn.wopaipai.ui.contract

import cn.wopaipai.base.BaseContract
import cn.wopaipai.bean.request.RegisterRequestBean
import cn.wopaipai.bean.UserResultBean

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-18 00:20
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.contract
+--------------+---------------------------------
+ description  +  注册
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface RegisterContract {

    interface View : BaseContract.View {
        fun registerSuccess(userResultBean: UserResultBean)
        fun registerFailure(msg: String)
        fun sendRegisterVerifyCodeFailure(msg: String)
        fun sendRegisterVerifyCodeSuccess(msg: String)

    }

    interface Presenter :BaseContract.Presenter{
        fun register(registerRequestBean: RegisterRequestBean, phoneNumber: String)
        fun sendRegisterVerifyCode(phoneNumber: String, countryCode: Int)
    }
}