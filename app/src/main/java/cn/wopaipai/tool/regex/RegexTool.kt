package cn.wopaipai.tool.regex

import java.util.regex.Pattern

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 15:55
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.tool.regex
+--------------+---------------------------------
+ description  +   工具類：正則表達式
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object RegexTool {
    private const val EMAIL =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    const val REPLACE_BLANK = "\t|\r|\n|\\s*"

    const val PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9!@#$%^&*_]{0,}$"
    private fun getPattern(regex: String): Pattern {

        return Pattern.compile(regex)
    }

    /*将当前的空格替换掉*/
    fun replaceBlank(src: String?): String {
        var dest = ""
        if (src != null) {
            val pattern = Pattern.compile(REPLACE_BLANK)
            val matcher = pattern.matcher(src)
            dest = matcher.replaceAll("")
        }
        return dest
    }

    //是否是中文
    fun isChinese(s: String): Boolean {
        return s.matches("[\u4e00-\u9fa5]+".toRegex())
    }

    fun isRightEmail(info: String): Boolean {

        val pattern = getPattern(EMAIL)

        val matcher = pattern.matcher(info)

        return matcher.matches()
    }
}