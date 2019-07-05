package cn.wopaipai.base

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import cn.wopaipai.BaseDialog
import cn.wopaipai.R
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.gson.ResponseJson
import cn.wopaipai.tool.OttoTool

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-12 20:00
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.fragment
+--------------+---------------------------------
+ description  +   Fragment的基类
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

abstract class BaseFrg : Fragment(), BaseContract.View {
    private val TAG: String  by lazy { BaseFrg::class.java.simpleName }
    var activity: Activity? = null
    private var rootView: View? = null
    private var unbinder: Unbinder? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        OttoTool.register(this)
        unbinder = ButterKnife.bind(this, view)
        activity = getActivity()

        getArgs(savedInstanceState)
        initViews(view)
        initListener()
    }


    /**
     * 得到当前的屏幕方向是否是垂直
     */
    fun getScreenDirectionIsPortrait(): Boolean =
        this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    abstract fun getLayoutRes(): Int
    abstract fun initViews(view: View)
    abstract fun getArgs(bundle: Bundle?)
    abstract fun initListener()

    private fun checkActivityState(): Boolean {
        return (activity != null
                && !activity!!.isFinishing
                && isAdded)
    }

    fun showBaseDialog(message: String, confirmClickListener: BaseDialog.ConfirmClickListener) {
        activity?.let {
            BaseDialog().showDialog(
                it, resources.getString(R.string.warning),
                resources.getString(R.string.cancel),
                resources.getString(R.string.confirm),
                message, confirmClickListener
            )
        }
    }

    override fun showLoading() {
        hideLoading()
        if (activity != null) {
            (activity as BaseAty).showLoading()
        }
    }

    override fun hideLoading() {
        if (activity != null) {
            (activity as BaseAty).hideLoading()
        }
    }

    override fun httpExceptionStatus(responseJson: ResponseJson): Boolean {
        return false
    }

    override fun connectFailure() {
    }

    override fun noNetWork() {
    }


    fun getResString(resId: Int): String {
        context?.let {
            return it.resources.getString(resId)

        }
        return MessageConstants.Empty
    }

}