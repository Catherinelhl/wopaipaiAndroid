package cn.catherine.token.tool.language

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import cn.wopaipai.constant.Constants
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.PreferenceTool
import cn.wopaipai.tool.StringTool
import java.util.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 10:40
+--------------+---------------------------------
+ projectName  +   wopaipai
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.tool
+--------------+---------------------------------
+ description  +   國際化語言管理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object LanguageTool {
    private val TAG = LanguageTool::class.java.simpleName
    /**
     * 設置本地語言
     *
     * @param context
     * @return
     */
    fun setLocal(context: Context): Context {
        return updateLanguage(context, getLanguageLocal(context))
    }

    /**
     * 切換語言
     *
     * @param locale 當前的語言環境
     */
    private fun updateLanguage(context: Context, locale: Locale): Context {
        var context = context
        // 1：获得res资源对象
        val resources = context.resources
        //2： 获得设置对象
        val config = Configuration(resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
        } else {
            config.locale = locale
            //获得屏幕参数：主要是分辨率，像素等
            resources.updateConfiguration(config, resources.displayMetrics)

        }
        return context
    }


    /**
     * 設置Application語言
     *
     * @param locale
     */
    fun setApplicationLanguage(context: Context, locale: Locale) {
        // 1：获得res资源对象
        val resources = context.applicationContext.resources
        //2： 获得设置对象
        val config = resources.configuration
        //3： 获得屏幕参数：主要是分辨率，像素等。
        val dm = resources.displayMetrics
        BaseApplication.isZH = (locale === Locale.CHINA)
        config.locale = locale // 简体中文
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.locales = localeList
            BaseApplication.context.createConfigurationContext(config)
            Locale.setDefault(locale)
        }
        resources.updateConfiguration(config, dm)
    }


    /**
     * 配置改變的時候調用
     *
     * @param context
     */
    fun onConfigurationChanged(context: Context) {
        setLocal(context)
        setApplicationLanguage(context, getLanguageLocal(context))
    }

    /*獲取當前語言環境轉換成Local的形式*/
    fun getLanguageLocal(context: Context): Locale {
        val currentString = getCurrentLanguageString(context)
        //3:匹配當前的語言獲取，返回APP裡面識別的TAG
        return if (StringTool.equals(currentString, Constants.Language.CN)) {
            Locale.CHINA
        } else {
            Locale.ENGLISH

        }

    }

    /*獲取當前語言環境*/
    fun getCurrentLanguageString(context: Context): String {
        // 1：檢查應用是否已經有用戶自己存儲的語言種類
        var currentString =
            PreferenceTool.getInstance(context).getString(Constants.Preference.LANGUAGE_TYPE)
        if (StringTool.isEmpty(currentString)) {
            //2:當前的選中為空，那麼就默認讀取當前系統的語言環境
            val locale = Locale.getDefault()
            //locale.getLanguage();//zh  是中國
            currentString = locale.country//CN-簡體中文，TW、HK-繁體中文
        }
        //3:匹配當前的語言獲取，返回APP裡面識別的TAG
        return if (StringTool.equals(currentString, Constants.Language.CN)) {
            currentString!!
        } else {
            Constants.Language.EN

        }

    }
}