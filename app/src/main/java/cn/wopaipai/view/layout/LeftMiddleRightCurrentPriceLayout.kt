package cn.wopaipai.view.layout

import android.content.Context
import android.graphics.Color
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cn.wopaipai.R
import kotlinx.android.synthetic.main.layout_left_middle_right_current_price.view.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-15 14:55
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.view
+--------------+---------------------------------
+ description  +   当前价格的Layout自定义
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class LeftMiddleRightCurrentPriceLayout(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_left_middle_right_current_price, this, true)

        //获取自定义属性的值
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.leftMiddleRightLayoutStyle)
        if (typedArray != null) {
            //获取左中右的文字内容
            val leftContent =
                typedArray.getString(R.styleable.leftMiddleRightLayoutStyle_leftContent)
            val middleContent =
                typedArray.getString(R.styleable.leftMiddleRightLayoutStyle_middleContent)
            val rightContent =
                typedArray.getString(R.styleable.leftMiddleRightLayoutStyle_rightContent)

            //获取左中右的文字颜色
            val leftColor = typedArray.getInt(
                R.styleable.leftMiddleRightLayoutStyle_left_text_color,
                context.resources.getColor(R.color.orange_FAAE00)
            )
            val middleColor = typedArray.getInt(
                R.styleable.leftMiddleRightLayoutStyle_middle_text_color,
                context.resources.getColor(R.color.orange_FAAE00)
            )
            val rightColor = typedArray.getInt(
                R.styleable.leftMiddleRightLayoutStyle_right_text_color,
                context.resources.getColor(R.color.orange_FAAE00)
            )
            //获取左中右的文字大小
            val leftTextSize =
                typedArray.getFloat(R.styleable.leftMiddleRightLayoutStyle_left_text_size, 10f)
            val middleTextSize =
                typedArray.getFloat(R.styleable.leftMiddleRightLayoutStyle_middle_text_size, 24f)
            val rightTextSize =
                typedArray.getFloat(R.styleable.leftMiddleRightLayoutStyle_right_text_size, 10f)

            leftContent?.let {
                tv_left_content.text = it
            }
            middleContent?.let {
                tv_middle_content.text = it
            }
            rightContent?.let {
                tv_right_content.text = it
            }


            setLeftColor(leftColor)
            setMiddleColor(middleColor)
            setRightColor(rightColor)

            setLeftTextSize(leftTextSize)
            setRightTextSize(rightTextSize)
            setMiddleTextSize(middleTextSize)

        }
    }

    /**
     *
     * 设置当前的左边文本
     * 当前的价格
     */
    fun setLeftContent(info: String): LeftMiddleRightCurrentPriceLayout {
        if (tv_left_content != null) {
            tv_left_content.text = info
        }
        return this
    }

    fun setLeftColor(resId: Int): LeftMiddleRightCurrentPriceLayout {

        if (tv_left_content != null) {
                tv_left_content.setTextColor(resId)
        }
        return this
    }

    fun setLeftTextSize(textSize: Float): LeftMiddleRightCurrentPriceLayout {
        if (tv_left_content != null) {
            tv_left_content.textSize = textSize
        }
        return this
    }

    /**
     *
     * 设置当前的中间文本
     * 当前的价格
     */
    fun setMiddleContent(info: String): LeftMiddleRightCurrentPriceLayout {
        if (tv_middle_content != null) {
            tv_middle_content.text = info
        }
        return this
    }

    fun setMiddleColor(resId: Int): LeftMiddleRightCurrentPriceLayout {
        if (tv_middle_content != null) {
                tv_middle_content.setTextColor(resId)
        }
        return this
    }

    fun setMiddleTextSize(textSize: Float): LeftMiddleRightCurrentPriceLayout {
        if (tv_middle_content != null) {
            tv_middle_content.textSize = textSize
        }
        return this
    }


    /**
     *    设置当前的右边文本
     */
    fun setRightContent(info: String): LeftMiddleRightCurrentPriceLayout {
        if (tv_right_content != null) {
            tv_right_content.text = info
        }
        return this
    }

    fun setRightColor(resId: Int): LeftMiddleRightCurrentPriceLayout {
        if (tv_right_content != null) {
                tv_right_content.setTextColor(resId)
        }
        return this
    }

    fun setRightTextSize(textSize: Float): LeftMiddleRightCurrentPriceLayout {
        if (tv_right_content != null) {
            tv_right_content.textSize = textSize
        }
        return this
    }

}