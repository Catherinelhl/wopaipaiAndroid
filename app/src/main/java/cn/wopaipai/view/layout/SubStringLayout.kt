package cn.wopaipai.view.layout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cn.wopaipai.R
import cn.wopaipai.constant.Constants
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.tool.StringTool
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.layout_substring.view.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-12 14:55
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.view
+--------------+---------------------------------
+ description  +   字符串截取
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class SubStringLayout(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {

    private var onItemSelectListener: OnItemSelectListener? = null
    private var from: String? = null
    private var isAgree = false//是否同意

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_substring, this, true)
        //获取自定义属性的值
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.subStringStyle)
        if (typedArray != null) {
            val content = typedArray.getString(R.styleable.subStringStyle_normal_content)
            val actionText = typedArray.getString(R.styleable.subStringStyle_action_text)
            val textSize = typedArray.getFloat(R.styleable.subStringStyle_text_size, 12f)
            val contentColor = typedArray.getInteger(
                R.styleable.subStringStyle_content_color,
                context.resources.getColor(R.color.grey_0A082D)
            )
            val actionColor = typedArray.getInteger(
                R.styleable.subStringStyle_action_color,
                context.resources.getColor(R.color.button_color)
            )

            typedArray.recycle()
            if (StringTool.notEmpty(content) && tv_left != null) {
                tv_left.text = content
                tv_left.run {
                    setTextSize(textSize)
                    setTextColor(contentColor)
                }
            }
            if (StringTool.notEmpty(actionText) && tv_action != null) {
                tv_action.text = actionText
                tv_action.textSize = textSize
                tv_action.setTextColor(actionColor)
            }
        }
        val subscribe = RxView.clicks(tv_action)
            .throttleFirst(Constants.Time.sleep1000, TimeUnit.MILLISECONDS)
            .subscribe {
                onItemSelectListener?.onItemSelect(null, from)
            }

        RxView.clicks(tv_left)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe {
                //重设左边图片的padding
                tv_left.compoundDrawablePadding =
                    context!!.resources.getDimensionPixelOffset(R.dimen.d10)
                isAgree = !isAgree
                //设置左边图片
                tv_left.setCompoundDrawablesWithIntrinsicBounds(
                    context!!.resources.getDrawable(
                        if (isAgree) R.mipmap.icon_check_select else R.mipmap.icon_check
                    ), null, null, null
                )
            }
    }


    fun checkIsAgree(): Boolean {
        //检查是否同意
        return isAgree
    }

    fun setOnItemSelectListener(onItemSelectListener: OnItemSelectListener, from: String) {
        this.onItemSelectListener = onItemSelectListener
        this.from = from
    }


    fun setContent(info: String) {
        if (tv_left != null) {
            tv_left.text = info
        }
    }

    fun setActionText(info: String) {
        if (tv_action != null) {
            tv_action.text = info
        }
    }
}