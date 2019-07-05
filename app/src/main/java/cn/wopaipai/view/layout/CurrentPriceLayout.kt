package cn.wopaipai.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cn.wopaipai.R
import cn.wopaipai.constant.Constants
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.tool.StringTool
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.layout_current_price.view.*
import kotlinx.android.synthetic.main.layout_substring.view.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-15 14:55
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.view
+--------------+---------------------------------
+ description  +   当前价格的Layout自定义
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class CurrentPriceLayout(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_current_price, this, true)
    }

    /**
     * 当前的价格
     */
    fun setCurrentPrice(info: String) {
        if (tv_current_price != null) {
            tv_current_price.text = info
        }
    }

    /**
     * 当前价格的单位
     */
    fun setCurrencyType(info: String) {
        if (tv_current_price_currency_type != null) {
            tv_current_price_currency_type.text = info
        } else {
            //todo 暂时写了一个临时的币种信息
            tv_current_price_currency_type.text = "ZBB"

        }
    }
}