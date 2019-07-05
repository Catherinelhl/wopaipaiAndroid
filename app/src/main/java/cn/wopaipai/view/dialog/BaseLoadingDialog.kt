package cn.catherine.token.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import cn.wopaipai.BuildConfig
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.StringTool
import kotlinx.android.synthetic.main.layout_loading_dailog.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/6/12 19:07
+--------------+---------------------------------
+ projectName  +   wopaipai
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.view.dialog
+--------------+---------------------------------
+ description  +   自定義Dialog：加载进度的显示框
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class BaseLoadingDialog(context: Context, themeResId: Int) : Dialog(context, themeResId),
    DialogInterface {
    private val TAG = BaseLoadingDialog::class.java.simpleName

    //define the second constructor
    constructor(context: Context) : this(context,  R.style.dialog_loading)

    // define a constructor that no params
    constructor() : this(BaseApplication.context)

    private var cancelAble: Boolean = false

    private var msg: String? = null
    private var hyperspaceJumpAnimation: Animation

    init {
        val mDialogView = View.inflate(context, R.layout.layout_loading_dailog, null)
        setContentView(mDialogView)
        cancelAble = false
        mDialogView.setOnClickListener(null)
        if (!StringTool.isEmpty(msg)) {
            tip_text_view.visibility = View.VISIBLE
            tip_text_view.text = msg// 设置加载信息
        } else {
            tip_text_view.visibility = View.GONE
        }
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
            context, R.anim.loading_animation
        )
        cancelAble = true
    }

    fun setProgressBarColor(color: Int) {
        val colorStateList = ColorStateList.valueOf(color)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pb_loading.indeterminateTintList = colorStateList
        }
    }


    fun message(msg: String) {
        if (!StringTool.isEmpty(msg)) {
            tip_text_view.visibility = View.VISIBLE
            tip_text_view.text = msg// 设置加载信息
        } else {
            tip_text_view.visibility = View.GONE
        }
    }

    fun isCancelableOnTouchOutside(cancelable: Boolean): BaseLoadingDialog {
        this.cancelAble = cancelable
        if (BuildConfig.DEBUG)
            this.setCanceledOnTouchOutside(cancelAble)
        else
            this.setCanceledOnTouchOutside(cancelable)
        return this
    }

    fun isCancelable(cancelable: Boolean): BaseLoadingDialog {
        this.cancelAble = cancelable
        if (BuildConfig.DEBUG)
            this.setCancelable(cancelAble)
        else
            this.setCancelable(cancelable)
        return this
    }


    override fun show() {
        if (isShowing) {
            return
        }
        super.show()
    }

    override fun dismiss() {
        if (isShowing) {
            try {
                super.dismiss()
            } catch (e: Exception) {
                LogTool.d(TAG, e.message)
            }

        }
    }

}