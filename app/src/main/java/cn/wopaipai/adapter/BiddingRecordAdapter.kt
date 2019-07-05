package cn.wopaipai.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.wopaipai.R
import cn.wopaipai.bean.BiddingRecordBean
import cn.wopaipai.bean.CommodityParamsBean
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.tool.ListTool

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-16 10:54
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.adapter
+--------------+---------------------------------
+ description  +   适配器：出价记录
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class BiddingRecordAdapter(
    private val context: Context,
    private var biddingRecordBeans: List<BiddingRecordBean>?
) : RecyclerView.Adapter<BiddingRecordAdapter.viewHolder>() {


    override fun onBindViewHolder(viewHolder: viewHolder, position: Int) {
        biddingRecordBeans?.let {
            //判断如果当前是最新的一条，那么需要显示不同的颜色
            if (position == 0){
                viewHolder.tvUserName.setTextColor(context.resources.getColor(R.color.orange_FAAE00))
                viewHolder.tvCurrentPrice.setTextColor(context.resources.getColor(R.color.orange_FAAE00))
                viewHolder.tvIncrementPrice.setTextColor(context.resources.getColor(R.color.orange_FAAE00))
            }
            val biddingRecordBean = it[position]
            viewHolder.tvUserName.text = biddingRecordBean.name
            viewHolder.tvCurrentPrice.text =
                biddingRecordBean.currentPrice +MessageConstants.Space + biddingRecordBean.currencyType
            viewHolder.tvIncrementPrice.text =
                context.resources.getString(R.string.plus_sign) + biddingRecordBean.incrementPrice + MessageConstants.Space + biddingRecordBean.currencyType
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): viewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_bidding_record, viewGroup, false)
        return viewHolder(view)
    }


    var onItemSelectListener: OnItemSelectListener? = null


    override fun getItemCount(): Int =
        if (ListTool.isEmpty(biddingRecordBeans)) 0 else biddingRecordBeans!!.size

    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvUserName: TextView = view.findViewById(R.id.tv_user_name)
        var tvCurrentPrice: TextView = view.findViewById(R.id.tv_current_top_price)
        var tvIncrementPrice: TextView = view.findViewById(R.id.tv_increment_price)

    }
}