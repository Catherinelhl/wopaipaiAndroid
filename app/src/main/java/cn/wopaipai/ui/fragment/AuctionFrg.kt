package cn.wopaipai.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import cn.catherine.token.tool.json.GsonTool
import cn.wopaipai.BuildConfig
import cn.wopaipai.R
import cn.wopaipai.adapter.TabViewAdapter
import cn.wopaipai.base.BaseFrg
import cn.wopaipai.bean.AuctionCommoditiesBean
import cn.wopaipai.bean.AuctionInfoBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.listener.LoadingDataListener
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.listener.UpdateListener
import cn.wopaipai.manager.intentActivityForResult
import cn.wopaipai.manager.intentToActivity
import cn.wopaipai.manager.showToast
import cn.wopaipai.tool.LogTool
import cn.wopaipai.ui.activity.AuctionDetailAty
import cn.wopaipai.ui.contract.AuctionContract
import cn.wopaipai.ui.presenter.AuctionPresenterImp
import cn.wopaipai.ui.view.AuctioningView
import com.google.gson.reflect.TypeToken
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.frg_auction.*
import kotlinx.android.synthetic.main.include_commodity_specifications.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-12 20:35
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.fragment
+--------------+---------------------------------
+ description  +  Fragment类：竞拍
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class AuctionFrg : BaseFrg(), AuctionContract.View {

    private val TAG = AuctionFrg::class.java.simpleName
    private val presenter by lazy { AuctionPresenterImp(this) }

    private val tabViewAdapter: TabViewAdapter by lazy { TabViewAdapter(auctioningView, allTitles) }
    private val auctioningView: AuctioningView by lazy { AuctioningView(activity!!) }
    private var selectAuctionInfoBean: AuctionInfoBean? = null//当前选中的
    private var auctionInfoBeans: ArrayList<AuctionInfoBean> = arrayListOf()// 当前的所有竞拍品信息
    private var type: Int = 1//当前请求的数据类型
    private var minPrice = 0.0//当前的最低价
    private var maxPrice = 0.0//当前的最高价
    private var pageSize = 20//页大小
    private var pageIndex = 1//页码
    private var fromLoading: Boolean = false
    private var canLoadingMore: Boolean = false// 是否能够加载更多

    private val allTitles: ArrayList<String> by lazy {
        arrayListOf(
            context!!.resources.getString(R.string.all),
            "0-499",
            "500-999",
            "1000-4999",
            getResString(R.string.above_five_thousand)
        )

    }
    private var currentPosition: Int = 0

    override fun getLayoutRes(): Int = R.layout.frg_auction

    override fun initViews(view: View) {
        auctioningView.updateListenerTemp = updateListener
        refreshView()
        // 设置加载按钮的形态
        srl_data.setColorSchemeResources(
            R.color.button_color,
            R.color.button_color

        )
        srl_data.setSize(SwipeRefreshLayout.DEFAULT)
        if (tab_layout == null && viewpager == null) {
            return
        }

        //  初始化顶部tab的数据以及相对应的界面信息
        // 移除所有的view
        tab_layout.removeTabLayout()
        viewpager.removeAllViews()
        val size = allTitles.size
        tab_layout.setTabSize(size)

        for (i in 0 until size) {
            //显示标题
            tab_layout.addTab(allTitles[i], i)
        }

        auctioningView.onItemSelectListener = object : OnItemSelectListener {
            override fun <T : Any?> onItemSelect(type: T, from: String?) {
                selectAuctionInfoBean = type as AuctionInfoBean
                //开始请求确认交易密码
                presenter.confirmTradePassword(from!!)

            }
        }
        viewpager.adapter = tabViewAdapter
        viewpager.currentItem = 0
        viewpager.offscreenPageLimit = size
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout.tabLayout))
        tab_layout.setupWithViewPager(viewpager, object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                currentPosition = tab.position
                when (currentPosition) {
                    0 -> {
                        minPrice = 0.0
                        maxPrice = 0.0
                    }
                    1 -> {
                        minPrice = 0.0
                        maxPrice = 500.0
                    }
                    2 -> {
                        minPrice = 500.0
                        maxPrice = 1000.0
                    }
                    3 -> {
                        minPrice = 1000.0
                        maxPrice = 5000.0
                    }
                    4 -> {
                        minPrice = 5000.0
                        maxPrice = 0.0
                    }
                }
                refreshView()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun getArgs(bundle: Bundle?) {
    }

    @SuppressLint("CheckResult")
    override fun initListener() {

        srl_data.setOnRefreshListener {
            srl_data.isRefreshing = false
            refreshView()
        }

        RxView.clicks(tv_auctioning).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                checkAuctionTabTitleStyle(
                    true,
                    tv_auctioning,
                    tv_auction_begin_in_a_minute
                )
                changeAdapterType(true)
                type = 1
                refreshView()
            },
                { e -> LogTool.e(TAG, e.toString()) })
        RxView.clicks(tv_broadcast).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                if (BuildConfig.LazyMode) {
                    activity.intentToActivity(AuctionDetailAty::class.java)
                }

            },
                { e -> LogTool.e(TAG, e.toString()) })
        RxView.clicks(tv_auction_begin_in_a_minute)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                checkAuctionTabTitleStyle(
                    true,
                    tv_auction_begin_in_a_minute,
                    tv_auctioning
                )
                changeAdapterType(false)
                type = 2
                refreshView()

            },
                { e -> LogTool.e(TAG, e.toString()) })
    }

    /**
     * 通知View切换adapter类型
     * @param isAuctioning 是否是「正在竞拍」
     *
     */
    private fun changeAdapterType(isAuctioning: Boolean) {
        auctioningView?.let {
            it.changeAdapterType(isAuctioning)
        }
    }

    /**
     * 切换竞拍标题tab的文字样式
     * @param isCheck 是否选中
     * @param view 当前需要操作的view
     */
    private fun checkAuctionTabTitleStyle(isCheck: Boolean, viewFocus: TextView?, view: TextView?) {
        context ?: return
        viewFocus?.let {
            //改变字体颜色
            it.setTextColor(context!!.resources!!.getColor(if (isCheck) R.color.black_222222 else R.color.white))
            //重设左边图片的padding
            it.compoundDrawablePadding =
                context!!.resources.getDimensionPixelOffset(R.dimen.d10)
            //设置左边图片
            it.setCompoundDrawablesWithIntrinsicBounds(
                context!!.resources.getDrawable(
                    if (isCheck) R.mipmap.icon_auction_tab_f else R.drawable.bg_auction_tab_white_conner
                ), null, null, null
            )
            //设置字体大小
            it.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context!!.resources.getDimensionPixelSize(R.dimen.text_size_16).toFloat()
            )
            //设置文字粗细
            it.typeface = Typeface.DEFAULT_BOLD
        }
        view?.let {
            it.setTextColor(context!!.resources!!.getColor(if (isCheck) R.color.white else R.color.black_222222))
            it.compoundDrawablePadding =
                context!!.resources.getDimensionPixelOffset(R.dimen.d10)
            it.setCompoundDrawablesWithIntrinsicBounds(
                context!!.resources.getDrawable(
                    if (isCheck) R.drawable.bg_auction_tab_white_conner else R.mipmap.icon_auction_tab_f
                ), null, null, null
            )
            it.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context!!.resources.getDimensionPixelSize(R.dimen.text_size_14).toFloat()
            )
            it.typeface = Typeface.DEFAULT

        }
    }

    /**
     * 刷新当前界面
     * @param refreshType 用于标记刷新界面的类型
     */
    fun refreshView() {
        pageIndex=1
        refreshView(false)
    }

    fun refreshView(fromLoading: Boolean) {
        this.fromLoading = fromLoading
        if (fromLoading) {
            // 如果是从上拉加载进入,检查当前数据是否大于等于pageSize，如果是就需要更新
            if (canLoadingMore) {
                //代表还需要加载
                pageIndex += 1
                presenter.getAuctionList(minPrice, maxPrice, type, pageSize, pageIndex)
            } else {
                activity.showToast(getResString(R.string.no_more_data))
            }

        } else {
            presenter.getAuctionList(minPrice, maxPrice, type, pageSize, pageIndex)

        }
    }

    override fun getAuctionListSuccess(auctionCommoditiesBean: AuctionCommoditiesBean) {
        var auctionInOProgressBean = GsonTool.convert<ArrayList<AuctionInfoBean>>(
            GsonTool.string(auctionCommoditiesBean.data),
            object : TypeToken<ArrayList<AuctionInfoBean>>() {}.type
        )
        auctioningView.refreshView(auctionInOProgressBean, type, fromLoading)
        auctioningView.stopLoading()
        auctionInOProgressBean?.let {
            for (i in it.indices) {
                this.auctionInfoBeans.add(it[i])
            }
        }
        canLoadingMore = this.auctionInfoBeans.size < auctionCommoditiesBean.total

    }

    override fun noMoreData() {
        canLoadingMore = false
    }

    override fun getAuctionListFailure(msg: String) {
        activity ?: return
        activity!!.showToast(msg)
        auctioningView.stopLoading()

    }

    /**
     *确认交易密码成功后进入竞拍详情
     */
    override fun confirmTradePasswordSuccess(msg: String) {
        val intent = Intent()
        val bundle = Bundle()
        bundle.putSerializable(
            Constants.KeyMaps.AUCTION_COMMODITY_INFO,
            selectAuctionInfoBean
        )

        intent.putExtras(bundle)
        activity.intentActivityForResult(
            AuctionDetailAty::class.java,
            Constants.RequestCode.AUCTION_DETAIL_REQUEST_CODE,
            intent
        )
    }

    override fun confirmTradePasswordFailure(msg: String) {
        activity?.showToast(msg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LogTool.d(TAG, "onActivityResult")
        if (resultCode == AppCompatActivity.RESULT_OK) {
            when (requestCode) {
                Constants.RequestCode.AUCTION_DETAIL_REQUEST_CODE -> {
                    //刷新当前的list
                    refreshView()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        auctioningView.disposeAllTimer()
    }

    var updateListener = object : UpdateListener {
        override fun onUpdate(isLoading: Boolean) {
            refreshView(isLoading)
        }

    }
}