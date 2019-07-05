package cn.wopaipai.tool

import cn.wopaipai.bean.AuctionRecordDivideBean
import cn.wopaipai.bean.AuctionRecordOfferBean
import cn.wopaipai.tool.decimal.DecimalTool
import java.util.ArrayList


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
+ description  +   工具類：List內容管理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object ListTool {
    //判断当前的list是否为空
    @JvmStatic
    fun <T> isEmpty(list: List<T>?): Boolean {
        if (list == null || list.isEmpty()) {
            return true
        }
        return false

    }
    @JvmStatic
    fun <T> noEmpty(list: List<T>?): Boolean {
        return !isEmpty(list)

    }

    /**
     * 获取最新的记录ID
     */
    @JvmStatic
    fun <T> getLatestRecordId(auctionRecordBeans: ArrayList<T>?): Int {
        var recordId = 0
        var recordIdTemp = 0
        auctionRecordBeans?.let {
            for (i in it.indices) {
                var auctionRecordBean = it[i]
                when (auctionRecordBean) {
                    is AuctionRecordOfferBean -> {
                        recordIdTemp = auctionRecordBean.offerRecordId
                    }
                    is AuctionRecordDivideBean -> {
                        recordIdTemp = auctionRecordBean.divideRecordId

                    }
                }
                if (recordIdTemp > recordId) {
                    recordId = recordIdTemp
                }
            }
        }
        return recordId

    }

    fun <T> getRecordComparator(): Comparator<T> {
        // 这里排序规则：按出价记录/分成记录的ID从高到低排
        return Comparator<T> { o1, o2 ->
            return@Comparator when (o1) {
                is AuctionRecordOfferBean -> {
                    ((o2 as AuctionRecordOfferBean).currentPrice - o1.currentPrice).toInt()
                }
                is AuctionRecordDivideBean -> {
                    DateFormatTool.getCountDownTime(
                        (o2 as AuctionRecordDivideBean).dateTime, o1.dateTime
                    ).toInt()
                }
                else -> {
                    -1
                }
            }

        }
    }

}