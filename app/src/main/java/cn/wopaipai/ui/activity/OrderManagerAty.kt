package cn.wopaipai.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import cn.wopaipai.R
import cn.wopaipai.adapter.OrderManagerAdapter
import cn.wopaipai.base.BaseAty
import cn.wopaipai.bean.OrderManagerBean
import cn.wopaipai.bean.OrderManagerListBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.manager.intentToActivity
import cn.wopaipai.manager.returnResult
import cn.wopaipai.tool.ListTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.recycleview.SpaceItemDecoration
import cn.wopaipai.ui.contract.OrderManagerContract
import cn.wopaipai.ui.presenter.OrderManagerPresenterImp
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.aty_order_manager.*
import kotlinx.android.synthetic.main.aty_order_manager.srl_data
import kotlinx.android.synthetic.main.include_header.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-18 15:56
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +  订单管理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class OrderManagerAty : BaseAty(), OrderManagerContract.View {


    private val presenter: OrderManagerContract.Presenter by lazy { OrderManagerPresenterImp(this) }
    private lateinit var orderManagerAdapter: OrderManagerAdapter
    private lateinit var orderManagerBeans: ArrayList<OrderManagerBean>
    private val pageSize = 50//当前的pageSize
    private val pageIndex = 1//当前

    override fun getArgs(bundle: Bundle?) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_order_manager

    override fun initViews() {
        tv_title.text = getResString(R.string.order_manager)
        ib_back.visibility = View.VISIBLE
        // 设置加载按钮的形态
        srl_data.setColorSchemeResources(
            R.color.button_color,
            R.color.button_color
        )
        srl_data.setSize(SwipeRefreshLayout.DEFAULT)

    }

    override fun initData() {
        presenter.getAuctionObtain(pageSize, pageIndex)
        orderManagerBeans = arrayListOf()
        orderManagerAdapter =
            OrderManagerAdapter(
                context, orderManagerBeans
            )

        rv_list.setHasFixedSize(true)
        rv_list.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        rv_list.addItemDecoration(SpaceItemDecoration(context.resources.getDimensionPixelOffset(R.dimen.d10)))
        orderManagerAdapter.onItemSelectListener = onItemSelectListenerAuctionAcquisition
        rv_list.adapter = orderManagerAdapter
    }

    @SuppressLint("CheckResult")
    override fun initListener() {
        srl_data.setOnRefreshListener {
            srl_data.isRefreshing = false
            refreshView(1)
        }
        RxView.clicks(ib_back).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe {
                returnResult(true)
            }


        RxView.clicks(tv_buy_commodity)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                checkTabTitleStyle(
                    true,
                    tv_buy_commodity,
                    tv_auction_acquisition
                )
                changeAdapterType(true)
                v_right_red_cursor.visibility = View.INVISIBLE
                v_left_red_cursor.visibility = View.VISIBLE
                //隐藏数据
                rl_no_data.visibility = View.VISIBLE
                srl_data.visibility = View.GONE
            },
                { e -> LogTool.e(tag, e.toString()) })
        RxView.clicks(tv_auction_acquisition)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                checkTabTitleStyle(
                    true,
                    tv_auction_acquisition,
                    tv_buy_commodity
                )
                changeAdapterType(false)
                v_right_red_cursor.visibility = View.VISIBLE
                v_left_red_cursor.visibility = View.INVISIBLE
                rl_no_data.visibility = View.GONE
                srl_data.visibility = View.VISIBLE

            },
                { e -> LogTool.e(tag, e.toString()) })

    }

    /**
     * 刷新当前界面
     * @param refreshType 用于标记刷新界面的类型
     */
    private fun refreshView(refreshType: Int) {

    }

    private fun changeAdapterType(isAuction: Boolean) {

    }

    /**
     * 切换竞拍标题tab的文字样式
     * @param isCheck 是否选中
     * @param view 当前需要操作的view
     */
    private fun checkTabTitleStyle(isCheck: Boolean, viewFocus: TextView?, view: TextView?) {
        viewFocus?.let {
            //改变字体颜色
            it.setTextColor(context.resources!!.getColor(if (isCheck) R.color.black_222222 else R.color.black_666666))
        }
        view?.let {
            it.setTextColor(context.resources!!.getColor(if (isCheck) R.color.black_666666 else R.color.black_222222))
        }
    }

    /**
     * 监听子项目返回点击事件
     */
    private val onItemSelectListenerAuctionAcquisition = object : OnItemSelectListener {
        override fun <T : Any?> onItemSelect(type: T, from: String?) {

            from?.let {
                var bundle = Bundle()
                bundle.putSerializable(
                    Constants.KeyMaps.AUCTION_ACQUISITION,
                    type as OrderManagerBean
                )
                when (from) {
                    Constants.ActionFrom.APPLY_AUCTION -> {
                        //持宝竞拍
                        intentToActivity(bundle, ApplyAuctionAty::class.java)

                    }
                    Constants.ActionFrom.REPLACEMENT_COMMODITY -> {
                        //置换商品
                        intentToActivity(bundle, ReplacementCommodityAty::class.java)
                    }
                    Constants.ActionFrom.RECEIVE_COMMODITY -> {
                        showToast(getResString(R.string.contact_customer_service_please))
                        //TODO 领取商品
//                        intentToActivity(bundle, ReceiveCommodityAty::class.java)

                    }
                }
            }
        }
    }

    override fun getAuctionObtainSuccess(orderManagerListBeans: OrderManagerListBean?) {
        if (orderManagerListBeans == null) {
        } else {
            val orderManagerListBean = orderManagerListBeans.data
            if (ListTool.isEmpty(orderManagerListBean)) {
                //显示没有数据
            } else {
                this.orderManagerBeans.clear()
                this.orderManagerBeans.addAll(orderManagerListBean!!)
                orderManagerAdapter.notifyDataSetChanged()

            }
        }


    }

    override fun getAuctionObtainFailure(msg: String) {
        showToast(msg)
    }
}