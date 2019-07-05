package cn.wopaipai.ui.presenter

import android.annotation.SuppressLint
import cn.catherine.token.tool.json.GsonTool
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseException
import cn.wopaipai.base.BasePresenterImp
import cn.wopaipai.bean.OrderManagerBean
import cn.wopaipai.bean.OrderManagerListBean
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.requester.OrderManagerRequester
import cn.wopaipai.ui.contract.OrderManagerContract
import cn.wopaipai.utils.MD5
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-25 10:26
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

class OrderManagerPresenterImp(val view: OrderManagerContract.View) :
    OrderManagerContract.Presenter, BasePresenterImp() {
    private val TAG = OrderManagerPresenterImp::class.java.simpleName
    @SuppressLint("CheckResult")
    override fun getAuctionObtain(PageSize: Int, PageIndex: Int) {
        view.showLoading()
        val passportId: Int = BaseApplication.getPassportId()
        val sign = MD5.Md5Sign("$passportId$PageIndex$PageSize")
        OrderManagerRequester.getAuctionObtain(passportId, PageSize, PageIndex, sign)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                GsonTool.logInfo(
                    TAG,
                    MessageConstants.LogInfo.RESPONSE_JSON,
                    "getAuctionObtain",
                    it
                )

                if (it != null) {
                    when (val code = it.statusCode) {
                        MessageConstants.CODE_200 -> {
                            val orderManagerListBeans =
                                GsonTool.convert(
                                    GsonTool.string(it.data), OrderManagerListBean::class.java
                                )
                            view.getAuctionObtainSuccess(orderManagerListBeans)
                        }
                        else -> {
                            view.getAuctionObtainFailure(getExceptionInfoByCode(code))
                        }
                    }
                } else {
                    view.getAuctionObtainFailure(getString(R.string.no_more_data))
                }
            }, {
                BaseException.print(it)
                view.hideLoading()
            }, {
                view.hideLoading()
            })
    }
}