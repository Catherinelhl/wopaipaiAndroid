package cn.wopaipai.listener

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.DigitsKeyListener
import cn.wopaipai.constant.MessageConstants

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
+ description  +   EditText文本輸入過濾器：金额输入限制小数点后输入位数；1：默认限制小数点8位。 2：默认第一位输入小数点时，转换为0。 3：如果起始位置为0,且第二位跟的不是".",则无法后续输入。
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class AmountEditTextFilter : DigitsKeyListener(false, true) {
    private val TAG = AmountEditTextFilter::class.java.simpleName

    private var digits = 8

    fun setDigits(d: Int): AmountEditTextFilter {
        digits = d
        return this
    }

    override fun filter(
        source: CharSequence, start: Int, end: Int,
        dest: Spanned, dstart: Int, dend: Int
    ): CharSequence {
        var source = source
        var start = start
        var end = end
        val out = super.filter(source, start, end, dest, dstart, dend)


        // if changed, replace the source
        if (out != null) {
            source = out
            start = 0
            end = out.length
        }

        val len = end - start

        // if deleting, source is empty
        // and deleting can't break anything
        if (len == 0) {
            return source
        }

        //以点开始的时候，自动在前面添加0
        if (source.toString() == "." && dstart == 0) {
            return "0."
        }
        //如果起始位置为0,且第二位跟的不是".",则无法后续输入
        if (source.toString() != "." && dest.toString() == "0") {
            return MessageConstants.Empty
        }

        val dlen = dest.length

        // Find the position of the decimal .
        for (i in 0 until dstart) {
            if (dest[i] == '.') {
                // being here means, that a number has
                // been inserted after the dot
                // check if the amount of digits is right
                return if (dlen - (i + 1) + len > digits)
                    MessageConstants.Empty
                else
                    SpannableStringBuilder(source, start, end)
            }
        }

        for (i in start until end) {
            if (source[i] == '.') {
                // being here means, dot has been inserted
                // check if the amount of digits is right
                return if (dlen - dend + (end - (i + 1)) > digits)
                    MessageConstants.Empty
                else
                    break  // return new SpannableStringBuilder(source, start, end);
            }
        }


        // if the dot is after the inserted part,
        // nothing can break
        return SpannableStringBuilder(source, start, end)
    }
}