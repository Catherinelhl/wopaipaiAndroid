package cn.wopaipai.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import cn.wopaipai.R
import cn.wopaipai.bean.TypeSwitchingBean
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.listener.OnLanguageItemSelectListener
import cn.wopaipai.tool.ListTool
import cn.wopaipai.tool.StringTool

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-12 16:31
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.adapter
+--------------+---------------------------------
+ description  +  「切換語言」、「TV版切換幣種」數據填充適配器
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class TypeSwitchingAdapter(
    val context: Context,
    private val typeSwitchingBeans: List<TypeSwitchingBean>
) : RecyclerView.Adapter<TypeSwitchingAdapter.viewHolder>() {

    private var settingItemSelectListener: OnLanguageItemSelectListener? = null

    fun setSettingItemSelectListener(settingItemSelectListener: OnLanguageItemSelectListener) {
        this.settingItemSelectListener = settingItemSelectListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): viewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_language_switch, viewGroup, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: viewHolder, i: Int) {
        typeSwitchingBeans ?: return
        val typeSwitchingBean: TypeSwitchingBean? = typeSwitchingBeans[i]
        typeSwitchingBean ?: return
//如果當前是手機，那麼顯示紅色的勾選
        viewHolder.btnChoose.background =
            context.resources.getDrawable(R.mipmap.icon_choose)
        if (i == typeSwitchingBeans.size - 1) {
            viewHolder.vLine.visibility = View.INVISIBLE
        } else {
            viewHolder.vLine.visibility = View.VISIBLE
        }
        val isChoose = typeSwitchingBean.isChoose
        val language = typeSwitchingBean.language
        viewHolder.tvLanguage.text = language
        viewHolder.btnChoose.visibility = if (isChoose) View.VISIBLE else View.INVISIBLE
        viewHolder.btnChoose.setOnClickListener { v ->
            if (!isChoose) {
                viewHolder.btnChoose.visibility = View.VISIBLE
                updateData(typeSwitchingBean)
                settingItemSelectListener?.onItemSelect(typeSwitchingBean, "")
            } else {
                settingItemSelectListener?.changeItem(false)
            }
        }
        viewHolder.tvLanguage.setOnClickListener { v ->
            if (!isChoose) {
                viewHolder.btnChoose.visibility = View.VISIBLE
                updateData(typeSwitchingBean)
                settingItemSelectListener?.onItemSelect(typeSwitchingBean, "")
            } else {
                settingItemSelectListener?.changeItem(false)

            }
        }
        viewHolder.rlLanguageSwitch.setOnClickListener { v ->
            if (!isChoose) {
                viewHolder.btnChoose.visibility = View.VISIBLE
                settingItemSelectListener?.onItemSelect(typeSwitchingBean, "")
                updateData(typeSwitchingBean)
            } else {
                settingItemSelectListener?.changeItem(false)

            }
        }

    }

    /**
     * 根據當前選中的item，刷新其他數據
     *
     * @param typeSwitchingBean
     */
    private fun updateData(typeSwitchingBean: TypeSwitchingBean?) {
        if (ListTool.isEmpty(typeSwitchingBeans)) {
            return
        }
        for (typeSwitchingBeanNew in typeSwitchingBeans) {
            val type = typeSwitchingBeanNew.type
            val typeChoose = typeSwitchingBean!!.type
            typeSwitchingBeanNew.isChoose = StringTool.equals(type, typeChoose)
        }
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return typeSwitchingBeans.size
    }


    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvLanguage: TextView = view.findViewById(R.id.tv_language)
        var btnChoose: Button = view.findViewById(R.id.btn_choose)
        var vLine: View = view.findViewById(R.id.v_line)
        var rlLanguageSwitch: RelativeLayout = view.findViewById(R.id.rl_language_switch)

    }

}