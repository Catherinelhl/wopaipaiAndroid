package cn.wopaipai.ui.presenter

import android.annotation.SuppressLint
import cn.catherine.token.tool.json.GsonTool
import cn.wopaipai.R
import cn.wopaipai.base.BaseException
import cn.wopaipai.base.BasePresenterImp
import cn.wopaipai.bean.ResetPasswordBean
import cn.wopaipai.bean.ResetPasswordVerifyCodeBean
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.requester.LoginRequester
import cn.wopaipai.tool.LogTool
import cn.wopaipai.ui.contract.ResetPasswordContract
import cn.wopaipai.utils.MD5
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-18 14:38
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.presenter
+--------------+---------------------------------
+ description  +  重设密码
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class ResetPasswordPresenterImp(val view: ResetPasswordContract.View) :
    ResetPasswordContract.Presenter, BasePresenterImp() {

    private val TAG = ResetPasswordPresenterImp::class.java.simpleName
    @SuppressLint("CheckResult")
    override fun resetPassword(
        phoneNumber: String,
        phoneCode: Int,
        password: String,
        validCode: String
    ) {
        view.showLoading()
        val sign = MD5.Md5Sign(password + validCode)
        val resetPasswordBean = ResetPasswordBean(phoneNumber, phoneCode, password, validCode, sign)
        GsonTool.logInfo(
            TAG,
            MessageConstants.LogInfo.REQUEST_JSON,
            "resetPassword:",
            resetPasswordBean
        )
        val body = GsonTool.beanToRequestBody(resetPasswordBean)

        LoginRequester.resetPassword(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.hideLoading()
                LogTool.d(TAG, it)
                if (it != null) {
                    GsonTool.logInfo(
                        TAG,
                        MessageConstants.LogInfo.RESPONSE_JSON,
                        "resetPassword:",
                        it
                    )

                    when (val code = it.statusCode) {
                        MessageConstants.CODE_200 -> {
                            view.resetPasswordSuccess(getString(R.string.password_reset_success))
                        }
                        else -> {
                            view.resetPasswordFailure(getExceptionInfoByCode(code))
                        }
                    }
                } else {
                    view.resetPasswordFailure(getString(R.string.get_data_failure))

                }


            }
                , { e ->
                    BaseException.print(e)
                    view.hideLoading()
                })

    }

    @SuppressLint("CheckResult")
    override fun sendResetPasswordVerifyCode(
        phoneNumber: String,
        phoneCode: Int,
        ipAddress: String
    ) {

        view.showLoading()
        val sign = MD5.Md5Sign(phoneCode.toString() + phoneNumber)
        val resetPasswordVerifyCodeBean =
            ResetPasswordVerifyCodeBean(phoneNumber, phoneCode, ipAddress, sign)
        GsonTool.logInfo(
            TAG,
            MessageConstants.LogInfo.REQUEST_JSON,
            "sendResetPasswordVerifyCode:",
            resetPasswordVerifyCodeBean
        )
        val body = GsonTool.beanToRequestBody(resetPasswordVerifyCodeBean)

        LoginRequester.resetPasswordVerifyCode(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.hideLoading()
                LogTool.d(TAG, it)
                if (it != null) {
                    GsonTool.logInfo(
                        TAG,
                        MessageConstants.LogInfo.RESPONSE_JSON,
                        "sendResetPasswordVerifyCode:",
                        it
                    )

                    when (val code = it.statusCode) {
                        MessageConstants.CODE_200 -> {
                            view.getResetPasswordVerifyCodeSuccess(MessageConstants.Empty)
                        }
                        else -> {
                            view.getResetPasswordVerifyCodeFailure(getExceptionInfoByCode(code))
                        }
                    }
                } else {
                    view.getResetPasswordVerifyCodeFailure(getString(R.string.get_data_failure))

                }


            }
                , { e ->
                    BaseException.print(e)
                    view.hideLoading()
                })
    }
}