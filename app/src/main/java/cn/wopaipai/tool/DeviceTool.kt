package cn.wopaipai.tool

import android.app.ActivityManager
import android.app.UiModeManager
import android.content.Context
import android.content.Context.*
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import android.text.format.Formatter
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseException
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException

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
+ description  +   工具類：设备信息
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class DeviceTool {
    private val TAG = DeviceTool::class.java.simpleName

    fun getMemoryInfo(tag: String) {
        val manager = BaseApplication.context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val mi = ActivityManager.MemoryInfo()
        manager.getMemoryInfo(mi)

        Formatter.formatFileSize(BaseApplication.context, mi.availMem)// 将获取的内存大小规格化
        LogTool.i(
            TAG,
            "[" + tag + "]\t 可用内存：" + Formatter.formatFileSize(BaseApplication.context, mi.availMem)
                    + ";\t总内存:" + Formatter.formatFileSize(BaseApplication.context, mi.totalMem)
                    + ";\t阀值：" + Formatter.formatFileSize(BaseApplication.context, mi.threshold)
        )
    }

    /**
     * 获取设备的内存信息
     *
     * @return
     */
    fun getDeviceMemoryInfo(): String {
        val manager = BaseApplication.context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        //获取Android设备限定的一个应用程序占用的内存限制;
        val memoryClass = manager.memoryClass

        //获取运行期间，内存的使用情况
        val runtime = Runtime.getRuntime()
        //当前程序在当前时间，整个分配的内存空间，这个空间可以增加，有虚拟机自己来处理
        val totalMemory = runtime.totalMemory()
        val maxMemory = runtime.maxMemory()
        //当前程序在当前时间，虚拟机分配的内存中可用的内存空间尺寸
        val freeMemory = runtime.freeMemory()

        val mi = ActivityManager.MemoryInfo()
        manager.getMemoryInfo(mi)

        Formatter.formatFileSize(BaseApplication.context, mi.availMem)// 将获取的内存大小规格化
        //  + ";\r 可用内存：" + mi.availMem
        //  (1024 * 1024)
        //        "TotalMemory:" + Formatter.formatFileSize(BaseApplication.context(), totalMemory)
        //                + ";\tFreeMemory:" + Formatter.formatFileSize(BaseApplication.context(), freeMemory)
        //                + ";\tMaxMemory:" + Formatter.formatFileSize(BaseApplication.context(), maxMemory)
        //                + ";\tlargerMemory:" + manager.getLargeMemoryClass()
        //                +
        //        + ";\t 是否低内存：" + mi.lowMemory;//当前可用内存
        //   + ";\t内存:" + memoryClass
        return ("\t 可用内存：" + Formatter.formatFileSize(BaseApplication.context, mi.availMem)
                + ";\t总内存:" + Formatter.formatFileSize(BaseApplication.context, mi.totalMem)
                + ";\t阀值：" + Formatter.formatFileSize(BaseApplication.context, mi.threshold))

    }

    // 获取手机CPU信息
    fun getCpuInfo(): String {
        val str1 = "/proc/cpuinfo"
        var str2 = ""
        val cpuInfo = arrayOf("", "") // 1-cpu型号 //2-cpu频率
        var arrayOfString: Array<String>
        try {
            val fr = FileReader(str1)
            val localBufferedReader = BufferedReader(fr, 8192)
            str2 = localBufferedReader.readLine()
            arrayOfString =
                str2.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in 2 until arrayOfString.size) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " "
            }
            str2 = localBufferedReader.readLine()
            arrayOfString =
                str2.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            cpuInfo[1] += arrayOfString[2]
            localBufferedReader.close()
        } catch (e: IOException) {
            LogTool.e(TAG, e.toString())
        }

        //+ "2-cpu频率:" + cpuInfo[1]
        return MessageConstants.Device.CPU_INFO + cpuInfo[0]
    }


    fun getTotalRam(): String {//GB
        val path = "/proc/meminfo"
        var firstLine: String? = null
        var totalRam = 0
        try {
            val fileReader = FileReader(path)
            val br = BufferedReader(fileReader, 8192)
            firstLine =
                br.readLine().split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
            br.close()
        } catch (e: Exception) {
            BaseException.print(e)
        }

        if (firstLine != null) {
            totalRam =
                Math.ceil((java.lang.Float.valueOf(firstLine) / (1024 * 1024)).toDouble()).toInt()
        }

        return totalRam.toString() + "GB"//返回1GB/2GB/3GB/4GB
    }

    fun getDeviceOSInfo(): String {
        //硬件制造商（MANUFACTURER)
        val manufacturer = android.os.Build.MANUFACTURER
        //品牌名称（BRAND）
        val brand = android.os.Build.BRAND
        //主板名称（BOARD）
        val board = android.os.Build.BOARD
        //设备名 （DEVICE）
        val device = android.os.Build.DEVICE
        //型号（MODEL）:即用户可见的名称
        val model = android.os.Build.MODEL
        //显示屏参数（DISPLAY）
        val display = android.os.Build.DISPLAY
        //产品名称（PRODUCT）：即手机厂商
        val product = android.os.Build.PRODUCT
        //设备唯一识别码（FINGER_PRINT）
        val fingerPrint = android.os.Build.FINGERPRINT
        LogTool.d(
            TAG, MessageConstants.Device.MANUFACTURER + manufacturer,
            MessageConstants.Device.BRAND + brand,
            MessageConstants.Device.BOARD + board,
            MessageConstants.Device.NAME + device,
            MessageConstants.Device.MODEL + model,
            MessageConstants.Device.DISPLAY + display,
            MessageConstants.Device.PRODUCT + product,
            MessageConstants.Device.FINGER_PRINT + fingerPrint
        )
        return brand
    }

    /**
     * 检查当前是否是TV
     *
     * @return
     */
    fun checkIsTV(): Boolean {
        val uiModeManager =
            BaseApplication.context.getSystemService(UI_MODE_SERVICE) as UiModeManager
                ?: return false
        if (uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION) {
            LogTool.d(TAG, MessageConstants.Device.TV_DEVICE)
            return true
        } else {
            LogTool.d(TAG, MessageConstants.Device.NON_TV_DEVICE)
            return false
        }


    }

    /**
     * 获取当前移动设备的Ip信息
     *
     * @return
     */
    fun getIpAddress(): String? {
        val info = (BaseApplication.context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        if (info != null && info.isConnected) {
            // 3/4g网络
            if (info.type == ConnectivityManager.TYPE_MOBILE) {
                try {
                    val en = NetworkInterface.getNetworkInterfaces()
                    while (en.hasMoreElements()) {
                        val networkInterface = en.nextElement()
                        val enumeration = networkInterface.inetAddresses
                        while (enumeration.hasMoreElements()) {
                            val interAddress = enumeration.nextElement()
                            if (!interAddress.isLoopbackAddress && interAddress is Inet4Address) {
                                return interAddress.getHostAddress()
                            }
                        }
                    }
                } catch (e: SocketException) {
                    BaseException.print(e)
                }

            } else if (info.type == ConnectivityManager.TYPE_WIFI) {
                //  wifi网络
                val wifiManager =
                    BaseApplication.context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
                        ?: return getLocalIp()
                val wifiInfo = wifiManager.connectionInfo
                return intIP2StringIP(wifiInfo.ipAddress)
            } else if (info.type == ConnectivityManager.TYPE_ETHERNET) {
                // 有限网络
                return getLocalIp()
            }
        }
        return null
    }

    private fun intIP2StringIP(ip: Int): String {
        return (ip and 0xFF).toString() + "." +
                (ip shr 8 and 0xFF) + "." +
                (ip shr 16 and 0xFF) + "." +
                (ip shr 24 and 0xFF)
    }


    /**
     * 获取有限网IP
     *
     * @return
     */
    private fun getLocalIp(): String {
        try {
            val en = NetworkInterface
                .getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val networkInterface = en.nextElement()
                val enumeration = networkInterface
                    .inetAddresses
                while (enumeration.hasMoreElements()) {
                    val inetAddress = enumeration.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (ex: SocketException) {
            LogTool.d(TAG, ex.message)
        }

        return Constants.LOCAL_DEFAULT_IP

    }

    /**
     * 判断当前是否是TV
     *
     *
     * 电视和手机的差异：
     * 屏幕物理尺寸不同。
     * 布局尺寸不同。
     * SIM 卡的状态不同。
     * 电源接入的方式不同。
     */
    //检查当前屏幕尺寸,小于6.5认为是手机，否则是电视
    private fun checkScreenIsPhone(): Boolean {
        LogTool.d(TAG, MessageConstants.Device.CHECK_SIM_STATUS_IS_TV)
        val displayMetrics = BaseApplication().getDisplayMetrics()
        if (displayMetrics != null) {
            val x = Math.pow((displayMetrics.widthPixels / displayMetrics.xdpi).toDouble(), 2.0)
            val y = Math.pow((displayMetrics.heightPixels / displayMetrics.ydpi).toDouble(), 2.0)
            LogTool.d(TAG, x)
            LogTool.d(TAG, y)
            //屏幕尺寸
            val screenInches = Math.sqrt(x + y)
            return screenInches < 6.5
        }
        return false
    }

    /**
     * 通过检查布局是否是手机
     *
     * @param context
     * @return
     */
    private fun checkLayoutIsPhone(context: Context): Boolean {
        return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK <= Configuration.SCREENLAYOUT_SIZE_LARGE

    }

    /**
     * 检查SIM信息来比对是否是TV
     *
     * @param context
     * @return
     */
    private fun checkSIMStatusIsPhone(context: Context): Boolean {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.phoneType != TelephonyManager.PHONE_TYPE_NONE
    }

    /**
     * 检查当前是否是手机
     *
     * @param context
     * @return
     */
    fun checkIsPhone(context: Context): Boolean {
        return checkLayoutIsPhone(context) && checkSIMStatusIsPhone(context)
    }

    /**
     * 得到当前设备的model
     *
     * @return
     */
    fun getDeviceModel(): String {
        return android.os.Build.BRAND + MessageConstants.Space + android.os.Build.MODEL
    }

    /**
     * 应用区的顶端位置即状态栏的高度
     * *注意*
     * 该方法不能在初始化的时候用
     */
    fun getStatusTop() {
        var height = 0
        val resourceId =
            BaseApplication.context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = BaseApplication.context.resources.getDimensionPixelSize(resourceId)
        }
        LogTool.d(TAG, "getStatusTop$height")

    }

}