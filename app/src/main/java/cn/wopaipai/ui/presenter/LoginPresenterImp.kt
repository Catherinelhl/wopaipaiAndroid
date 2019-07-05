package cn.wopaipai.ui.presenter

import android.annotation.SuppressLint
import cn.catherine.token.tool.json.GsonTool
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseException
import cn.wopaipai.base.BasePresenterImp
import cn.wopaipai.bean.request.LoginRequestBean
import cn.wopaipai.bean.UserResultBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.requester.LoginRequester
import cn.wopaipai.tool.DeviceTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.PreferenceTool
import cn.wopaipai.ui.contract.LoginContract
import cn.wopaipai.utils.MD5
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 17:48
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.presenter
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class LoginPresenterImp(private val view: LoginContract.View) : LoginContract.Presenter,
    BasePresenterImp() {
    private val TAG = LoginPresenterImp::class.java.simpleName

    @SuppressLint("CheckResult")
    override fun login(phoneNumber: String, countryCode: Int, password: String) {
        view.showLoading()
        val sign = MD5.Md5Sign(password)
        val loginResult = LoginRequestBean(
            phoneNumber,
            countryCode,
            password,
            DeviceTool().getDeviceModel(),
            sign
        )

        GsonTool.logInfo(TAG, MessageConstants.LogInfo.REQUEST_JSON, "login:", loginResult)
        val body = GsonTool.beanToRequestBody(loginResult)
        LoginRequester.login(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.hideLoading()
                LogTool.d(TAG, it)
                if (it != null) {
                    GsonTool.logInfo(TAG, MessageConstants.LogInfo.RESPONSE_JSON, "login:", it)
                    val code = it.statusCode
                    if (code == MessageConstants.CODE_200) {
                        var userResultBean = it.data as UserResultBean
                        if (userResultBean != null) {
                            BaseApplication.userInfoBean = userResultBean
                            BaseApplication.balance=userResultBean.balance
                            //存储当前的用户信息
                            PreferenceTool.getInstance().saveString(
                                Constants.Preference.USER_INFO,
                                GsonTool.string(userResultBean)
                            )
                            view.loginSuccess(userResultBean)
                        } else {
                            view.loginFailure(getString(R.string.get_data_failure))
                        }


                    } else {
                        view.loginFailure(getExceptionInfoByCode(code))
                    }

                } else {
                    view.loginFailure(getString(R.string.get_data_failure))

                }


            }
                , { e ->
                    BaseException.print(e)
                    view.hideLoading()
                })
    }


}