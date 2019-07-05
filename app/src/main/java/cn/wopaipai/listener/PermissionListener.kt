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
+ description  +   检查当前权限回掉监听
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface PermissionListener {
    //已经获取到权限
    fun getPermissionSuccess()

    //获取权限失败
    fun getPermissionFailure()
}