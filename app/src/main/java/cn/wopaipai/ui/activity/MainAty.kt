package cn.wopaipai.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.View
import android.widget.TextView
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseAty
import cn.wopaipai.base.BaseException
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.event.NetStateChangeEvent
import cn.wopaipai.manager.hideSoftKeyBoardByTouchView
import cn.wopaipai.manager.showToast
import cn.wopaipai.tool.ActivityTool
import cn.wopaipai.tool.ListTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.ui.fragment.AccountFrg
import cn.wopaipai.ui.fragment.AuctionFrg
import cn.wopaipai.ui.fragment.MainFrg
import com.jakewharton.rxbinding2.view.RxView
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.aty_main.*
import kotlinx.android.synthetic.main.include_header.*
import java.util.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 14:37
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +  Activity：首页
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class MainAty : BaseAty() {
    //底部tab的标题集合         //初始化底部栏文本数据
    private val tabTitles by lazy {
        arrayOf(
            getResString(R.string.main),
            getResString(R.string.auction),
            getResString(R.string.account)
        )
    }
    //底部tab的图标集合
    private val tabDrawables by lazy {
        arrayOf(
            R.mipmap.icon_home,
            R.mipmap.icon_auction,
            R.mipmap.icon_account
        )
    }
    //底部tab的选中图标集合
    private val tabFocusDrawables by lazy {
        arrayOf(R.mipmap.icon_home_f, R.mipmap.icon_auction_f, R.mipmap.icon_account_f)
    }
    //底部tab的数量
    private val tabBottomTitleCount: Int by lazy { tabTitles.size }


    //声明当前需要和底部栏搭配的所有fragment
    private lateinit var fragments: ArrayList<Fragment>
    //得到当前显示的Fragment
    private lateinit var currentFragment: Fragment


    override fun getArgs(bundle: Bundle?) {

    }

    override fun getLayoutRes(): Int = R.layout.aty_main

    override fun initViews() {
        //將當前的activity加入到管理之中，方便「退出」的時候進行移除操作
        ActivityTool.addActivity(this)
        fragments = ArrayList()
        //默认不显示标题
        rl_header.visibility = View.GONE
        //初始化「首页」页面
        val mainFragment = MainFrg()
        fragments.add(mainFragment)
        //初始化「竞拍」页面
        val auctionFragment = AuctionFrg()
        fragments.add(auctionFragment)
        //初始化「我的」页面
        val myFragment = AccountFrg()
        fragments.add(myFragment)


    }

    /**
     * 根据底部栏当前的位置返回当前位置信息
     *
     * @param position
     * @return
     */
    private fun getTabTitle(position: Int): String {
        return if (position >= tabBottomTitleCount) {
            MessageConstants.Empty
        } else tabTitles[position]
    }

    /**
     * 根据当前底部栏的位置返回当前位置上图标信息
     *
     * @param position
     * @return
     */
    private fun getTabDrawable(position: Int, isSelect: Boolean): Int {
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

    override fun initData() {
        for (i in fragments.indices) {
            val tab = bottom_tab_layout.newTab()
            // method 自定义布局-----
            tab.setCustomView(R.layout.item_bottom_tab)
            val textView = tab.customView!!.findViewById<TextView>(R.id.tv_tab_title)
            //            textView.getPaint().setShader(getShader(textView, false));
            textView.setTextColor(context.resources.getColor(R.color.black30_222222))
            textView.setCompoundDrawablesWithIntrinsicBounds(
                null,
                getDrawableTop(this, i, false),
                null,
                null
            )
            textView.text = getTabTitle(i)
            //自定义布局-----

            bottom_tab_layout.addTab(tab)
            if (i == 0) {
                onTabItemSelected(i)
                tab.customView!!.findViewById<View>(R.id.ll_tab_item).isSelected = true
                textView.setTextColor(context.resources.getColor(R.color.red_F5515F))
                //method 2
                textView.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    getDrawableTop(this, 0, true),
                    null,
                    null
                )
            }
        }
    }

    override fun initListener() {
        ll_main.hideSoftKeyBoardByTouchView(activity)
        bottom_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //自定义:如果是自定义的tabItem，那么就需要调用这句来设置选中状态，虽然没有界面上的变化
                tab.customView!!.findViewById<View>(R.id.ll_tab_item).isSelected = true
                val textView = tab.customView!!.findViewById<TextView>(R.id.tv_tab_title)
                textView.setTextColor(context.resources.getColor(R.color.red_F5515F))
                //method 2：如果是直接就用一个TextView控件来表示了，那么就可以直接用下面这一句来表示
                textView.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    getDrawableTop(this@MainAty, tab.position, true),
                    null,
                    null
                )
                //改变当前中间content信息；Fragment变换
                onTabItemSelected(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                //自定义
                tab.customView!!.findViewById<View>(R.id.ll_tab_item).isSelected = false
                val textView = tab.customView!!.findViewById<TextView>(R.id.tv_tab_title)
                textView.setTextColor(context.resources.getColor(R.color.black30_222222))
                //method 2
                textView.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    getDrawableTop(this@MainAty, tab.position, false),
                    null,
                    null
                )

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                //自定义
                val textView = tab.customView!!.findViewById<TextView>(R.id.tv_tab_title)
                textView.setTextColor(context.resources.getColor(R.color.red_F5515F))
                //method 2
                textView.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    getDrawableTop(this@MainAty, tab.position, true),
                    null,
                    null
                )

            }
        })

        RxView.clicks(ib_right).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                showToast(getResString(R.string.this_feature_not_open_yet))
            }, { BaseException.print(it) })

    }

    /**
     * 根据选中的position来切换选项卡
     *
     * @param position
     */
    private fun onTabItemSelected(position: Int) {
        if (ListTool.noEmpty(fragments) && position < fragments.size) {
            currentFragment = fragments[position]
            supportFragmentManager.beginTransaction()
                .replace(R.id.home_container, currentFragment).commitAllowingStateLoss()
            when (position) {
                0 -> setTitle(false, null, -1)
                1 -> {
                    setTitle(false, null, -1)
                    // 更新当前界面设置
                }
                2 -> setTitle(true, getResString(R.string.account), R.mipmap.icon_email)
            }
        }

    }

    /**
     * 设置标题
     * @param showTitle 是否显示标题
     * @param title 标题内容
     * @param rightIcon 右边图标
     */
    private fun setTitle(showTitle: Boolean, title: String?, rightIcon: Int) {
        if (rl_header != null && tv_title != null) {
            rl_header.visibility = if (showTitle) View.VISIBLE else View.GONE
            title ?: return
            tv_title.text = title
        }
        //TODO   暂时隐藏
//        if (ib_right != null) {
//            if (rightIcon != -1) {
//                ib_right.visibility = View.VISIBLE
//                ib_right.setImageResource(rightIcon)
//            }
//        }
    }

    override fun onBackPressed() {
        if (multipleClickToDo(2)) {
            super.onBackPressed()
        } else {
            activity.showToast(getResString(R.string.double_click_for_exit))
        }
    }


    /**
     * 网络变化监听
     */
    @Subscribe
    fun netStateChange(netStateChangeEvent: NetStateChangeEvent?) {
        if (netStateChangeEvent != null) {
            if (!netStateChangeEvent.isConnect) {
                activity.showToast(getResString(R.string.network_not_reachable))
            }
            BaseApplication.realNet = netStateChangeEvent.isConnect

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.RequestCode.AUCTION_DETAIL_REQUEST_CODE -> {
                //刷新当前的list
                if (ListTool.noEmpty(fragments) && fragments.size > 2) {
                    (fragments[1] as AuctionFrg).refreshView()
                }
            }
            else -> {
                LogTool.d(tag, "onActivityResult else")

            }

        }
    }
}