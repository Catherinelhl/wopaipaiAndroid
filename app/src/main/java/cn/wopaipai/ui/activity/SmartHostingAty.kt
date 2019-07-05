package cn.wopaipai.ui.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.EditText
import cn.wopaipai.R
import cn.wopaipai.adapter.SmartHostAdapter
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseAty
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.manager.intentToActivity
import cn.wopaipai.tool.LogTool
import cn.wopaipai.ui.my.SmartHostDetailsActivity
import cn.wopaipai.view.dialog.IntelligentHostingOpenDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.aty_smart_hosting.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-24 17:02
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +   智能托管
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class SmartHostingAty : BaseAty() {

    private var hostList: ArrayList<String> = arrayListOf()
    override fun getArgs(bundle: Bundle?) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_smart_hosting

    override fun initViews() {

        val smartHostAdapter = SmartHostAdapter(R.layout.recycle_smart_host, hostList)
        smartHostAdapter.setOnItemSelectListener(object : OnItemSelectListener {
            override fun <T> onItemSelect(type: T, from: String) {
                val editDialog = IntelligentHostingOpenDialog(this@SmartHostingAty)
                //设置背景圆弧
                editDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                editDialog.setCanceledOnTouchOutside(false)
                editDialog.create()
                editDialog.show()
                editDialog.setSure(object : IntelligentHostingOpenDialog.SureInterface {
                    override fun getSure(passWord: String) {
                        LogTool.d(tag, passWord)
                    }

                    override fun max(etNum: EditText) {

                    }
                })
            }
        })
        rcl_smart_host.layoutManager = LinearLayoutManager(this)
        rcl_smart_host.adapter = smartHostAdapter
        smartHostAdapter.setOnItemClickListener { adapter, view, position ->
            intentToActivity(SmartHostDetailsActivity::class.java)
        }
    }

    override fun initData() {
    }

    override fun initListener() {
    }
}