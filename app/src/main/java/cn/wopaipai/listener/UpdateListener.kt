package cn.wopaipai.listener

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-27 13:52
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.listener
+--------------+---------------------------------
+ description  +  更新监听
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface UpdateListener {
    fun onUpdate(isLoading: Boolean)
}