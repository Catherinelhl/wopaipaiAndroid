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
+ since        +   2019-06-18 18:06
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +  申请竞拍
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class ApplyAuctionAty : BaseAty() {
    override fun getArgs(bundle: Bundle?) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_apply_auction

    override fun initViews() {
        tv_title.text = getResString(R.string.apply_auction)
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