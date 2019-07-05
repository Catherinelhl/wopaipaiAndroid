package cn.wopaipai.manager

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import cn.wopaipai.R
import cn.wopaipai.constant.Constants
import cn.wopaipai.ui.activity.LoginAty

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 15:49
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.listener
+--------------+---------------------------------
+ description  +   APP管理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/
/**
 * 跳转登录页面
 */
fun Activity.intentToLogin() {
    //如果當前是phone，那麼就跳轉到手機的登錄頁面，否則跳轉到TV的登錄頁面
    intentToActivity(LoginAty::class.java, true)
}

/**
 * 从当前页面跳转到另一个页面
 *
 * @param classTo
 */
fun Activity?.intentToActivity(classTo: Class<*>) {
    intentToActivity(null, classTo)
}

/**
 * @param finishFrom 是否关闭上一个activity，默认是不关闭 false
 */
fun Activity?.intentToActivity(classTo: Class<*>, finishFrom: Boolean) {
    intentToActivity(null, classTo, finishFrom)
}

/**
 * @param bundle 存储当前页面需要传递的数据
 */
fun Activity?.intentToActivity(bundle: Bundle?, classTo: Class<*>) {
    intentToActivity(bundle, classTo, false)
}

/**
 * 頁面跳轉
 *
 * @param bundle
 * @param classTo
 * @param finishFrom 是否
 */
fun Activity?.intentToActivity(bundle: Bundle?, classTo: Class<*>, finishFrom: Boolean) {
    this.intentToActivity(bundle, classTo, finishFrom, false)
}

/**
 * 頁面跳轉
 *
 * @param bundle
 * @param classTo
 * @param finishFrom
 * @param isClearTask 是否清空任務
 */
fun Activity?.intentToActivity(
    bundle: Bundle?,
    classTo: Class<*>,
    finishFrom: Boolean,
    isClearTask: Boolean
) {
    this?.let {
        val intent = Intent()
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        if (isClearTask) {
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        intent.setClass(it, classTo)
        it.startActivity(intent)
        if (finishFrom) {
            it.finish()
        }
        it.overridePendingTransition(R.anim.slide_in_alpha, R.anim.slide_exit_alpha)
    }

}

/**
 *Activity 带返回数据的跳转
 */
fun Activity?.intentActivityForResult(classTo: Class<*>, requestCode: Int, intent: Intent?) {
    this?.let {
        var intent = intent
        if (intent == null) {
            intent = Intent()
        }
        intent.setClass(it, classTo)
        it.startActivityForResult(intent, requestCode)
    }

}

/**
 * Activity  返回相关结果
 * */

fun Activity?.returnResult(isBack: Boolean) {
    this?.let {
        val intent = Intent()
        val bundle = Bundle()
        bundle.putBoolean(Constants.KeyMaps.From, isBack)
        intent.putExtras(bundle)
        it.setResult(RESULT_OK, intent)
        it.finish()
    }

}

/**
 * 通过传入的View 来隐藏当前的软键盘
 * @param activity 当前的界面
 */
fun View?.hideSoftKeyBoardByTouchView(activity: Activity?) {
    this?.let {
        this.setOnTouchListener { v, event ->
            //hideSoftKeyboard
            activity.hideSoftKeyboard()
            false
        }
    }
}

/*隱藏當前軟鍵盤*/
fun Activity?.hideSoftKeyboard() {
    /*键盘输入管理者*/
    var inputMethodManager: InputMethodManager? = null
    this?.let { activity ->
        inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.let {
            if (activity.currentFocus != null) {
                it.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

}

fun Activity?.showToast(toastInfo: String) {
    this ?: return
    runOnUiThread {
        val toast = Toast.makeText(this, "", Toast.LENGTH_LONG)
        /*解决小米手机toast自带包名的问题*/
        toast.setText(toastInfo)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}

