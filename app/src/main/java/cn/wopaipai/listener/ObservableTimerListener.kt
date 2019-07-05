package cn.wopaipai.listener

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 15:37
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.listener
+--------------+---------------------------------
+ description  +   OBservable 定时监听回调
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/

interface ObservableTimerListener {
    fun timeUp(message: String)
}