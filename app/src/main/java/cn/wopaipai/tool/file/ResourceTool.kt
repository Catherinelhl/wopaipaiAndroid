package cn.wopaipai.tool.file

import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 17:17
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.tool.file
+--------------+---------------------------------
+ description  +  工具类：资源管理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object ResourceTool {

    /**
     * 根据文件名字获取assets下面的json文件
     *
     * @param fileName
     * @return
     */
    fun getJsonFromAssets(fileName: String): String {

        val stringBuilder = StringBuilder()
        try {
            val assetManager = BaseApplication.context.assets
            val bf = BufferedReader(
                InputStreamReader(
                    assetManager.open(fileName)
                )
            )
            var line: String?
            do {
                line = bf.readLine()
                if (line == null)
                    break
                stringBuilder.append(line)
            } while (true)
        } catch (e: IOException) {
            BaseException.print(e)
        }

        return stringBuilder.toString()
    }
     fun getString(id:Int) :String{
        return   BaseApplication.context.resources.getString(id)
    }
}