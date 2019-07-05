package cn.wopaipai.manager

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import java.util.*


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
+ description  +   回調監聽：監聽軟鍵盤彈起管理當前界面做出相對應改動
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class SoftKeyBroadManager : ViewTreeObserver.OnGlobalLayoutListener {
    private val TAG = SoftKeyBroadManager::class.java.simpleName

    interface SoftKeyboardStateListener {
        fun onSoftKeyboardOpened(keyboardHeightInPx: Int, bottom: Int)

        fun onSoftKeyboardClosed()
    }

    private val listeners = LinkedList<SoftKeyboardStateListener>()
    private lateinit var activityRootView: View
    private lateinit var scrollView: View
    private var lastSoftKeyboardHeightInPx: Int = 0
    private var isSoftKeyboardOpened: Boolean = false

    constructor(activityRootView: View, scrollView: View) : this(activityRootView, scrollView, false)

    constructor(activityRootView: View, scrollView: View, isSoftKeyboardOpened: Boolean) {
        this.activityRootView = activityRootView
        this.scrollView = scrollView
        this.isSoftKeyboardOpened = isSoftKeyboardOpened
        this.activityRootView.viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        val r = Rect()
        //r will be populated with the coordinates of your view that area still visible.
        activityRootView.getWindowVisibleDisplayFrame(r)
        val screenHeight = activityRootView.rootView.height
        val heightDiff = screenHeight - (r.bottom - r.top)
        val isKeyboardShowing = heightDiff > screenHeight / 3
        if (!isSoftKeyboardOpened && isKeyboardShowing) { // if more than 100 pixels， its probably a keyboard...
            isSoftKeyboardOpened = true
            val factHeight = scrollView.y + scrollView.height
            //            LogTool.d(TAG, factHeight);
            //            LogTool.d(TAG, heightDiff);
            //            LogTool.d(TAG, screenHeight - factHeight);
            if (screenHeight - factHeight < heightDiff) {
                val location = IntArray(2)
                //获取scrollToView在窗体的坐标
                scrollView.getLocationInWindow(location)
                //计算root滚动高度，使scrollToView在可见区域
                val scrollHeight = location[1] + scrollView.height - r.bottom
                activityRootView.scrollTo(0, scrollHeight)
            }

            notifyOnSoftKeyboardOpened(heightDiff, r.bottom)
        } else if (isSoftKeyboardOpened && !isKeyboardShowing) {
            isSoftKeyboardOpened = false
            activityRootView.scrollTo(0, 0)
            notifyOnSoftKeyboardClosed()
        }
    }

    fun setIsSoftKeyboardOpened(isSoftKeyboardOpened: Boolean) {
        this.isSoftKeyboardOpened = isSoftKeyboardOpened
    }

    fun isSoftKeyboardOpened(): Boolean {
        return isSoftKeyboardOpened
    }

    /**
     * Default value is zero (0)
     *
     * @return last saved keyboard height in px
     */
    fun getLastSoftKeyboardHeightInPx(): Int {
        return lastSoftKeyboardHeightInPx
    }

    fun addSoftKeyboardStateListener(listener: SoftKeyboardStateListener) {
        listeners.add(listener)
    }

    fun removeSoftKeyboardStateListener(listener: SoftKeyboardStateListener) {
        listeners.remove(listener)
    }

    private fun notifyOnSoftKeyboardOpened(keyboardHeightInPx: Int, bottom: Int) {
        this.lastSoftKeyboardHeightInPx = keyboardHeightInPx

        for (listener in listeners) {
            listener.onSoftKeyboardOpened(keyboardHeightInPx, bottom)
        }
    }

    private fun notifyOnSoftKeyboardClosed() {
        for (listener in listeners) {
            listener.onSoftKeyboardClosed()
        }
    }
}