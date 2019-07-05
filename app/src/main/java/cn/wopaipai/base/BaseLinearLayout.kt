package cn.wopaipai.base

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import cn.catherine.token.view.dialog.BaseLoadingDialog
import cn.wopaipai.R
import cn.wopaipai.gson.ResponseJson
import cn.wopaipai.tool.OttoTool

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-14 18:38
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.base
+--------------+---------------------------------
+ description  +  继承LinearLayout的View的基类
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

abstract class BaseLinearLayout(context: Context) :
    LinearLayout(context), BaseContract.View {

    private val TAG = BaseLinearLayout::class.java.simpleName
    private val view: View
    private var loadingDialog: BaseLoadingDialog? = null

    init {
        view = LayoutInflater.from(context).inflate(setContentView(), this, true)
        initView()
        initListener()
        //注册otto事件
        OttoTool.register(this)

    }

    protected abstract fun setContentView(): Int

    protected abstract fun initView()

    protected abstract fun initListener()

    override fun showLoading() {
        if (context == null) {
            return
        }
        hideLoading()
        loadingDialog = BaseLoadingDialog(context)
        loadingDialog!!.setProgressBarColor(resources.getColor(R.color.button_color))
        loadingDialog!!.show()
    }

    override fun hideLoading() {
        if (context == null) {
            return
        }
        if (loadingDialog != null) {
            loadingDialog!!.dismiss()
            loadingDialog!!.cancel()
            loadingDialog = null

        }
    }

    override fun noNetWork() {
        Toast.makeText(
            context,
            resources.getString(R.string.network_not_reachable),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun httpExceptionStatus(responseJson: ResponseJson): Boolean {
        if (responseJson == null) {
            return false
        }
//        val code = responseJson.code
//        //判断是否是Token过期，弹出提示重新登录，然后跳转界面
//        if (code == MessageConstants.CODE_2019
//            || code == MessageConstants.CODE_2016
//            || code == MessageConstants.CODE_2018
//        ) {
//            //    {"success":false,"code":2019,"message":"AccessToken expire."}
//            OttoTool.getInstance().post(LogoutEvent())
//            return true
//        } else if (code == MessageConstants.CODE_2005) {
//            val logoutEvent = LogoutEvent()
//            logoutEvent.info = context!!.getString(R.string.please_register_email_first)
//            OttoTool.post(logoutEvent)
//            return true
//        }
        return false
    }

    fun showToast(toastInfo: String) {
        val toast = Toast.makeText(context, "", Toast.LENGTH_LONG)
        /*解决小米手机toast自带包名的问题*/
        toast.setText(toastInfo)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }


    override fun connectFailure() {
    }

}