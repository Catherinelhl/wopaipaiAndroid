package cn.wopaipai.tool.file

import cn.wopaipai.constant.Constants
import cn.wopaipai.tool.StringTool
import java.io.File

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
+ description  +   工具类：管理文件路径
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object FilePathTool {

    /**
     * 根据当前语言环境获取相对应的文件
     *
     * @param getCurrentLanguage
     * @return
     */
    fun getCountryCodeFilePath(getCurrentLanguage: String): String {
        return if (StringTool.equals(getCurrentLanguage, Constants.ValueMaps.ZH_CN)) {
            Constants.FilePath.COUNTRY_CODE + File.separator + Constants.FilePath.ZH_CN_COUNTRY_CODE
        } else if (StringTool.equals(getCurrentLanguage, Constants.ValueMaps.ZH_TW)) {
            Constants.FilePath.COUNTRY_CODE + File.separator + Constants.FilePath.ZH_TW_COUNTRY_CODE
        } else {
            Constants.FilePath.COUNTRY_CODE + File.separator + Constants.FilePath.EN_US_COUNTRY_CODE
        }
    }

    fun getJsonFileContent(file: String): String {
        return "json" + File.separator + file
    }
}