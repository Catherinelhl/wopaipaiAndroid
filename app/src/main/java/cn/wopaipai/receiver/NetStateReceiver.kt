package cn.catherine.token.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.event.NetStateChangeEvent
import cn.wopaipai.tool.OttoTool


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
+ description  +   廣播：監聽系統網絡變化然後進行廣播
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/

class NetStateReceiver : BroadcastReceiver() {
    private var connectivityManager: ConnectivityManager? = null
    private var networkInfo: NetworkInfo? = null

    init {
        OttoTool.register(BaseApplication.context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == ConnectivityManager.CONNECTIVITY_ACTION) {
            connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            networkInfo = connectivityManager!!.activeNetworkInfo
            if (networkInfo == null || !networkInfo!!.isConnectedOrConnecting) {
                OttoTool.post(NetStateChangeEvent(false))
            } else {
                OttoTool.post(NetStateChangeEvent(true))
            }
        }
    }
}