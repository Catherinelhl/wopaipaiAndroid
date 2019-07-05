package cn.wopaipai.ui.view

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.wopaipai.EnterInAuctionTipDialog
import cn.wopaipai.R
import cn.wopaipai.adapter.AuctionInProgressAdapter
import cn.wopaipai.adapter.AuctionStartByAMinuteAdapter
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseLinearLayout
import cn.wopaipai.bean.AuctionInfoBean
import cn.wopaipai.bean.WalletResponsesBean
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.gson.ResponseJson
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.listener.UpdateListener
import cn.wopaipai.tool.ListTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.StringTool
import cn.wopaipai.tool.decimal.DecimalTool
import cn.wopaipai.tool.recycleview.SpaceItemDecoration
import cn.wopaipai.ui.contract.GetUserContract
import cn.wopaipai.ui.presenter.GetUserWalletPresenterImp
import kotlinx.android.synthetic.main.view_auctioning.view.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-14 18:36
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.view
+--------------+---------------------------------
+ description  +  View：正在竞拍
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class AuctioningView(val activity: Activity) : BaseLinearLayout(activity), GetUserContract.View {


    private val presenter by lazy { GetUserWalletPresenterImp(this) }
    private val TAG = AuctioningView::class.java.simpleName
    private var auctionInOProgressBean: ArrayList<AuctionInfoBean>? = null
    private var auctionStartByAMinuteBean: ArrayList<AuctionInfoBean>? = null
    private lateinit var auctionInProgressAdapter: AuctionInProgressAdapter
    private lateinit var auctionStartByAMinuteAdapter: AuctionStartByAMinuteAdapter
    var updateListenerTemp: UpdateListener? = null
    lateinit var onItemSelectListener: OnItemSelectListener
    //能否加載更多
    private var canLoadingMore: Boolean = true
    private var isAuctioning = true
    private var auctinInfoBean: AuctionInfoBean? = null//当前选择的竞拍信息
    override fun setContentView(): Int = R.layout.view_auctioning

    override fun initView() {
        auctionInOProgressBean = arrayListOf()
        auctionInProgressAdapter =
            AuctionInProgressAdapter(
                context, auctionInOProgressBean, object : UpdateListener {
                    override fun onUpdate(isLoading: Boolean) {
                        updateListenerTemp?.let { it.onUpdate(isLoading) }
                    }
                }
            )

        auctionStartByAMinuteBean = arrayListOf()
        auctionStartByAMinuteAdapter =
            AuctionStartByAMinuteAdapter(
                context, auctionStartByAMinuteBean, object : UpdateListener {
                    override fun onUpdate(isLoading: Boolean) {
                        updateListenerTemp?.let { it.onUpdate(isLoading) }
                    }
                })

        rv_list.setHasFixedSize(true)
        rv_list.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        rv_list.addItemDecoration(SpaceItemDecoration(context.resources.getDimensionPixelOffset(R.dimen.d5)))
        rv_list.adapter = auctionInProgressAdapter
        auctionInProgressAdapter.onItemSelectListener = object : OnItemSelectListener {
            override fun <T : Any?> onItemSelect(type: T, from: String?) {
                if (type is AuctionInfoBean) {
                    auctinInfoBean = type
                    //获取当前的余额信息
                    presenter.getUserWallet()

                }
            }

        }

    }

    private var mLastVisibleItemPosition: Int = 0
    override fun initListener() {
        rv_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val layoutManager = recyclerView.layoutManager
                if (layoutManager is LinearLayoutManager) {
                    mLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                }
                if (isAuctioning) {
                    if (auctionInProgressAdapter != null) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItemPosition + 1 == auctionInProgressAdapter.itemCount) {
                            LogTool.d(TAG, MessageConstants.LOADING_MORE + canLoadingMore)
                            //发送网络请求获取更多数据
                            if (canLoadingMore) {
                                updateListenerTemp?.let { it.onUpdate(true) }
//                                pb_loading_more?.let { it.visibility = View.VISIBLE }

                            }
                        }
                    }
                } else {
                    if (auctionStartByAMinuteAdapter != null) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItemPosition + 1 == auctionStartByAMinuteAdapter.itemCount) {
                            LogTool.d(TAG, MessageConstants.LOADING_MORE + canLoadingMore)
                            //发送网络请求获取更多数据
                            if (canLoadingMore) {
                                updateListenerTemp?.let { it.onUpdate(true) }
//                                pb_loading_more?.let { it.visibility = View.VISIBLE }

                            }
                        }
                    }
                }

            }
        })
    }

    override fun httpExceptionStatus(responseJson: ResponseJson): Boolean = false
    /**
     * 通知View切换adapter类型
     * @param isAuctioning 是否是「正在竞拍」
     *
     */
    fun changeAdapterType(isAuctioning: Boolean) {
        this.isAuctioning = isAuctioning
        if (isAuctioning) {
            rv_list.adapter = auctionInProgressAdapter
            auctionInProgressAdapter.notifyDataSetChanged()
        } else {
            rv_list.adapter = auctionStartByAMinuteAdapter
            auctionStartByAMinuteAdapter.notifyDataSetChanged()

        }
    }

    /**
     * 刷新当前的数据列表
     * @param auctionCommoditiesBean 数据
     * @param type 当前的数据类型 1：正在竞拍 2：即将开始
     * @param fromLoading 是否是来自加载
     */
    fun refreshView(
        auctionInfoBeans: ArrayList<AuctionInfoBean>?,
        type: Int,
        fromLoading: Boolean
    ) {
        LogTool.d(TAG, "refreshView$auctionInfoBeans")
        //刷新adapter
        when (type) {
            1 -> {
                isAuctioning = true
                auctionInOProgressBean?.let {
                    if (!fromLoading) {
                        it.clear()
                    }
                    auctionInfoBeans?.let { temp ->
                        it.addAll(temp)
                    }

                }
                auctionInProgressAdapter.updateTime(System.currentTimeMillis())
            }
            2 -> {
                isAuctioning = false
                auctionStartByAMinuteBean?.let {
                    if (!fromLoading) {
                        it.clear()
                    }
                    auctionInfoBeans?.let { temp ->
                        it.addAll(temp)
                    }

                }
                auctionStartByAMinuteAdapter.updateTime(System.currentTimeMillis())
            }
        }


    }

    fun disposeAllTimer() {
        if (auctionInProgressAdapter != null) {
            auctionInProgressAdapter.disposeAllTimer()
        }
        if (auctionStartByAMinuteAdapter != null) {
            auctionStartByAMinuteAdapter.disposeAllTimer()
        }
    }

    fun stopLoading() {
//        pb_loading_more?.let { it.visibility = View.GONE }

    }

    override fun getUserWalletSuccess(walletResponsesBean: WalletResponsesBean) {
        walletResponsesBean?.let {
            val coins = walletResponsesBean.coins
            if (ListTool.noEmpty(coins)) {
                for (coinsBean in coins) {
                    val name = coinsBean.coinCode
                    if (StringTool.equals(name, MessageConstants.AUCTION_TYPE)) {
                        val balance = coinsBean.balance
                        BaseApplication.balance = balance
                        // 如果是当前的币种，那么取出余额进行显示
                        showEnterInDialog()
                    }
                }
            } else {
                showEnterInDialog()
            }

        }
    }

    override fun getUserWalletFailure(msg: String) {
        showEnterInDialog()
    }

    /**
     * 显示弹出提示进入框*/
    private fun showEnterInDialog() {
        auctinInfoBean?.let {
            var securityMoney = it.securityMoney
            //弹框提示冻结币种
            EnterInAuctionTipDialog().showDialog(activity,
                securityMoney.toString(),
                object : EnterInAuctionTipDialog.ConfirmClickListener {
                    override fun sure(tradePassword: String) {
                        val balance = BaseApplication.balance
                        //比对当前需要的冻结的金额和自己的余额是否满足大小，否则不能进去
                        if (StringTool.equals(
                                DecimalTool.calculateFirstSubtractSecondValue(
                                    balance.toString(), securityMoney.toString()
                                    , true
                                ),//可以相等，满足保证金之后就可以进去围观
                                MessageConstants.NO_ENOUGH_BALANCE
                            )
                        ) {
                            showToast(context.resources.getString(R.string.no_enough_balance))
                            return
                        }
                        onItemSelectListener?.let { listener ->
                            listener.onItemSelect(
                                it,
                                tradePassword
                            )
                        }


                    }

                    override fun cancel() {

                    }

                }
            )
        }

    }

}