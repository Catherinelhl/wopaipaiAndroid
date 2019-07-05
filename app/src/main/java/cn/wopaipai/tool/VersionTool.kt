package cn.wopaipai.tool

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import cn.wopaipai.constant.Constants
import cn.wopaipai.base.BaseApplication

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 15:55
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.tool
+--------------+---------------------------------
+ description  +    工具類：获取当前APP版本信息
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object  VersionTool {
    /**
     * 获取当前的版本信息
     *
     * @param context
     * @return
     */
    fun getVersionInfo(context: Context): String? {
        val info = getPackageInfo(context)
        return if (info != null) info.versionName + "( " + info.versionCode + " )" else null
    }

    /**
     * 获取当前的版本code
     *
     * @param context
     * @return
     */
    fun getVersionCode(context: Context): Int {
        var versionCode = 0
        val packageInfo = getPackageInfo(context)
        if (packageInfo != null) {
            versionCode = packageInfo.versionCode
        }
        return versionCode
    }

    /**
     * 得到包信息
     *
     * @param context
     * @return
     */
    private fun getPackageInfo(context: Context): PackageInfo? {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = context.packageManager.getPackageInfo(context.packageName, 16384)
        } catch (var3: PackageManager.NameNotFoundException) {
        }

        return packageInfo
    }

    /**
     * 获取版本名字
     *
     * @param context
     * @return
     */
    fun getVersionName(context: Context): String? {
        val info = getPackageInfo(context)
        return info?.versionName
    }

    /**
     * 比对当前的版本和服务器返回的名字是否一致，否则进行比较判断是否需要更新
     *
     * @param serverVersionName 服务器返回的版本名字
     */
    fun needUpdate(serverVersionName: String): Boolean {
        //当前的版本名字
        val currentVersionName =getVersionName(BaseApplication.context)
        if (StringTool.isEmpty(currentVersionName)) {
            return false
        }
        if (StringTool.isEmpty(serverVersionName)) {
            return false
        }
        //1:解析当前本地的版本信息
        val localVersionSplit =
            currentVersionName!!.split(Constants.SYMBOL_DOT.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        //2:解析服务器传回的版本信息
        val serverVersionSplit =
            serverVersionName.split(Constants.SYMBOL_DOT.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        //3:比较两者是否相等，如果服务器的大于本地的，那么需要提示更新
        if (localVersionSplit.size < 3) {
            return false
        }
        if (serverVersionSplit.size < 3) {
            return false
        }
        if (Integer.valueOf(localVersionSplit[0]) < Integer.valueOf(serverVersionSplit[0])) {
            return true
        }
        if (Integer.valueOf(localVersionSplit[1]) < Integer.valueOf(serverVersionSplit[1])) {
            return true
        }
        return Integer.valueOf(localVersionSplit[2]) < Integer.valueOf(serverVersionSplit[2])

    }
}