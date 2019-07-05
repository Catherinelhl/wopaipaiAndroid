package cn.catherine.token.tool.json

import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.StringTool
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import okhttp3.MediaType
import okhttp3.RequestBody
import java.lang.reflect.Type


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
+ description  +   工具類：Gson格式管理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object GsonTool {
    /*将对象转换为String*/
    fun string(o: Any?): String {
        o ?: return MessageConstants.Empty
        val gson = getGson()
        return gson.toJson(o)
    }

    /*通过传入的key得到相应的数组数据*/
    fun <T> getListByKey(resource: String, key: String, type: Type): Any? {
        val gson = getGson()
        val value = JsonTool.getString(resource, key)
        return if (!StringTool.isEmpty(value) && !StringTool.equals(
                "[]",
                value.replace(" ", "")
            )
        ) gson.fromJson<Any>(
            value,
            type
        ) else null
    }

    /*通过传入的key得到相应的数据*/
    fun <T> getBeanByKey(resource: String, key: String, type: Type): Any? {
        val gson = getGson()
        val value = JsonTool.getString(resource, key)
        return if (StringTool.isEmpty(value)) null else gson.fromJson<Any>(value, type)
    }

    /*解析数据是object的情况*/
    @Throws(JsonSyntaxException::class)
    fun <T> convert(str: String, cls: Class<T>): T {
        val gson = getGson()
        return gson.fromJson(str, cls)
    }

    @Throws(JsonSyntaxException::class)
    fun <T> convert(str: String, type: Type): T? {
        val gson = getGson()
        return gson.fromJson<T>(str, type)
    }


    fun getGson(): Gson {
        return GsonBuilder()
            .disableHtmlEscaping()
            .create()
    }


    fun <T> logInfo(TAG: String, flag: String, info: T) {
        LogTool.d(TAG, flag, GsonTool.string(info))
    }

    fun <T> logInfo(TAG: String, stuff: String, flag: String, info: T) {
        LogTool.d(TAG, stuff, flag, GsonTool.string(info))
    }

    fun <T> beanToRequestBody(jsonBean: T): RequestBody {
        if (jsonBean == null) {
            throw NullPointerException("beanToRequestBody str is null")
        }
        return RequestBody.create(MediaType.parse("application/json"), GsonTool.string(jsonBean))
//        return RequestBody.create(
//            MediaType.parse("application/json-patch+json"),
//            jsonBean.toString()
//        )
    }
}