package cn.wopaipai.ui.activity

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import cn.wopaipai.AuctionIncreaseDialog
import cn.wopaipai.BaseDialog
import cn.wopaipai.BaseSingleDialog
import cn.wopaipai.R
import cn.wopaipai.adapter.AuctionRecordDivideAdapter
import cn.wopaipai.adapter.AuctionRecordOfferAdapter
import cn.wopaipai.adapter.CommoditiesDetailAdapter
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseAty
import cn.wopaipai.base.BaseException
import cn.wopaipai.bean.*
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.listener.ObservableTimerListener
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.manager.returnResult
import cn.wopaipai.tool.DateFormatTool
import cn.wopaipai.tool.ListTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.StringTool
import cn.wopaipai.tool.time.ObservableTimerTool
import cn.wopaipai.ui.contract.AuctionDetailContract
import cn.wopaipai.ui.presenter.AuctionDetailPresenterImp
import cn.wopaipai.utils.GlideImgManager
import cn.wopaipai.utils.MD5
import cn.wopaipai.view.viewpager.IconPagerFragmentAdapter
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.aty_auction_detail.*
import kotlinx.android.synthetic.main.view_main_banner.view.*
import java.util.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-15 16:41
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +  Activity：竞拍详情
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/


/**
 * 注意事项：
 *
 * 1：GetLatestAuctionRecords接口每6s调用一次
 * 2：进入页面、出价、页面倒计时两秒的时候都请求GetLatestAuctionRecords接口
 * 3：保证GetLatestAuctionRecords只会有一条请求在
 * 4：GetLatestAuctionRecords接口发挥当前剩余时间以及最低加价要求
 * 5：如果到时计时快完毕了然后最新的请求也没有回来，那么界面可以保持加载框的模式
 */
class AuctionDetailAty : BaseAty(), AuctionDetailContract.View {


    private val presenter by lazy { AuctionDetailPresenterImp(this) }
    private var auctionRecordBean: AuctionRecordBean? = null//当前的竞价记录
    private var latestAuctionRecordBean: AuctionRecordBean? = null//最新的竞价记录
    private var auctionRecordOfferBeans: ArrayList<AuctionRecordOfferBean> =
        arrayListOf()//当前的竞价出价记录
    private var auctionRecordDivideBeans: ArrayList<AuctionRecordDivideBean> =
        arrayListOf()//当前的竞价分成记录
    private var latestAuctionRecordOfferBeans: ArrayList<AuctionRecordOfferBean> =
        arrayListOf()//最新当前的竞价记录
    private var latestAuctionRecordDivideBeans: ArrayList<AuctionRecordDivideBean> =
        arrayListOf()//最新当前的竞价分成记录
    private val auctionRecordOfferAdapter: AuctionRecordOfferAdapter by lazy {
        AuctionRecordOfferAdapter(
            this
        )
    }//填充竞价出价记录的数据适配器
    private val auctionRecordDivideAdapter: AuctionRecordDivideAdapter by lazy {
        AuctionRecordDivideAdapter(
            this
        )
    }//填充竞价分成记录的数据适配器
    private var auctionDetailImagesBean: ArrayList<AuctionDetailImagesBean> = arrayListOf()
    private val commoditiesDetailAdapter: CommoditiesDetailAdapter by lazy {
        CommoditiesDetailAdapter(
            this,
            auctionDetailImagesBean
        )
    }
    private var countTime = 0L//当前需要倒数计时的总时间
    //顶部banner的视图
    private var viewList: MutableList<View> = arrayListOf()
    //图片页码的viewpager适配
    private val viewFragmentAdapter: IconPagerFragmentAdapter by lazy {
        IconPagerFragmentAdapter(
            viewList
        )
    }

    private var productDetailBean: ProductDetailBean? = null//当前竞拍产品的详细信息

    private var pageSize = 50
    private var pageIndex = 1

    private var auctionInfoBean: AuctionInfoBean? = null//上个页面传入的竞拍的物品信息
    private var auctionDetailBean: AuctionDetailBean? = null// 当前界面服务器返回的当前商品的规格信息


    private var offerRecordId = 0
    private var divideRecordId = 0

    private var auctionEndTime: String? = null//当前竞拍品的最新结束时间，如果当前返回的是null，则不用理会，否则与此字段进行比对赋值

    private val day by lazy { getResString(R.string.day) }

    private var timeIsOver = false//竞拍是否结束
    override fun getArgs(bundle: Bundle?) {
        bundle ?: return
        auctionInfoBean =
            bundle.getSerializable(Constants.KeyMaps.AUCTION_COMMODITY_INFO) as AuctionInfoBean

    }

    override fun getLayoutRes(): Int = R.layout.aty_auction_detail

    override fun initViews() {
        // 根据屏幕的宽度来得到屏幕的高度
        var height = BaseApplication.screenWidth * 300 / 375
        var params = rl_cover.layoutParams
        params.height = height
        rl_cover.layoutParams = params

        initAuctionRecordData(rv_bidding_record).adapter = auctionRecordOfferAdapter
        initAuctionRecordData(rv_divide_record).adapter = auctionRecordDivideAdapter
    }

    /**
     * 初始化竞拍记录数据
     */
    private fun initAuctionRecordData(view: RecyclerView): RecyclerView {
        view.setHasFixedSize(true)
        view.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        return view
    }

    private fun initMainTopBanner(imageBean: List<String>) {
        if (ListTool.noEmpty(viewList)) {
            viewList.clear()
            icon_pager_indicator.removeAllViews()
        }
        for (i in imageBean.indices) {
            val view = LayoutInflater.from(activity).inflate(R.layout.view_main_banner, null)
            view.iv_broadcast_banner.scaleType=ImageView.ScaleType.CENTER_CROP
            GlideImgManager.displayImage(
                activity,
                imageBean[i],
                view.iv_broadcast_banner
            )
            viewList.add(view)
        }
        vp_main_banner.offscreenPageLimit = viewList.size
        vp_main_banner.adapter = viewFragmentAdapter
        vp_main_banner.currentItem = 0
        icon_pager_indicator.setViewPager(vp_main_banner)
    }

    override fun initData() {
        // 刷新竞拍详情
        refreshAuctionDetail()
        //开始定时请求最新的记录
        ObservableTimerTool.getLastRecordByIntervalTimer(
            timeToRefreshDataListener
        )
        //初始化产品详情的list信息
        rv_commodities_detail.setHasFixedSize(true)
        rv_commodities_detail.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        rv_commodities_detail.adapter = commoditiesDetailAdapter
        commoditiesDetailAdapter.onItemSelectListener = object : OnItemSelectListener {
            override fun <T : Any?> onItemSelect(type: T, from: String?) {
                if (type is String) {
                    LogTool.d(tag, "dot to Zoom:$type")
                }
            }
        }
    }


    /**
     * 刷新竞品详情
     */
    private fun refreshAuctionDetail() {
        var passportId = BaseApplication.getPassportId()
        auctionInfoBean?.let {
            presenter.getAuctionDetail(passportId, it.auctionId)//请求竞拍页面详情
            presenter.getAuctionRecord(
                passportId,
                it.auctionId,
                pageSize,
                pageIndex
            )//请求竞拍记录
            //设置冻结金额
            tv_frozen_amount.text =
                getResString(R.string.freeze) + MessageConstants.Space + it.securityMoney + "\t" + MessageConstants.AUCTION_TYPE + getString(
                    R.string.brackets
                )
        }
    }

    private val timeToRefreshDataListener = object :
        ObservableTimerListener {
        override fun timeUp(message: String) {
            when (message) {
                Constants.TimerType.GET_LATEST_RECORD -> {
                    //获取最新的竞价记录
                    getLastRecord()
                }

            }
        }
    }

    @SuppressLint("CheckResult")
    override fun initListener() {
        RxView.clicks(ib_back).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                showBackTipDialog()
            },
                { e -> LogTool.e(tag, e.toString()) })
        RxView.clicks(btn_bidding)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                var offerPrice = auctionDetailBean?.lowestInPrice
                AuctionIncreaseDialog().showDialog(this,
                    offerPrice.toString(),
                    object : AuctionIncreaseDialog.ConfirmClickListener {
                        override fun toast(info: String) {
                            showToast(info)
                        }

                        override fun sure(tradePassword: String) {
                            if (auctionInfoBean != null) {
                                presenter.offerPrice(
                                    BaseApplication.getPassportId(),
                                    auctionInfoBean!!.auctionId,
                                    MD5.confuseTradePassword(tradePassword),
                                    offerPrice!!

                                )

                            } else {
                                showToast(getResString(R.string.get_data_failure))
                            }
                        }

                        override fun cancel() {

                        }

                    }
                )
            },
                { e ->
                    BaseException.print(e)
                    LogTool.e(tag, e.toString())
                })
    }

    private var disposableCountDownTimer: Disposable? = null
    private fun disposeRequest(disposable: Disposable?) {
        tv_end_count_down_time!!.text = MessageConstants.NO_ENOUGH_TIME
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    override fun getAuctionDetailSuccess(auctionDetailBean: AuctionDetailBean) {
        // 得到当前的商品信息，然后显示
        this.auctionDetailBean = auctionDetailBean
        productDetailBean = auctionDetailBean.detail
        productDetailBean?.let {
            auctionDetailImagesBean = it.images
            commoditiesDetailAdapter.addList(auctionDetailImagesBean)
        }
        //初始化banner的值
        initMainTopBanner(auctionDetailBean.mainImages)
        //初始化其他信息
        //判断当前的endTime是否为空，如果为空那么就不能进入
        if (StringTool.isEmpty(auctionDetailBean.endTime)) {
            showToast(getString(R.string.auction_is_over))
            timeIsOver = true
            finishAllDispose()
            rl_auction_over.visibility = View.VISIBLE
            btn_bidding.isEnabled = false
            btn_bidding.alpha = 0.6f
        } else {
            //计算倒计时
            calculatingTimeDifferenceByEndAndCurrentTime(
                auctionDetailBean.endTime,
                auctionDetailBean.currentTime
            )
        }

        // productName
        tv_info.text = auctionDetailBean.productName
        //当前价格
        tv_current_price.text = auctionDetailBean.currentPrice.toString()
        // 产品原价
        tv_market_price.text =
            getString(R.string.market_price) + auctionDetailBean.rawPrice + getString(
                R.string.brackets
            )
        tv_market_price.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        //竞价次数
        lmr_bidding_person_number.setMiddleContent(auctionDetailBean.auctionCount.toString())
        //围观人数
        lmr_looker_count.setMiddleContent(auctionDetailBean.lookerCount.toString())
        // 设置账户余额
        tv_current_balance.text = auctionDetailBean.balance.toString()
    }

    /**
     * 根据结束时间和开始时间计算时间差然后显示
     */
    private fun calculatingTimeDifferenceByEndAndCurrentTime(endTime: String, currentTime: String) {
        //先解除倒计时信息
        disposeRequest(disposableCountDownTimer)
        countTime = DateFormatTool.getCountDownTime(
            endTime,
            currentTime

        )
        var displayCountDownTime = MessageConstants.NO_ENOUGH_TIME
        if (countTime > 0L) {
            LogTool.d(tag, "countTime::$countTime")
            displayCountDownTime = DateFormatTool.getDisplayCountDownTime(day, countTime)
            LogTool.d(tag, "displayCountDownTime::$displayCountDownTime")
            // 如果当前有需要倒计时的时间则启动倒计时
            countDownAuctionEndTime()
        }
        tv_end_count_down_time.text = displayCountDownTime
    }

    override fun getAuctionDetailFailure(msg: String) {
        showToast(msg)
    }

    override fun getAuctionRecordSuccess(auctionRecordBean: AuctionRecordBean) {
        this.auctionRecordBean = auctionRecordBean
        this.auctionRecordOfferBeans = auctionRecordBean.offerRecordList.data
        //更新出价记录
        if (ListTool.isEmpty(auctionRecordOfferBeans)) {
//            ll_auction_offer_record.visibility = View.GONE
        } else {
//            ll_auction_offer_record.visibility = View.VISIBLE
            //对当前的数组进行排序
            Collections.sort(this.auctionRecordOfferBeans, ListTool.getRecordComparator())
            auctionRecordOfferAdapter.addList(auctionRecordOfferBeans)

        }
        auctionRecordDivideBeans = auctionRecordBean.divideRecordList.data
        if (ListTool.isEmpty(auctionRecordDivideBeans)) {
//            ll_auction_divide_record.visibility = View.GONE
        } else {
//            ll_auction_divide_record.visibility = View.VISIBLE
            //对当前的数组进行排序
            Collections.sort(this.auctionRecordDivideBeans, ListTool.getRecordComparator())
            auctionRecordDivideAdapter.addList(this.auctionRecordDivideBeans)
        }
        //获取最新的offerRecordId and divideRecordId
        this.offerRecordId = ListTool.getLatestRecordId(this.auctionRecordOfferBeans)
        this.divideRecordId = ListTool.getLatestRecordId(auctionRecordDivideBeans)
        getLastRecord()
    }

    override fun getAuctionRecordDetailFailure(msg: String) {
        showToast(msg)
    }

    override fun getLatestAuctionRecordsSuccess(auctionRecordBean: AuctionRecordBean) {
        //得到当前最新的竞拍信息
        this.latestAuctionRecordBean = auctionRecordBean
        //判断当前的竞拍是否结束
        if (auctionRecordBean.isAuctionEnd) {
            timeIsOver = true
            finishAllDispose()
            rl_auction_over.visibility = View.VISIBLE
            btn_bidding.isEnabled = false
            btn_bidding.alpha = 0.6f
            //获取拍卖下此竞拍的用户昵称
            val nickName = auctionRecordBean.nickName
            // 关闭现在已经有的弹框
            BaseDialog().dismiss()
            BaseSingleDialog().showDialog(this,
                "${getResString(R.string.auction_tips_pre)}\t$nickName\t${getResString(
                    R.string.auction_tips_last
                )}", object : BaseSingleDialog.ConfirmClickListener {
                    override fun sure() {
                        presenter.unFreeze()
                    }
                })
        } else {
            val auctionRecordOfferListBean = auctionRecordBean.offerRecordList
            val auctionRecordOfferDivideBean = auctionRecordBean.divideRecordList
            if (auctionRecordOfferListBean != null) {
                this.latestAuctionRecordOfferBeans = auctionRecordOfferListBean.data
                //如果当前的竞价出价记录部位空，那么追到已有的数据的后面
                if (ListTool.noEmpty(latestAuctionRecordOfferBeans)) {
                    for (i in latestAuctionRecordOfferBeans.indices) {
                        //将当前的最新数据加入记录列表，然后重新刷新数据
                        this.auctionRecordOfferBeans.add(this.latestAuctionRecordOfferBeans[i])
                        //对当前的数组进行排序
                        Collections.sort(
                            this.auctionRecordOfferBeans,
                            ListTool.getRecordComparator()
                        )
                    }
                    //获取最新的offerRecordId and divideRecordId
                    this.offerRecordId = ListTool.getLatestRecordId(this.auctionRecordOfferBeans)
                    //更新adapter
                    if (ListTool.isEmpty(auctionRecordOfferBeans)) {
//                        ll_auction_offer_record.visibility = View.GONE
                    } else {
//                        ll_auction_offer_record.visibility = View.VISIBLE
                        auctionRecordOfferAdapter.addList(auctionRecordOfferBeans)
                        //取最新的记录更新当前界面的最新价
                        tv_current_price.text =
                            auctionRecordOfferBeans[0].currentPrice.toString()

                    }

                }
            }
            if (auctionRecordOfferDivideBean != null) {
                this.latestAuctionRecordDivideBeans = auctionRecordOfferDivideBean.data
                if (ListTool.noEmpty(latestAuctionRecordDivideBeans)) {
                    for (i in latestAuctionRecordDivideBeans.indices) {
                        //将当前的最新数据加入记录列表，然后重新刷新数据
                        this.auctionRecordDivideBeans.add(this.latestAuctionRecordDivideBeans[i])
                        //对当前的数组进行排序
                        Collections.sort(
                            this.auctionRecordDivideBeans,
                            ListTool.getRecordComparator()
                        )
                    }
                    //获取最新的offerRecordId and divideRecordId
                    this.divideRecordId = ListTool.getLatestRecordId(auctionRecordDivideBeans)
                    if (ListTool.isEmpty(auctionRecordDivideBeans)) {
//                        ll_auction_divide_record.visibility = View.GONE
                    } else {
//                        ll_auction_divide_record.visibility = View.VISIBLE
                        auctionRecordDivideAdapter.addList(this.auctionRecordDivideBeans)
                    }
                }
            }
            //获取围观人数和竞价数目
            var auctionCount = auctionRecordBean.auctionCount
            var lookerCount = auctionRecordBean.lookerCount
            if (auctionCount != 0) {
                //竞价次数
                lmr_bidding_person_number.setMiddleContent(auctionCount.toString())
            }
            if (lookerCount != 0) {
                //竞价次数
                lmr_looker_count.setMiddleContent(lookerCount.toString())
            }

            //围观人数

            // 得到最新的时间与当前的定值进行比对
            judgeAuctionIsOverByEndAndCurrentTime(
                auctionRecordBean.endTime,
                auctionRecordBean.currentTime
            )
        }

    }


    override fun getLatestAuctionRecordsFailure(msg: String) {
        showToast(msg)
    }

    /**
     * 根据当前最新记录返回的最新结束时间和当前时间来判断当前的竞品拍卖是否已经结束
     */
    private fun judgeAuctionIsOverByEndAndCurrentTime(endTime: String?, currentTime: String) {
        endTime?.let {
            //如果当前的endTime不为空，判断历史endTime是否为空
            //两个数值进行比对
            if (!StringTool.equals(auctionEndTime, endTime)) {
                if (StringTool.isEmpty(auctionEndTime)) {
                    auctionEndTime = endTime
                } else {
                    if (DateFormatTool.getCountDownTime(endTime, auctionEndTime) > 0) {
                        auctionEndTime = endTime
                    }
                }
                //马上去改变倒计时
                calculatingTimeDifferenceByEndAndCurrentTime(
                    auctionEndTime!!,
                    currentTime
                )
            }
        }
    }

    override fun offerPriceSuccess(offerPriceBean: OfferPriceBean) {
        showToast(getString(R.string.auction_success))
        //刷新当前余额
        tv_current_balance.text = offerPriceBean.balance.toString()
        //请求最新记录
        getLastRecord()
    }

    override fun offerPriceFailure(msg: String) {
        showToast(msg)

    }

    /**
     * 定时刷新最新记录
     */
    private fun getLastRecord() {
        // 判断time是否结束，如果已经结束就不进行请求
        if (timeIsOver) return
        auctionInfoBean?.let {
            //请求最新竞拍记录
            presenter.getLatestAuctionRecords(
                BaseApplication.getPassportId(),
                it.auctionId,
                offerRecordId,
                divideRecordId
            )
        }

    }

    /**
     * 倒数计时竞价结束时间
     */
    private fun countDownAuctionEndTime() {
        ObservableTimerTool.countDownTimer(countTime)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
            }.subscribe(object : Observer<Long> {
                @SuppressLint("SetTextI18n")
                override fun onNext(integer: Long) {
                    tv_end_count_down_time!!.text =
                        DateFormatTool.getDisplayCountDownTime(day, integer)
//                    //TODO 如果当前还剩下2s，那么就开始请求GetLatestAuctionRecords接口，获取最新的商品情况
//                    if (integer == 2L) {
//                        LogTool.d(tag, "还剩2s，我重新请求了哦～～～～")
//                        getLastRecord()
//                    }
                }

                override fun onSubscribe(d: Disposable) {
                    disposableCountDownTimer = d

                }

                override fun onError(e: Throwable) {
                    disposeRequest(disposableCountDownTimer)
                }

                override fun onComplete() {
                    disposeRequest(disposableCountDownTimer)
                    //再次请求GetLatestRecord
                    LogTool.d(tag, "倒计时结束了，我再请求重新确认一下～～～")
                    getLastRecord()
                    // 设置按钮不可点击或者提示
                }
            })
    }

    override fun onBackPressed() {
        showBackTipDialog()
    }

    /**
     * 显示退出的提示框
     */
    private fun showBackTipDialog() {
        if (!timeIsOver) {
            BaseDialog().showDialog(this,
                getResString(R.string.sure_to_exit),
                getResString(R.string.cancel),
                getResString(R.string.confirm),
                getResString(R.string.you_will_miss_the_red_envelop),
                object : BaseDialog.ConfirmClickListener {
                    override fun cancel() {
                    }

                    override fun sure() {
                        presenter.unFreeze()
                    }
                })
        } else {
            presenter.unFreeze()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        finishAllDispose()
    }

    /**
     * 完成所有的订阅
     */
    private fun finishAllDispose() {
        //停止所有订阅
        presenter.disposableAll()
        //解除倒数计时订阅
        disposeRequest(disposableCountDownTimer)
        //解除定时发送订阅
        ObservableTimerTool.closeGetLatestRecordByIntervalTimer()
    }

    override fun unFreezeFailure(msg: String) {
        showToast(msg)
        returnResult(true)
    }

    override fun unFreezeSuccess(msg: String) {
        returnResult(true)

    }


}