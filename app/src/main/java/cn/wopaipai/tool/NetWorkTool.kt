package cn.wopaipai.tool

import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 10:40
+--------------+---------------------------------
+ projectName  +   wopaipai
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.base
+--------------+---------------------------------
+ description  +   工具類：網絡管理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object NetWorkTool {
    /*HTTP 連接超時*/
    fun connectTimeOut(throwable: Throwable): Boolean {
        return (throwable is UnknownHostException
                || throwable is SocketTimeoutException
                || throwable is ConnectException)
    }

    /*TCP 連接超時*/
    fun tcpConnectTimeOut(e: Exception): Boolean {
        return (e is ConnectException
                || e is SocketTimeoutException
                || e is UnknownHostException
                || e is SocketException)
    }

    fun NeedReset(e: Exception): Boolean {
        //            if (e instanceof SocketException) {
        //                // 如果当前已经是连接到的状态，那么就不需要重新连接了
        //                if (e.toString().equals(MessageConstants.ALREADY_CONNECTED)) {
        //                } else {
        //                    if (e.getMessage() != null) {
        //                        //如果当前连接不上，代表需要重新设置AN,内网5s，外网10s
        //                        resetSAN();
        //                    }
        //                }
        //            }
        return e.message != null
    }
}