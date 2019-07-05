package cn.wopaipai.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import cn.wopaipai.R
import cn.wopaipai.base.BaseAty
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.HostURLConstants
import cn.wopaipai.manager.returnResult
import cn.wopaipai.tool.ActivityTool
import cn.wopaipai.tool.LogTool
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.aty_platform_protocal.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-18 00:05
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +  Activity类：平台服务协议
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class PlatformProtocolAty : BaseAty() {
    override fun getArgs(bundle: Bundle?) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_platform_protocal

    @SuppressLint("SetJavaScriptEnabled")
    override fun initViews() {
        ActivityTool.addActivity(this)

        /**
         * 调用loadUrl()方法进行加载内容
         */
        webview.loadUrl(HostURLConstants.PLATFORM_PROTOCAL)
        /**
         * 设置WebView的属性，此时可以去执行JavaScript脚本
         */
        webview.settings.javaScriptEnabled = true

    }

    override fun initData() {

        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                var msg =
                    handler.sendEmptyMessage(newProgress)
                LogTool.d(tag, newProgress)
            }
        }

    }

    val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            msg?.let {
                var newProgress = it.what
                if (newProgress == 100) {
                    pb.visibility = View.GONE
                } else {
                    pb.progress = newProgress

                }

            }


        }

    }

    @SuppressLint("CheckResult")
    override fun initListener() {
        RxView.clicks(ib_back).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe {
                activity.returnResult(true)
            }

    }
}