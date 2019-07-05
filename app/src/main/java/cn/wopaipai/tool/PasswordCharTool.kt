package cn.wopaipai.tool

import cn.wopaipai.constant.Constants

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-07-05 15:40
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.tool
+--------------+---------------------------------
+ description  +  密码输入框
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class PasswordCharTool(private val source: CharSequence) : CharSequence {
    override val length: Int
        get() = source.length
    override fun get(index: Int): Char = Constants.BlackDot

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
        source.subSequence(startIndex, endIndex) // Return default
}