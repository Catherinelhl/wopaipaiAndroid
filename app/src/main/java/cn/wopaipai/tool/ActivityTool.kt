package cn.wopaipai.tool

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import java.util.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 15:55
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.tool
+--------------+---------------------------------
+ description  +   工具類：用來管理當前啟動的Activity
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object ActivityTool {
    private val TAG = ActivityTool::class.java.simpleName

    private val activityMap = HashMap<String, Activity>()//用來存儲加入的activity

    /**
     * 保存指定key值的activity（activity启动时调用）
     *
     * @param activity
     */
    fun addActivity(activity: Activity) {
        val key = System.currentTimeMillis().toString()
        if (activityMap[key] == null) {
            activityMap[key] = activity
        }
    }

    /**
     * 移除指定key值的activity （activity关闭时调用）
     */
    fun removeActivity(activity: Activity?) {
        val key = System.currentTimeMillis().toString()
        if (activity != null) {
            if (activity.isDestroyed || activity.isFinishing) {
                activityMap.remove(key)
                return
            }
            activity.finish()
            activityMap.remove(key)
        }
    }

    /**
     * 移除所有的Activity
     */
    fun removeAllActivity() {
        for ((_, activity) in activityMap) {
            finishAty(activity)
        }
    }

    private fun finishAty(aty: Activity?) {
        if (aty != null && !aty.isFinishing) {
            aty.finish()
        }
    }

    fun exit() {
        //        MobclickAgent.onKillProcess(context());
        killProcess()
        System.exit(0)
    }

    fun killProcess() {
        removeAllActivity()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    /**
     * 检测某Activity是否在当前Task的栈顶
     * appointClassName：指定类名称
     */
    fun isTopActivity(appointClassName: String, context: Context): Boolean {
        val manager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val runningTaskInfo = manager.getRunningTasks(1)
        var topClassName: String? = null
        if (null != runningTaskInfo) {
            topClassName = runningTaskInfo[0].topActivity.shortClassName.toString()
        }
        if (StringTool.isEmpty(topClassName)) {
            return false
        }
        LogTool.e(
            TAG,
            topClassName + "类存在于栈顶；指定类：" + appointClassName + "   返回Boolean值：" + topClassName!!.contains(
                appointClassName
            )
        )
        return topClassName.contains(appointClassName)
    }
}