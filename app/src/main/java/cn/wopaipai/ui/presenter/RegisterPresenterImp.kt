package cn.wopaipai.ui.presenter

import android.annotation.SuppressLint
import cn.catherine.token.tool.json.GsonTool
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseException
import cn.wopaipai.base.BasePresenterImp
import cn.wopaipai.bean.request.RegisterRequestBean
import cn.wopaipai.bean.RegisterValidCodeBean
import cn.wopaipai.bean.UserResultBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.requester.LoginRequester
import cn.wopaipai.tool.DeviceTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.PreferenceTool
import cn.wopaipai.ui.contract.RegisterContract
import cn.wopaipai.utils.MD5
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-18 00:32
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.presenter
+--------------+---------------------------------
+ description  +   注册网络逻辑处理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class RegisterPresenterImp(val view: RegisterContract.View) : RegisterContract.Presenter,
    BasePresenterImp() {

    private val TAG = RegisterPresenterImp::class.java.simpleName

    @SuppressLint("CheckResult")
    override fun register(registerRequestBean: RegisterRequestBean, phoneNumber: String) {
        view.showLoading()
        val sign = MD5.Md5Sign(phoneNumber)
        registerRequestBean.sign = sign
        GsonTool.logInfo(
            TAG,
            MessageConstants.LogInfo.REQUEST_JSON,
            "register:",
            registerRequestBean
        )
        val body = GsonTool.beanToRequestBody(registerRequestBean)
        LoginRequester.register(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.hideLoading()
                LogTool.d(TAG, it)
                if (it != null) {
                    GsonTool.logInfo(TAG, MessageConstants.LogInfo.RESPONSE_JSON, "register:", it)
                    when (val code = it.statusCode) {
                        MessageConstants.CODE_200 -> {
                            var userResultBean = it.data as UserResultBean

                            if (userResultBean!=null){
                                //存储当前的用户信息
                                PreferenceTool.getInstance().saveString(
                                    Constants.Preference.USER_INFO,
                                    GsonTool.string(userResultBean)
                                )
                                BaseApplication.userInfoBean = userResultBean
                                BaseApplication.balance=userResultBean.balance
                                view.registerSuccess(userResultBean)

                            }else{
                                view.registerFailure(getString(R.string.register_failed))
                            }
                        }
                        else -> {
                            view.registerFailure(
                                getExceptionInfoByCode(
                                    code)
                            )
                        }
                    }
                } else {
                    view.registerFailure(getString(R.string.get_data_failure))

                }


            }
                , { e ->
                    BaseException.print(e)
                    view.hideLoading()
                })

    }

    /**
     * 发送注册验证码
     */
    @SuppressLint("CheckResult")
    override fun sendRegisterVerifyCode(phoneNumber: String, countryCode: Int) {
        view.showLoading()
        val ipAddress = DeviceTool().getIpAddress()
        val sign = MD5.Md5Sign(countryCode.toString() + phoneNumber)
        val registerValidCodeBean = RegisterValidCodeBean(phoneNumber, countryCode, ipAddress, sign)
        GsonTool.logInfo(
            TAG,
            MessageConstants.LogInfo.REQUEST_JSON,
            "sendRegisterVerifyCode:",
            registerValidCodeBean
        )
        val body = GsonTool.beanToRequestBody(registerValidCodeBean)
        LoginRequester.sendRegisterVerifyCode(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.hideLoading()
                GsonTool.logInfo(
                    TAG,
                    MessageConstants.LogInfo.RESPONSE_JSON,
                    "sendRegisterVerifyCode:",
                    it
                )
                if (it != null) {
                    val code = it.statusCode
                    if (code == MessageConstants.CODE_200) {
                        view.sendRegisterVerifyCodeSuccess(MessageConstants.Empty)
                    } else {
                        view.sendRegisterVerifyCodeFailure(
                            getExceptionInfoByCode(
                                code,Constants.KeyMaps.REGISTER_CODE)
                        )
                    }
                } else {
                    view.sendRegisterVerifyCodeFailure(getString(R.string.get_data_failure))

                }


            }
                , { e ->
                    BaseException.print(e)
                    view.hideLoading()
                })
    }

}