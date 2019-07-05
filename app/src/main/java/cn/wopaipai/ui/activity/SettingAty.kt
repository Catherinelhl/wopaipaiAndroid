package cn.wopaipai.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import cn.wopaipai.R
import cn.wopaipai.base.BaseAty
import cn.wopaipai.constant.Constants
import cn.wopaipai.manager.intentToActivity
import cn.wopaipai.tool.ActivityTool
import cn.wopaipai.tool.PreferenceTool
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.aty_setting.*
import kotlinx.android.synthetic.main.include_header.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-24 10:27
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class SettingAty : BaseAty() {
    override fun getArgs(bundle: Bundle?) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_setting

    override fun initViews() {
        tv_title.text = getString(R.string.setting_center)
        ib_back.visibility = View.VISIBLE
    }

    override fun initData() {
    }

    @SuppressLint("CheckResult")
    override fun initListener() {
        RxView.clicks(ib_back).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe {
                finish()
            }
        RxView.clicks(tv_logout).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe {
                //结束所有的Activity
                ActivityTool.removeAllActivity()
                // 清除所有数据
                PreferenceTool.getInstance().clear()
                //跳转到登录界面
                intentToActivity(LoginAty::class.java, true)

            }

        RxView.clicks(rl_language_switch)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe {
                val bundle = Bundle()
                bundle.putBoolean(Constants.KeyMaps.FROM_LOGIN, false)
                //跳转到登录界面
                intentToActivity(bundle, LanguageSwitchAty::class.java, true)

            }
    }
}