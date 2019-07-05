package cn.catherine.token.tool.json

import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.tool.StringTool
import org.json.JSONException
import org.json.JSONObject

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 15:50
+--------------+---------------------------------
+ projectName  +   wopaipai
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.tool
+--------------+---------------------------------
+ description  +   工具類：JSON 数据判断
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object JsonTool {
    private val TAG = JsonTool::class.java.simpleName

    fun getString(resource: String, key: String): String {
        return getString(resource, key, MessageConstants.Empty)
    }

    fun getString(resource: String, key: String, value: String): String {
        return when {
            StringTool.isEmpty(resource) -> value
            StringTool.isEmpty(key) -> value
            else -> {
                var jsonObject: JSONObject? = null

                try {
                    jsonObject = JSONObject(resource)
                    if (!jsonObject.has(key)) value else jsonObject.getString(key)
                } catch (var5: JSONException) {
                    value
                }

            }
        }
    }


}