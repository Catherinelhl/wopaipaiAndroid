package cn.wopaipai.tool

import android.util.Log

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 10:40
+--------------+---------------------------------
+ projectName  +   wopaipai
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.tool
+--------------+---------------------------------
+ description  +   工具类：日志管理
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/

object LogTool {
    private const val D = "D"
    private const val E = "E"
    private const val V = "V"
    private const val I = "I"
    private const val line = "----------------------------"

    @JvmStatic
    fun <T> d(tag: String, values: T?) {
        if (values == null) return
        printf(D, tag, values.toString())
    }

    @JvmStatic
    fun <T> d(tag: String, valuesTag: T, values: T?) {
        if (values == null) return
        printf(D, tag, valuesTag.toString() + values)
    }

    @JvmStatic
    fun <T> i(tag: String, values: T?) {
        if (values == null) return
        printf(I, tag, values.toString())
    }

    @JvmStatic
    fun d(tag: String) {
        printf(D, tag, line)
    }

    @JvmStatic
    fun d(tag: String, vararg values: String) {
        printf(D, tag, *values)
    }

    @JvmStatic
    fun e(tag: String, vararg values: String) {
        printf(E, tag, *values)
    }

    @JvmStatic
    fun v(tag: String, vararg values: String) {
        printf(V, tag, *values)
    }

    private fun printf(mark: String, tag: String, vararg values: String) {
        //需要打印的内容
        val value = StringBuffer()
        for (i in values.indices) {
            value.append(values[i])
            if (i == values.size - 1) {
                break
            }
            value.append(", ")
        }
        val valueStr = value.toString()
        // 打印
        when (mark) {
            D -> printfLine(D, tag, valueStr)
            E -> printfLine(E, tag, valueStr)
            V -> printfLine(V, tag, valueStr)
            I -> printfLine(I, tag, valueStr)
        }
    }

    private fun getPosition(tag: String): String? {
        val sb = StringBuilder()
        val element = getTargetStack(tag) ?: return null

        //        sb.append(".")// 我电脑的AndroidStudio有点问题，必须在这加个点，在logcat中才能定位。AndroidStudio升级后，这个问题不存在了。
        sb.append(".")
            .append("(")
            .append(element.fileName)
            .append(":")
            .append(element.lineNumber)
            .append(")")
        return sb.toString()
    }

    private fun printfLine(mark: String, tag: String, msg: String) {
        val startLine = getPosition(tag)
        when (mark) {
            D -> {
                Log.d(tag, " ")
                Log.d(startLine, msg)
            }
            E -> {
                Log.e(tag, " ")
                Log.e(startLine, msg)
            }
            V -> {
                Log.v(tag, " ")
                Log.v(startLine, msg)
            }
            I -> {
                Log.i(tag, " ")
                Log.i(startLine, msg)
            }
        }


    }

    /**
     * 获取最后调用我们log的StackTraceElement
     *
     * @param tag 目标类的SimpleName
     * @return
     */

    private fun getTargetStack(tag: String): StackTraceElement? {
        for (element in Thread.currentThread().stackTrace) {
            if (element.className.contains(tag)) {
                //返回调用位置的 element
                return element
            }

        }

        return null
    }

}