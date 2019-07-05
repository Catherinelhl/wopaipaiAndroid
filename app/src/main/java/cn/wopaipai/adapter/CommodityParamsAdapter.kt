package cn.wopaipai.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.wopaipai.R
import cn.wopaipai.bean.CommodityParamsBean
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.tool.ListTool

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-14 10:54
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.adapter
+--------------+---------------------------------
+ description  +   适配器：商品参数
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class CommodityParamsAdapter(
    private val context: Context,
    private var paramsBean: List<CommodityParamsBean>?
) : RecyclerView.Adapter<CommodityParamsAdapter.viewHolder>() {


    override fun onBindViewHolder(viewHolder: viewHolder, position: Int) {
        paramsBean?.let {
            val productParamsBean = it[position]
            viewHolder.tvParamName.text = productParamsBean.name
            var sb: StringBuffer = StringBuffer()
            val paramInfo = productParamsBean.paramsInfo
            for (i in paramInfo.indices) {
                sb.append(paramInfo[i])
                if (i < paramInfo.size - 1) {
                    //如果不是最后一位数，继续添加间隔符
                    sb.append("\t\t")

                }
            }
            viewHolder.tvParamInfo.text = sb
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): viewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_commodity_params, viewGroup, false)
        return viewHolder(view)
    }


    var onItemSelectListener: OnItemSelectListener? = null


    override fun getItemCount(): Int = if (ListTool.isEmpty(paramsBean)) 0 else paramsBean!!.size

    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvParamName: TextView = view.findViewById(R.id.tv_params_name)
        var tvParamInfo: TextView = view.findViewById(R.id.tv_params_info)

    }
}