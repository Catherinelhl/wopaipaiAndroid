package cn.wopaipai.manager

import android.content.Context
import android.graphics.drawable.Drawable
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import java.util.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-12 20:53
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.manager
+--------------+---------------------------------
+ description  +   数据生成
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object DataGenerationManager {
    //底部tab的标题集合
    private val tabTitles = ArrayList<String>()
    //底部tab的图标集合
    private val tabDrawables = ArrayList<Int>()
    //底部tab的选中图标集合
    private val tabFocusDrawables = ArrayList<Int>()
    //底部tab的数量
    private var tabBottomTitleCount: Int = 0

    init {
        //初始化底部栏文本数据
        tabTitles.add(BaseApplication.context.getString(R.string.main))
        tabTitles.add(BaseApplication.context.getString(R.string.auction))
        tabTitles.add(BaseApplication.context.getString(R.string.account))
        tabBottomTitleCount = tabTitles.size


        //初始化底部栏图标数据
        tabDrawables.add(R.mipmap.icon_home)
        tabDrawables.add(R.mipmap.icon_auction)
        tabDrawables.add(R.mipmap.icon_account)
        tabFocusDrawables.add(R.mipmap.icon_home_f)
        tabFocusDrawables.add(R.mipmap.icon_auction_f)
        tabFocusDrawables.add(R.mipmap.icon_account_f)
    }


    /**
     * 根据底部栏当前的位置返回当前位置信息
     *
     * @param position
     * @return
     */
    fun getTabTitle(position: Int): String {

        return if (position >= tabBottomTitleCount) {
            ""
        } else tabTitles[position]
    }

    /**
     * 根据当前底部栏的位置返回当前位置上图标信息
     *
     * @param position
     * @return
     */
    fun getTabDrawable(position: Int, isSelect: Boolean): Int {
        if (position >= tabBottomTitleCount) {
            return 0
        }
        return if (isSelect) tabFocusDrawables[position] else tabDrawables[position]
    }

    /**
     * 根据位置下标，是否是选中的状态
     *
     * @param i
     * @param isSelect
     * @return
     */
    fun getDrawableTop(context: Context, i: Int, isSelect: Boolean): Drawable {
        return context.resources.getDrawable(getTabDrawable(i, isSelect))

    }

}