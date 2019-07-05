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

interface ForgotPasswordContract {

    interface View : BaseContract.View {
        fun resetPasswordSuccess()
        fun resetPasswordFailure()
    }

    interface Presenter : BaseContract.Presenter {
        fun resetPassword()
    }

}