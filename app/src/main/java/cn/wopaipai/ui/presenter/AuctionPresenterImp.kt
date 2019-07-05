package cn.wopaipai.ui.presenter

import android.annotation.SuppressLint
import cn.catherine.token.tool.json.GsonTool
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseException
import cn.wopaipai.base.BasePresenterImp
import cn.wopaipai.bean.AuctionCommoditiesBean
import cn.wopaipai.bean.request.TradePasswordRequestBean
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.requester.AuctionRequester
import cn.wopaipai.tool.LogTool
import cn.wopaipai.ui.contract.AuctionContract
import cn.wopaipai.utils.MD5
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-19 19:17
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.presenter
+--------------+---------------------------------
+ description  +   竞拍
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class AuctionPresenterImp(val view: AuctionContract.View) : AuctionContract.Presenter,
    BasePresenterImp() {

    /**
     * 确认交易密码
     */
    @SuppressLint("CheckResult")
    override fun confirmTradePassword(tradePassword: String) {
        view.showLoading()
        var tradePasswordConfuse = MD5.confuseTradePassword(tradePassword)
        var passportId = BaseApplication.getPassportId()
        val sign = MD5.Md5Sign("$passportId$tradePasswordConfuse")
        val confirmTradePassword =
            TradePasswordRequestBean(passportId, tradePasswordConfuse, sign)
        val body = GsonTool.beanToRequestBody(confirmTradePassword)

        AuctionRequester.confirmTradePassword(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                GsonTool.logInfo(
                    TAG,
                    MessageConstants.LogInfo.RESPONSE_JSON,
                    "confirmTradePassword:",
                    it
                )
                if (it != null) {
                    val code = it.statusCode
                    if (code == MessageConstants.CODE_200) {
                        view.confirmTradePasswordSuccess(MessageConstants.Empty)
                    } else {
                        view.confirmTradePasswordFailure(it.message)
                    }
                }
            }, { e ->
                LogTool.d(TAG, e.toString())
                view.hideLoading()
            }, {
                view.hideLoading()
            })
    }

    private val TAG = AuctionPresenterImp::class.java.simpleName;
    @SuppressLint("CheckResult")
    override fun getAuctionList(
        MinPrice: Double,
        MaxPrice: Double,
        Type: Int,
        PageSize: Int,
        PageIndex: Int
    ) {
        view.showLoading()
        val sign = MD5.Md5Sign("$Type$PageIndex$PageSize")
        AuctionRequester.getAuctionList(MinPrice, MaxPrice, Type, PageSize, PageIndex, sign)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                GsonTool.logInfo(
                    TAG,
                    MessageConstants.LogInfo.RESPONSE_JSON,
                    "getAuctionList:",
                    it
                )
                if (it != null) {
                    val code = it.statusCode
                    if (code == MessageConstants.CODE_200) {
                        var auctionCommoditiesBean = GsonTool.convert(
                            GsonTool.string(it.data),
                            AuctionCommoditiesBean::class.java
                        )
                        if (auctionCommoditiesBean == null) {
                            view.noMoreData()
                        } else {
                            view.getAuctionListSuccess(auctionCommoditiesBean)

                        }
                    } else {
                        view.getAuctionListFailure(getExceptionInfoByCode(code))
                    }
                }
            }, { e ->
                BaseException.print(e)
                view.hideLoading()
            }, {
                view.hideLoading()
            })

    }
}