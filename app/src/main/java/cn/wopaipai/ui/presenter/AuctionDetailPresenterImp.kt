package cn.wopaipai.ui.presenter

import android.annotation.SuppressLint
import cn.catherine.token.tool.json.GsonTool
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseException
import cn.wopaipai.base.BasePresenterImp
import cn.wopaipai.bean.AuctionDetailBean
import cn.wopaipai.bean.AuctionRecordBean
import cn.wopaipai.bean.OfferPriceBean
import cn.wopaipai.bean.request.OfferPriceRequestBean
import cn.wopaipai.bean.request.UnFreezeRequestBean
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.requester.AuctionRequester
import cn.wopaipai.tool.LogTool
import cn.wopaipai.ui.contract.AuctionDetailContract
import cn.wopaipai.utils.MD5
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-20 08:09
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.presenter
+--------------+---------------------------------
+ description  +   竞拍产品详情
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class AuctionDetailPresenterImp(val view: AuctionDetailContract.View) :
    AuctionDetailContract.Presenter,
    BasePresenterImp() {


    private val TAG = AuctionDetailPresenterImp::class.java.simpleName;
    private var getLatestRecordObservable: Disposable? = null
    /**
     * 获取竞拍产品详情
     */
    @SuppressLint("CheckResult")
    override fun getAuctionDetail(PassportId: Int, AuctionId: Int) {
        view.showLoading()
        val sign = MD5.Md5Sign("$AuctionId$PassportId")
        AuctionRequester.getAuctionDetail(PassportId, AuctionId, sign)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                GsonTool.logInfo(
                    TAG,
                    MessageConstants.LogInfo.RESPONSE_JSON,
                    "getAuctionDetail:",
                    it
                )
                if (it != null) {
                    val code = it.statusCode
                    if (code == MessageConstants.CODE_200) {
                        var auctionDetailBean = GsonTool.convert(
                            GsonTool.string(it.data),
                            AuctionDetailBean::class.java
                        )
                        if (auctionDetailBean != null) {
                            view.getAuctionDetailSuccess(auctionDetailBean)
                        }
                    } else {
                        view.getAuctionDetailFailure(getExceptionInfoByCode(code))
                    }
                }
            }, { e ->
                BaseException.print(e)
                LogTool.d(TAG, e.toString())
                view.hideLoading()
            }, {
                view.hideLoading()
            })
    }

    /**
     * 获取竞拍记录数据，包含出价记录和分成记录
     */
    @SuppressLint("CheckResult")
    override fun getAuctionRecord(PassportId: Int, AuctionId: Int, PageSize: Int, PageIndex: Int) {
        view.showLoading()
        val sign = MD5.Md5Sign("$AuctionId$PassportId$PageIndex$PageSize")
        AuctionRequester.getAuctionRecord(PassportId, AuctionId, PageSize, PageIndex, sign)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                GsonTool.logInfo(
                    TAG,
                    MessageConstants.LogInfo.RESPONSE_JSON,
                    "getAuctionRecord:",
                    it
                )

                if (it != null) {
                    val code = it.statusCode
                    if (code == MessageConstants.CODE_200) {
                        var auctionRecordBean = GsonTool.convert(
                            GsonTool.string(it.data), AuctionRecordBean::class.java
                        )
                        view.getAuctionRecordSuccess(auctionRecordBean)
                    } else {
                        view.getAuctionRecordDetailFailure(getExceptionInfoByCode(code))

                    }
                }


            }, { e ->
                BaseException.print(e)
                LogTool.d(TAG, e.toString())
                view.hideLoading()
            }, {
                view.hideLoading()
            })

    }

    /**
     *  获取最新竞拍记录数据，包含出价记录和分成记录，并自动更新在线时间
     */
    override fun getLatestAuctionRecords(
        PassportId: Int,
        AuctionId: Int,
        OfferRecordId: Int,
        DivideRecordId: Int
    ) {
        disposeRequest(getLatestRecordObservable)
        val sign = MD5.Md5Sign("$AuctionId$PassportId")
        getLatestRecordObservable = AuctionRequester.getLatestAuctionRecords(
            PassportId,
            AuctionId,
            OfferRecordId,
            DivideRecordId,
            sign
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                GsonTool.logInfo(
                    TAG,
                    MessageConstants.LogInfo.RESPONSE_JSON,
                    "getLatestAuctionRecords:",
                    it
                )
                if (it != null) {
                    val code = it.statusCode
                    if (code == MessageConstants.CODE_200) {
                        var auctionRecordBean = GsonTool.convert(
                            GsonTool.string(it.data), AuctionRecordBean::class.java
                        )
                        //判断当前是否有最新的数据，如果没有，那么就不传回数据
                        view.getLatestAuctionRecordsSuccess(auctionRecordBean)
                    } else {
                        //TODO 因为是定时获取，所以不做数据返回
//                        view.getLatestAuctionRecordsFailure(it.message)

                    }
                }
            }, { e ->
                //TODO 因为是定时获取，所以不做数据返回
                BaseException.print(e)
                disposeRequest(getLatestRecordObservable)

            }, {
                disposeRequest(getLatestRecordObservable)

            })
    }

    /**
     * 清除 请求
     * 如果当前的请求还没有回来，那么就直接取消，然后重新发起请求
     */
    private fun disposeRequest(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    /**
     *       出价竞拍
     */
    @SuppressLint("CheckResult")
    override fun offerPrice(
        PassportId: Int,
        AuctionId: Int,
        tradePassword: String,
        offerPrice: Double
    ) {

        view.showLoading()
        val sign = MD5.Md5Sign("$AuctionId$PassportId$tradePassword")
        val offerPriceRequestBean = OfferPriceRequestBean(
            PassportId,
            AuctionId,
            tradePassword,
            offerPrice,
            sign
        )

        GsonTool.logInfo(
            TAG,
            MessageConstants.LogInfo.REQUEST_JSON,
            "offerPrice:",
            offerPriceRequestBean
        )
        val body = GsonTool.beanToRequestBody(offerPriceRequestBean)
        AuctionRequester.offerPrice(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                GsonTool.logInfo(
                    TAG,
                    MessageConstants.LogInfo.RESPONSE_JSON,
                    "offerPrice:",
                    it
                )
                if (it != null) {
                    when (val code = it.statusCode) {
                        MessageConstants.CODE_200 -> {
                            val offerPriceBean = GsonTool.convert(
                                GsonTool.string(it.data),
                                OfferPriceBean::class.java
                            )
                            if (offerPriceBean != null) {
                                view.offerPriceSuccess(offerPriceBean)
                            }
                        }
                        else -> {
                            view.offerPriceFailure(getExceptionInfoByCode(code))
                        }
                    }
                }
            }, { e ->
                view.offerPriceFailure(getString(R.string.get_data_failure))
                LogTool.d(TAG, e.toString())
                view.hideLoading()
            }, {
                view.hideLoading()
            })
    }

    override fun disposableAll() {
        getLatestRecordObservable?.let { it.dispose() }
    }

    @SuppressLint("CheckResult")
    override fun unFreeze() {
        val passportID = BaseApplication.getPassportId()
        val sign = MD5.Md5Sign("$passportID")
        val unFreezeRequestBean = UnFreezeRequestBean(passportID, sign)

        GsonTool.logInfo(
            TAG,
            MessageConstants.LogInfo.REQUEST_JSON,
            "unFreeze:",
            unFreezeRequestBean
        )
        val body = GsonTool.beanToRequestBody(unFreezeRequestBean)
        AuctionRequester.unfreeze(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                GsonTool.logInfo(
                    TAG,
                    MessageConstants.LogInfo.RESPONSE_JSON,
                    "unFreeze:",
                    it
                )
                if (it != null) {

                    when (val code = it.statusCode) {
                        MessageConstants.CODE_200 -> {
                            view.unFreezeSuccess(MessageConstants.Empty)
                        }
                        else -> {
                            view.unFreezeFailure(getExceptionInfoByCode(code))
                        }
                    }
                }
            },
                { e ->
                    view.unFreezeFailure(getString(R.string.failed_to_unfreeze))
                    LogTool.d(TAG, e.toString())
                },
                {
                })
    }
}