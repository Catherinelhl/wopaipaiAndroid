package cn.wopaipai.adapter

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-14 18:33
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.adapter
+--------------+---------------------------------
+ description  +  數據適配器：TabLayout  数据适配
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class TabViewAdapter(private val view:View, private val allTitles: List<String>) :
    PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = view
        (container as ViewPager).addView(view, 0)
        return view
    }

    override fun getCount(): Int {
        return 1
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` === view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    //设置tabLayout标题
    override fun getPageTitle(position: Int): CharSequence? {
        return allTitles[position]
    }
}