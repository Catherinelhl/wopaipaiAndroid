package cn.wopaipai.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import cn.wopaipai.R
import cn.wopaipai.adapter.CommoditiesDetailAdapter
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseAty
import cn.wopaipai.base.BaseException
import cn.wopaipai.bean.AuctionDetailImagesBean
import cn.wopaipai.bean.CommoditySpecificationsBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.manager.showToast
import cn.wopaipai.tool.LogTool
import cn.wopaipai.view.pop.CommodityParamsPopWindow
import com.jakewharton.rxbinding2.view.RxView
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.aty_commodity_detail.*
import kotlinx.android.synthetic.main.include_commodity_specifications.*
import java.util.concurrent.TimeUnit


/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-13 16:45
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +   Activity:商品详情
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class CommodityDetailsAty : BaseAty() {

    private var auctionDetailImagesBean: ArrayList<AuctionDetailImagesBean> = arrayListOf()
    private val commoditiesDetailAdapter: CommoditiesDetailAdapter by lazy {
        CommoditiesDetailAdapter(
            this,
            auctionDetailImagesBean
        )
    }
    //延迟加载「选择参数」popWindow
    private val showParamsPopWindow: CommodityParamsPopWindow by lazy {
        CommodityParamsPopWindow(this)
    }
    //TODO 「商品规格」信息
    private val commoditySpecificationsBean: CommoditySpecificationsBean  by lazy {
        CommoditySpecificationsBean("宝石", "", "1.11", "144", arrayListOf("红色", "白色", "绿色", "蓝色"))
    }

    override fun getArgs(bundle: Bundle?) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_commodity_detail

    override fun initViews() {
        // 根据屏幕的宽度来得到屏幕的高度
        var height = BaseApplication.screenWidth * 300 / 375
        var params = iv_cover.layoutParams
        params.height = height
        iv_cover.layoutParams = params

    }

    override fun initData() {
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

    @SuppressLint("CheckResult")
    override fun initListener() {
        RxView.clicks(ib_back).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({ finish() },
                { e -> LogTool.e(tag, e.toString()) })
        RxView.clicks(ll_select_params)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                //选择参数=
                showCommodityParamsPopWindow()
            }, { e -> LogTool.e(tag, e.toString()) })
        RxView.clicks(ib_select_params)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                //选择参数
                showCommodityParamsPopWindow()
            }, { e -> LogTool.e(tag, e.toString()) })
        RxView.clicks(ll_select_specifications)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                showCommoditySpecifications()
            }, { e ->
                BaseException.print(e)
                LogTool.e(tag, e.toString())
            })
        RxView.clicks(ib_select_specifications)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                showCommoditySpecifications()
            }, { e ->
                BaseException.print(e)
                LogTool.e(tag, e.toString())
            })
        RxView.clicks(ll_commodity_specifications)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                //隐藏「商品规格」信息
                ll_commodity_specifications.visibility = View.GONE
            }, { e ->

                BaseException.print(e)
                LogTool.e(tag, e.toString())
            })
        RxView.clicks(ib_round_fork)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                //隐藏「商品规格」信息
                ll_commodity_specifications.visibility = View.GONE
            }, { e ->

                BaseException.print(e)
                LogTool.e(tag, e.toString())
            })
        flowlayout.setOnSelectListener { selectPosSet ->
            activity.title = "choose:$selectPosSet"
        }
        flowlayout.setOnTagClickListener(TagFlowLayout.OnTagClickListener { view, position, parent ->
            activity.showToast(commoditySpecificationsBean.commodityColors[position])
            true
        })
    }

    /**
     * 显示「商品规格」视图
     */
    private fun showCommoditySpecifications() {
        //选择规格
        resetLayoutHeight()
        //显示「商品规格」信息
        ll_commodity_specifications.visibility = View.VISIBLE
        //添加参数数据
        tv_commodity_specifications_price.text =
           getResString(R.string.symbol_dollar) + commoditySpecificationsBean.price
        tv_inventory_number.text =
            getResString(R.string.inventory_number) + commoditySpecificationsBean.inventoryNumber + context.getString(
                R.string.inventory_number_unit
            )
        flowlayout.adapter =
            object : TagAdapter<String>(commoditySpecificationsBean.commodityColors) {
                override fun getView(parent: FlowLayout?, position: Int, s: String?): View {
                    val tv = LayoutInflater.from(context).inflate(
                        R.layout.item_tv,
                        flowlayout, false
                    ) as TextView
                    tv.text = s
                    return tv
                }

            }


    }

    private fun resetLayoutHeight() {
        val param = rl_commodity_specifications.layoutParams
        //原比例是533/375，但是从效果图来popWindow占高太大，所以减小了33
        val height = BaseApplication.screenWidth * 500 / 375
        param.height = height
        rl_commodity_specifications.layoutParams = param
    }

    /**
     * 显示「商品参数」弹框
     */
    private fun showCommodityParamsPopWindow() {
        //1： 對當前pop window進行置空
        showParamsPopWindow.setOnDismissListener { setBackgroundAlpha(1f) }
        //设置layout在PopupWindow中显示的位置
        showParamsPopWindow.showAtLocation(
            window.decorView,
            Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
            0,
            0
        )
        setBackgroundAlpha(0.7f)
    }
}