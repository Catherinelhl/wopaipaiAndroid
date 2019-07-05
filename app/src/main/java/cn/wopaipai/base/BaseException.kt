package cn.wopaipai.base

import cn.wopaipai.BuildConfig

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-23 16:27
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.base
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object BaseException {

    fun print(exception: Exception) {
        if (BuildConfig.DEBUG) {
            exception.printStackTrace()
        }
    }
    fun print(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()

        }
    }
}