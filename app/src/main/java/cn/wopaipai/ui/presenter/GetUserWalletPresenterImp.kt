package cn.wopaipai.ui.presenter

import android.annotation.SuppressLint
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseException
import cn.wopaipai.base.BasePresenterImp
import cn.wopaipai.bean.WalletResponsesBean
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.requester.AccountRequester
import cn.wopaipai.ui.contract.GetUserContract
import cn.wopaipai.utils.MD5
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-28 09:12
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.presenter
+--------------+---------------------------------
+ description  +  取到用户信息
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class GetUserWalletPresenterImp(val view: GetUserContract.View) : GetUserContract.Presenter,
    BasePresenterImp() {


    @SuppressLint("CheckResult")
    override fun getUserWallet() {
        view.showLoading()
        val userId = BaseApplication.getPassportId()
        val sign = MD5.Md5Sign("$userId")
        AccountRequester.getUserWallet(userId, sign)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                if (it != null) {
                    val code = it.statusCode
                    when (code) {
                        MessageConstants.CODE_200 -> {
                            val responsesBean = it.data as WalletResponsesBean?
                            if (responsesBean != null) {
                                view.getUserWalletSuccess(responsesBean)
                            } else {
                                view.getUserWalletFailure(getString(R.string.no_more_data))
                            }
                        }
                        else -> {
                            view.getUserWalletFailure(getExceptionInfoByCode(code))
                        }
                    }
                }
            }, {
                BaseException.print(it)
                view.hideLoading()
            }, {
                view.hideLoading()

            })

    }
}