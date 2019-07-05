package cn.wopaipai.tool

import android.text.TextUtils
import cn.wopaipai.R
import cn.wopaipai.constant.MessageConstants

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
+ description  +   工具類：字符串判斷
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/

object StringTool {
    @JvmStatic
    fun isEmpty(content: String?): Boolean {
        content ?: return true
        return TextUtils.isEmpty(content.trim())
    }

    @JvmStatic
    fun notEmpty(content: String?): Boolean {
        return !isEmpty(content)
    }

    @JvmStatic
    fun equals(str1: String?, str2: String?): Boolean {
        return TextUtils.equals(str1, str2)
    }

    fun contains(seq: CharSequence?, searchSeq: CharSequence?): Boolean {
        return if (seq != null && searchSeq != null) {
            TextUtils.indexOf(seq, searchSeq, 0) >= 0
        } else {
            false
        }

    }

    /**
     * @param str
     * @return 去除不规则空格，平常空格，全角空格
     */
    fun removeIllegalSpace(str: String?): String? {
        if (str == null) {
            return null
        }
        //        char nbsp = 0x00a0;//160
        //        char qjnbsp = '\u3000';//12288
        //        return str.replace(nbsp, ' ').replace(qjnbsp, ' ');

        val newt = str.toCharArray()
        val sb = StringBuilder()
        for (w in newt) {
            val i = w.toInt()
            if (i == 160 || i == 32 || i == 12288) {
                sb.append(" ")
            } else {
                sb.append(w.toString())
            }
        }
        return sb.toString()
    }

    /**
     * 分割城市区号
     */
    fun splitCountryCode(code: String?): String {
        if (isEmpty(code)) {
            return MessageConstants.Empty
        }
        return code!!.split(".")[0]

    }

}