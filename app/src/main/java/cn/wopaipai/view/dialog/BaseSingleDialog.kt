package cn.wopaipai
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.StringTool
import kotlinx.android.synthetic.main.layout_single_dialog.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/14 16:33
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.view.dialog
+--------------+---------------------------------
+ description  +   自定義Dialog：单个按钮提示框
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class BaseSingleDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    private val TAG = BaseSingleDialog::class.java.simpleName
    private var confirmClickListener: ConfirmClickListener? = null
    //define the second constructor
    constructor(context: Context) : this(context, 0)

    // define a constructor that no params
    constructor() : this(BaseApplication.context)

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_single_dialog, null)
        setContentView(view)
        initListener()
    }

    fun setLeftText(left: String): BaseSingleDialog {
        if (StringTool.isEmpty(left)) return this
        btn_sure.text = left
        return this

    }

    fun setContent(content: String): BaseSingleDialog {
        if (StringTool.isEmpty(content)) return this
        LogTool.d(TAG,content)
        tv_content.text = content
        return this

    }

    fun setTitle(title: String): BaseSingleDialog {
        if (StringTool.isEmpty(title)) return this
        tv_title.text = title
        return this
    }

    fun initListener() {
        btn_sure.setOnClickListener { v -> confirmClickListener!!.sure() }
    }


    fun setOnConfirmClickListener(confirmClickListener: ConfirmClickListener): BaseSingleDialog {
        this.confirmClickListener = confirmClickListener
        return this
    }

    interface ConfirmClickListener {
        fun sure()
    }

    var basSingleDialog: BaseSingleDialog? = null


    fun showDialog(
        context: Context,
        message: String,
        listener: BaseSingleDialog.ConfirmClickListener
    ) {
        showDialog(context, context.resources.getString(R.string.warning),
            context.resources.getString(R.string.confirm),message, listener)
    }

    /**
     * 显示单个 按钮对话框
     *
     * @param title
     * @param message
     * @param listener
     */
    fun showDialog(
        context: Context,
        title: String,
        btnString: String,
        message: String,
        listener: BaseSingleDialog.ConfirmClickListener
    ) {
        if (basSingleDialog == null) {
            basSingleDialog = BaseSingleDialog(context)
        }
        basSingleDialog?.let {
            /*设置弹框点击周围不予消失*/
            it.setCanceledOnTouchOutside(false)
            it.setCancelable(false)
            /*设置弹框背景*/
            it.window.setBackgroundDrawable(context.resources.getDrawable(R.drawable.bg_white))
            it.setContent(message)
                .setTitle(title)
                .setLeftText(btnString)
                .setOnConfirmClickListener(object : ConfirmClickListener {
                    override fun sure() {
                        listener.sure()
                        it.dismiss()
                    }

                }).show()
        }

    }
}