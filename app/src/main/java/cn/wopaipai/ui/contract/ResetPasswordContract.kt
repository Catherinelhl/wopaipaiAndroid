package cn.wopaipai.ui.contract

import cn.wopaipai.base.BaseContract

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-18 10:35
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.contract
+--------------+---------------------------------
+ description  +   忘记密码
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface ResetPasswordContract {

    interface View : BaseContract.View {
        fun resetPasswordSuccess(msg: String?)
        fun resetPasswordFailure(msg: String?)

        fun getResetPasswordVerifyCodeSuccess(msg: String?)
        fun getResetPasswordVerifyCodeFailure(msg: String?)
    }

    interface Presenter : BaseContract.Presenter {
        fun resetPassword(phoneNumber: String, phoneCode: Int, password: String, validCode: String)

        fun sendResetPasswordVerifyCode(phoneNumber: String, phoneCode: Int, ipAddress: String)
    }

}