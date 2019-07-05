package cn.wopaipai.listener

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-17 16:30
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.listener
+--------------+---------------------------------
+ description  +   文本变化回调
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface OnTextChangeListener {
    fun onTextChange(content: String?)
    fun onComplete(content: String)
}