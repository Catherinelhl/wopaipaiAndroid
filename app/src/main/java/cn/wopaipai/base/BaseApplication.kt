package cn.wopaipai.base

import android.content.Context
import android.content.IntentFilter
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.support.multidex.MultiDexApplication
import android.util.DisplayMetrics
import android.view.WindowManager
import cn.catherine.token.receiver.NetStateReceiver
import cn.catherine.token.tool.language.LanguageTool
import cn.wopaipai.bean.ProfileBean
import cn.wopaipai.bean.UserResultBean
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.event.NetStateChangeEvent
import cn.wopaipai.tool.LogTool
import com.squareup.otto.Subscribe

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 10:42
+--------------+---------------------------------
+ projectName  +   wopaipai
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.base
+--------------+---------------------------------
+ description  +   當前APP's Application,一些整個APP需要公用的常數、變量、SP相關的存儲統一在此類。也包括獲取當前設備的一些尺寸以及硬件信息
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/

class BaseApplication : MultiDexApplication() {
    val tag: String = BaseApplication::class.java.simpleName


    companion object {
        private lateinit var instance: BaseApplication
        //得到当前的用户信息
        var userInfoBean: UserResultBean? = null
        var balance: Double? = 0.0
        //得到当前的用户的资料信息
        var profileBean: ProfileBean? = null

        //得到当前的通行ID
        fun getPassportId(): Int {
            userInfoBean ?: return -1
            return userInfoBean!!.passportId
        }

        var screenWidth: Int = 0
        /*屏幕的高*/
        var screenHeight: Int = 0
        /*当前上下文*/
        val context: Context by lazy { instance.applicationContext }
        /*当前的语言环境,默认是英文*/
        var isZH: Boolean = false
        /*判断当前程序是否真的有网*/
        var realNet = true
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        getScreenMeasure()
        registerNetStateReceiver()
    }

    override fun attachBaseContext(base: Context) {
        //保存系统选择语言
        //        LanguageTool.saveSystemCurrentLanguage(base);
        super.attachBaseContext(LanguageTool.setLocal(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //保存系统选择语言
        LanguageTool.onConfigurationChanged(applicationContext)
    }

    /*注册网络变化的监听*/
    private fun registerNetStateReceiver() {
        val netStateReceiver = NetStateReceiver()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        this.registerReceiver(netStateReceiver, intentFilter)
    }

    @Subscribe
    fun netChanged(stateChangeEvent: NetStateChangeEvent) {
        realNet = stateChangeEvent.isConnect
    }

    /**
     * 得到当前屏幕的尺寸
     */
    private fun getScreenMeasure() {
        val displayMetrics = getDisplayMetrics()
        if (displayMetrics != null) {
            screenWidth = displayMetrics.widthPixels
            screenHeight = displayMetrics.heightPixels
            // 屏幕密度（1.0 / 1.5 / 2.0）
            val density = displayMetrics.density
            // 屏幕密度DPI（160 / 240 / 320）
            val densityDpi = displayMetrics.densityDpi
            val info = (" 设备型号: " + android.os.Build.MODEL
                    + ",\nSDK版本:" + android.os.Build.VERSION.SDK
                    + ",\n系统版本:" + android.os.Build.VERSION.RELEASE + "\n "
                    + MessageConstants.SCREEN_WIDTH + screenWidth
                    + "\n " + MessageConstants.SCREEN_HEIGHT + screenHeight
                    + "\n屏幕密度:  " + density
                    + "\n屏幕密度DPI: " + densityDpi)
            LogTool.d(tag, MessageConstants.DEVICE_INFO + info)
        }
    }

    fun getScreenWidth(): Int {
        return screenWidth
    }

    fun getScreenHeight(): Int {
        return screenHeight
    }

    /**
     * 得到DisplayMetrics
     */
    fun getDisplayMetrics(): DisplayMetrics? {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        windowManager ?: return null
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

}