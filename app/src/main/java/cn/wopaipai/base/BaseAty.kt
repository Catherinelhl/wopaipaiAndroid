package cn.wopaipai.base

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.Unbinder
import cn.catherine.token.tool.language.LanguageTool
import cn.catherine.token.view.dialog.BaseLoadingDialog
import cn.wopaipai.R
import cn.wopaipai.constant.Constants
import cn.wopaipai.gson.ResponseJson
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.manager.intentActivityForResult
import cn.wopaipai.tool.OttoTool
import cn.wopaipai.ui.activity.CountryCodeAty

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
+ description  +  Activity 基类
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

abstract class BaseAty : AppCompatActivity(), BaseContract.View {
    private var unbinder: Unbinder? = null

    lateinit var activity: Activity
    val tag: String by lazy { activity::class.java.simpleName }
    val context: Context by lazy { applicationContext }
    private val loadingDialog: BaseLoadingDialog by lazy { BaseLoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs(intent.extras)
        setContentView(getLayoutRes())
        activity = this
        unbinder = ButterKnife.bind(this)
        //注册OTTO事件
        OttoTool.register(this)
        initViews()
        initData()
        initListener()

    }

    abstract fun getArgs(bundle: Bundle?)
    abstract fun getLayoutRes(): Int
    abstract fun initViews()
    abstract fun initData()
    abstract fun initListener()

    /**
     * 得到当前的屏幕方向是否是垂直
     */
    fun getScreenDirectionIsPortrait(): Boolean =
        this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT


    fun showToast(toastInfo: String) {
        runOnUiThread {
            val toast = Toast.makeText(activity, "", Toast.LENGTH_LONG)
            /*解决小米手机toast自带包名的问题*/
            toast.setText(toastInfo)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    fun showShortToast(toastInfo: String) {
        runOnUiThread {
            val toast = Toast.makeText(activity, "", Toast.LENGTH_SHORT)
            /*解决小米手机toast自带包名的问题*/
            toast.setText(toastInfo)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    override fun showLoading() {
        hideLoading()
        loadingDialog.setProgressBarColor(resources.getColor(R.color.button_color))
        loadingDialog.show()
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
        loadingDialog.cancel()

    }

    override fun httpExceptionStatus(responseJson: ResponseJson): Boolean = false

    override fun noNetWork() {

    }

    override fun connectFailure() {

    }

    /**
     * 根据传入的参数来捕捉用户点击的此时
     *
     * @param times 次数
     * @return
     */
    private var clickTimes = 0
    /*存儲當前點擊返回按鍵的時間，用於提示連續點擊兩次才能退出*/
    private var lastClickBackTime = 0L

    fun multipleClickToDo(times: Int): Boolean {
        if (System.currentTimeMillis() - lastClickBackTime > Constants.Time.sleep2000) {
            clickTimes = 1
            lastClickBackTime = System.currentTimeMillis()
            return false
        } else {
            clickTimes++
            if (clickTimes == times) {
                lastClickBackTime = 0
                return true
            } else if (clickTimes < times) {
                lastClickBackTime = System.currentTimeMillis()
                return false
            }
            clickTimes = 0
            return false
        }
    }

    /**
     * 为弹出的popWindow设置背景透明度
     *
     * @param bgAlpha
     */
    fun setBackgroundAlpha(bgAlpha: Float) {
        val lp = window
            .attributes
        lp.alpha = bgAlpha
        window.attributes = lp
    }

    override fun onDestroy() {
        super.onDestroy()
        if (unbinder != null) {
            unbinder!!.unbind()
        }
    }

    val baseOnItemSelectListener = object : OnItemSelectListener {
        override fun <T : Any?> onItemSelect(type: T, from: String?) {
            from?.let {
                when (from) {
                    Constants.ActionFrom.SELECT_COUNTRY_CODE -> {
                        intentToCountryCode()//跳转到城市区号选择界面
                    }
                }
            }
        }
    }

    /**
     * 跳转到选择区号的页面
     */
    private fun intentToCountryCode() {
        this.intentActivityForResult(
            CountryCodeAty::class.java,
            Constants.RequestCode.COUNTRY_CODE_REQUEST_CODE,
            null
        )
    }

    fun getResString(resId: Int): String {
        return context.resources.getString(resId)
    }

    override fun attachBaseContext(base: Context) {
        //保存系统选择语言
        //        LanguageTool.saveSystemCurrentLanguage(base);
        super.attachBaseContext(LanguageTool.setLocal(base))
    }

}