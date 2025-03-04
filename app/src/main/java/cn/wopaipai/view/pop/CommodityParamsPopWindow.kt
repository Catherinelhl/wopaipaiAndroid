package cn.wopaipai.view.pop

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import cn.wopaipai.R
import cn.wopaipai.adapter.CommodityParamsAdapter
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.bean.CommodityParamsBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.tool.LogTool
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.pop_show_commodity_params.view.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-13 22:41
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.view.pop
+--------------+---------------------------------
+ description  +  popWindow：商品参数
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class CommodityParamsPopWindow(val context: Context) : PopupWindow(context) {
    private val TAG = CommodityParamsPopWindow::class.java.simpleName
    private val popWindow: View
    private var commodityParamsBeans: ArrayList<CommodityParamsBean> = arrayListOf()
    private val paramsAdapter by lazy { CommodityParamsAdapter(context, commodityParamsBeans) }

    init {
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.MATCH_PARENT
        isOutsideTouchable = true
        isFocusable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        popWindow = inflater.inflate(R.layout.pop_show_commodity_params, null)
        contentView = popWindow
        initView()
        initListener()
    }

    private fun initView() {
        //根据屏幕高度重新设置高度
        resetLayoutHeight()
        //添加参数数据
        commodityParamsBeans.add(CommodityParamsBean("品牌", arrayListOf("AAA")))
        commodityParamsBeans.add(
            CommodityParamsBean(
                "尺码",
                arrayListOf("M", "L", "XL", "2XL")
            )
        )
        commodityParamsBeans.add(CommodityParamsBean("面料", arrayListOf("纯棉")))
        commodityParamsBeans.add(
            CommodityParamsBean(
                "上市年份季节",
                arrayListOf("2019年春季")
            )
        )

        contentView.rv_list.adapter = paramsAdapter
        contentView.rv_list.setHasFixedSize(true)
        contentView.rv_list.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

    }

    private fun resetLayoutHeight() {
        val param = popWindow.ll_show_params.layoutParams
        //原比例是533/375，但是从效果图来popWindow占高太大，所以减小了33
        val height = BaseApplication.screenWidth * 500 / 375
        param.height = height
        popWindow.ll_show_params.layoutParams = param
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
        RxView.clicks(contentView.btn_finish)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({ dismiss() }, { e -> LogTool.e(TAG, e.toString()) })
    }
}