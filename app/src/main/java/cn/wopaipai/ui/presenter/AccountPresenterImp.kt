package cn.wopaipai.ui.presenter

import android.annotation.SuppressLint
import cn.catherine.token.tool.json.GsonTool
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseException
import cn.wopaipai.base.BasePresenterImp
import cn.wopaipai.bean.ProfileBean
import cn.wopaipai.bean.request.LoginRequestBean
import cn.wopaipai.bean.UserResultBean
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.requester.AccountRequester
import cn.wopaipai.requester.LoginRequester
import cn.wopaipai.tool.DeviceTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.ui.contract.AccountContract
import cn.wopaipai.ui.contract.LoginContract
import cn.wopaipai.utils.MD5
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-23 17:48
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

class AccountPresenterImp(private val view: AccountContract.View) : AccountContract.Presenter,
    BasePresenterImp() {
    private val TAG = AccountPresenterImp::class.java.simpleName

    @SuppressLint("CheckResult")
    override fun getProfile() {
        view.showLoading()
        val passportId = BaseApplication.getPassportId()
        val sign = MD5.Md5Sign(passportId.toString())

        AccountRequester.getProfile(passportId, sign)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.hideLoading()
                LogTool.d(TAG, it)
                if (it != null) {
                    GsonTool.logInfo(TAG, MessageConstants.LogInfo.RESPONSE_JSON, "getProfile:", it)
                    val code = it.statusCode
                    if (code == MessageConstants.CODE_200) {
                        var profileBean =it.data as ProfileBean
//                            GsonTool.convert(it.data.toString(), ProfileBean::class.java)
                        if (profileBean != null) {
                            BaseApplication.profileBean = profileBean
                            view.getProfileSuccess(profileBean)

                        } else {
                            view.getProfileFailure(getString(R.string.no_more_data))

                        }

                    } else {
                        view.getProfileFailure(getExceptionInfoByCode(code))
                    }

                } else {
                    view.getProfileFailure(getString(R.string.get_data_failure))
                }


            }
                , { e ->
                    BaseException.print(e)
                    view.hideLoading()
                })
    }


}