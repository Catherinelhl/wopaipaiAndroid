package cn.wopaipai.base

import cn.wopaipai.gson.ResponseJson


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
+ description  +  Contract 基类
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/
interface BaseContract {
    interface View {
        //显示加载框
        fun showLoading()

        //隐藏加载框
        fun hideLoading()

        //Http访问状态异常
        fun httpExceptionStatus(responseJson: ResponseJson): Boolean

        //连接失败，请检查网路
        fun connectFailure()

        //没有网络
        fun noNetWork()

    }

    interface Presenter
}