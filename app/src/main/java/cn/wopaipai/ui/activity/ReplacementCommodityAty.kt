package cn.wopaipai.ui.activity

import android.os.Bundle
import android.view.View
import cn.wopaipai.R
import cn.wopaipai.base.BaseAty
import cn.wopaipai.constant.Constants
import cn.wopaipai.manager.returnResult
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.include_header.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-18 18:07
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +  置换商品
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class ReplacementCommodityAty : BaseAty() {
    override fun getArgs(bundle: Bundle?) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_replacement_commodity

    override fun initViews() {
        tv_title.text =getResString(R.string.replacement_commodity)
        ib_back.visibility = View.VISIBLE
    }

    override fun initData() {
    }

    override fun initListener() {


        RxView.clicks(ib_back).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe {
                returnResult(true)
            }

    }
}