package cn.wopaipai.tool

import android.content.Context
import android.content.SharedPreferences
import cn.wopaipai.constant.Constants
import cn.wopaipai.base.BaseApplication

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
+ description  +   工具類：用于存储当前APP需要用的值於SharedPreferences
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class PreferenceTool (context: Context){
    private val TAG = PreferenceTool::class.java.simpleName

    companion object {
        private lateinit var sp: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        //volatile https://www.cnblogs.com/dolphin0520/p/3920373.html
        @Volatile
        private var instance: PreferenceTool? = null

        @Synchronized
        fun getInstance(): PreferenceTool {
            if (instance == null) {
                instance = PreferenceTool(BaseApplication.context)
            }
            return instance!!
        }

        @Synchronized
        fun getInstance(context: Context): PreferenceTool {
            if (instance == null) {
                instance = PreferenceTool(context)
            }
            return instance!!
        }
    }

    init {
        sp = context.getSharedPreferences(Constants.Preference.SPNAME(), 0)
        editor = sp.edit()
    }
    fun getString(key: String): String? {
        return this.getString(key, "")
    }

    fun getString(key: String, defValue: String): String? {
        return sp.getString(key, defValue)
    }

    fun getInt(key: String): Int {
        return this.getInt(key, 0)
    }

    fun getInt(key: String, defValue: Int): Int {
        return sp.getInt(key, defValue)
    }

    fun getBoolean(key: String): Boolean {
        return this.getBoolean(key, false)
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sp.getBoolean(key, defValue)
    }

    fun getLong(key: String): Long? {
        return this.getLong(key, 0L)
    }

    fun getLong(key: String, defValue: Long?): Long? {
        return sp.getLong(key, defValue!!)
    }

    fun getFloat(key: String): Float? {
        return this.getFloat(key, 0.0f)
    }

    fun getFloat(key: String, defValue: Float?): Float? {
        return sp.getFloat(key, defValue!!)
    }

    fun saveString(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun saveInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }

    fun saveFloat(key: String, value: Float) {
        editor.putFloat(key, value)
        editor.commit()
    }

    fun saveLong(key: String, value: Long) {
        editor.putLong(key, value)
        editor.commit()
    }

    fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun clear() {
        editor.clear()
        editor.commit()
    }

    fun clear(key: String) {
        editor.remove(key)
        editor.commit()
    }
}