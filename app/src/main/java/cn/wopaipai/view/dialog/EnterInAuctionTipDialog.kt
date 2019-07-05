package cn.wopaipai

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.text.method.TransformationMethod
import android.view.LayoutInflater
import android.view.View
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.tool.PasswordCharTool
import cn.wopaipai.tool.StringTool
import kotlinx.android.synthetic.main.layout_enter_in_dialog.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/6/17 17:07
+--------------+---------------------------------
+ projectName  +   wopaipai
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.view.dialog
+--------------+---------------------------------
+ description  +    确认进入竞拍提示
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class EnterInAuctionTipDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    private val TAG = EnterInAuctionTipDialog::class.java.simpleName

    //define the second constructor
    constructor(context: Context) : this(context, 0)

    // define a constructor that no params
    constructor() : this(BaseApplication.context)

    private var confirmClickListener: ConfirmClickListener? = null

    init {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_enter_in_dialog, null)
        setContentView(view)
//        et_content.filters = arrayOf<InputFilter>(AmountEditTextFilter().setDigits(8))
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
                //检查当前的输入框输入信息
                if (et_trade_password != null) {
                    var password = et_trade_password.text.toString()
                    println(password)
//                            if (StringTool.isEmpty(password)) {
//                                activity.showToast("请输入交易密码～～～")
//                                return
//                            }
                    it.sure(password)
                    this.dismiss()
                }
            }
        }

        if (BuildConfig.LazyMode) {
            et_trade_password.setText("111111")
        }
    }

    fun setLeftText(left: String): EnterInAuctionTipDialog {
        if (StringTool.isEmpty(left)) return this
        btn_left.text = left
        return this

    }

    fun setRightText(right: String): EnterInAuctionTipDialog {
        if (StringTool.isEmpty(right)) return this
        btn_right.text = right
        return this

    }

    fun setHint(hint: String): EnterInAuctionTipDialog {
        if (StringTool.isEmpty(hint)) return this
        et_trade_password.hint = hint
        return this

    }

    /**
     * 设置余额信息
     */
    fun setBalance(balance: String?, content: String?): EnterInAuctionTipDialog {
        content ?: return this
        tv_account_balance_key.text = content
        tv_account_balance.text =
            balance + MessageConstants.Space + MessageConstants.AUCTION_TYPE
        return this

    }

    /**
     * 设置中间文字信息
     */
    fun setContent(content: String): EnterInAuctionTipDialog {
        if (StringTool.isEmpty(content)) return this
        tv_content.text = content
        return this
    }


    fun setTitle(title: String): EnterInAuctionTipDialog {
        if (StringTool.isEmpty(title)) return this
        tv_title.text = title
        return this

    }

    fun setOnConfirmClickListener(confirmClickListener: ConfirmClickListener): EnterInAuctionTipDialog {
        this.confirmClickListener = confirmClickListener
        return this
    }

    interface ConfirmClickListener {
        fun sure(tradePassword: String)

        fun cancel()
    }


    var baseIncreaseDialog: EnterInAuctionTipDialog? = null

    /**
     * 显示对话框
     *
     * @param message
     * @param listener
     */
    fun showDialog(
        context: Context,
        message: String,
        listener: EnterInAuctionTipDialog.ConfirmClickListener
    ) {
        showDialog(
            context,
            context.resources.getString(R.string.sure_to_enter_in),
            context.resources.getString(R.string.cancel),
            context.resources.getString(R.string.confirm),
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
        listener: EnterInAuctionTipDialog.ConfirmClickListener
    ) {
        if (baseIncreaseDialog == null) {
            baseIncreaseDialog = EnterInAuctionTipDialog(context)
        }
        baseIncreaseDialog?.let {
            /*设置弹框点击周围不予消失*/
            it.setCanceledOnTouchOutside(false)
            /*设置弹框背景*/
            it.window.setBackgroundDrawable(context.resources.getDrawable(R.drawable.bg_white))
            it.setLeftText(left)
                .setRightText(right)
                .setContent(
                    context.resources.getString(R.string.enter_in_auction_tip_pre) + MessageConstants.Space +
                            message + MessageConstants.Space + MessageConstants.AUCTION_TYPE + MessageConstants.Space + context.resources.getString(
                        R.string.enter_in_auction_tip_last
                    )
                )
                .setBalance(
                    BaseApplication.balance.toString(),
                    context.resources.getString(R.string.current_account_balance_str)
                )
                .setHint(context.resources.getString(R.string.trade_password))
                .setTitle(title)
                .setOnConfirmClickListener(listener).show()
        }

    }

}