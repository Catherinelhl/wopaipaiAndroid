package cn.wopaipai

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.text.InputFilter
import android.text.method.TransformationMethod
import android.view.LayoutInflater
import android.view.View
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.listener.AmountEditTextFilter
import cn.wopaipai.tool.PasswordCharTool
import cn.wopaipai.tool.StringTool
import kotlinx.android.synthetic.main.layout_auction_increase_dialog.*
import kotlinx.android.synthetic.main.layout_auction_increase_dialog.btn_left
import kotlinx.android.synthetic.main.layout_auction_increase_dialog.btn_right
import kotlinx.android.synthetic.main.layout_auction_increase_dialog.et_trade_password
import kotlinx.android.synthetic.main.layout_auction_increase_dialog.tv_title
import kotlinx.android.synthetic.main.layout_enter_in_dialog.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/6/16 15:07
+--------------+---------------------------------
+ projectName  +   wopaipai
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.view.dialog
+--------------+---------------------------------
+ description  +    竞拍加价
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class AuctionIncreaseDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    private val TAG = AuctionIncreaseDialog::class.java.simpleName

    //define the second constructor
    constructor(context: Context) : this(context, 0)

    // define a constructor that no params
    constructor() : this(BaseApplication.context)

    private var confirmClickListener: ConfirmClickListener? = null

    init {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_auction_increase_dialog, null)
        setContentView(view)
        btn_left.setOnClickListener {
            confirmClickListener?.let {
                it.cancel()
                this.dismiss()
            }
        }
        et_trade_password.transformationMethod = object : TransformationMethod {
            override fun getTransformation(source: CharSequence?, view: View?): CharSequence {
                return if (source === null) {
                    MessageConstants.Empty
                } else {
                    PasswordCharTool(source)

                }

            }

            override fun onFocusChanged(
                view: View?,
                sourceText: CharSequence?,
                focused: Boolean,
                direction: Int,
                previouslyFocusedRect: Rect?
            ) {
            }
        }
        btn_right.setOnClickListener {
            confirmClickListener?.let {

                if (et_trade_password != null) {
                    val password = et_trade_password.text
                    if (StringTool.notEmpty(password.toString())) {
                        it.sure(password.toString())
                        this.dismiss()
                    } else {
                        if (StringTool.isEmpty(password.toString())) {
                            it.toast(context.getString(R.string.please_input_trade_password))
                        }
                    }
                }
            }
        }
    }

    fun setLeftText(left: String): AuctionIncreaseDialog {
        if (StringTool.isEmpty(left)) return this
        btn_left.text = left
        return this

    }

    fun setRightText(right: String): AuctionIncreaseDialog {
        if (StringTool.isEmpty(right)) return this
        btn_right.text = right
        return this

    }

    fun setContent(content: String): AuctionIncreaseDialog {
        if (StringTool.isEmpty(content)) return this
        tv_rule.text = content
        return this

    }

    fun setLowestPriceRules(content: String): AuctionIncreaseDialog {
        if (StringTool.isEmpty(content)) return this
        tv_lowest_increase_price.text =
            MessageConstants.Space + content + MessageConstants.Space + MessageConstants.AUCTION_TYPE
        return this

    }

    fun setLowestPriceRulesPre(content: String): AuctionIncreaseDialog {
        if (StringTool.isEmpty(content)) return this
        tv_increase_amount_str.text = content
        return this

    }

    fun setTitle(title: String): AuctionIncreaseDialog {
        if (StringTool.isEmpty(title)) return this
        tv_title.text = title
        return this

    }

    fun setOnConfirmClickListener(confirmClickListener: ConfirmClickListener): AuctionIncreaseDialog {
        this.confirmClickListener = confirmClickListener
        return this
    }

    interface ConfirmClickListener {
        fun sure(tradePassword: String)

        fun cancel()
        fun toast(info: String)
    }


    var baseIncreaseDialog: AuctionIncreaseDialog? = null

    /**
     * 显示对话框
     *
     * @param message
     * @param listener
     */
    fun showDialog(
        context: Context,
        message: String,
        listener: AuctionIncreaseDialog.ConfirmClickListener
    ) {
        showDialog(
            context,
            context.resources.getString(R.string.bidding_increase),
            context.resources.getString(R.string.cancel),
            context.resources.getString(R.string.confirm),
            context.resources.getString(R.string.auction_price_rules),
            message,
            listener
        )
    }

    /**
     * 显示对话框
     *
     * @param title
     * @param left
     * @param right
     * @param message
     * @param listener
     */
    fun showDialog(
        context: Context,
        title: String,
        left: String,
        right: String,
        message: String,
        lowestPrice: String,
        listener: AuctionIncreaseDialog.ConfirmClickListener
    ) {
        if (baseIncreaseDialog == null) {
            baseIncreaseDialog = AuctionIncreaseDialog(context)
        }
        baseIncreaseDialog?.let {
            /*设置弹框点击周围不予消失*/
            it.setCanceledOnTouchOutside(false)
            /*设置弹框背景*/
            it.window.setBackgroundDrawable(context.resources.getDrawable(R.drawable.bg_white))
            it.setLeftText(left)
                .setRightText(right)
                .setContent(message)
                .setLowestPriceRules(lowestPrice)
                .setLowestPriceRulesPre(context.resources.getString(R.string.lowest_price_rules))
                .setTitle(title)
                .setOnConfirmClickListener(listener).show()
        }

    }

}