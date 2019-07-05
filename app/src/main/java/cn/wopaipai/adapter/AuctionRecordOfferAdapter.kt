package cn.wopaipai.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.wopaipai.R
import cn.wopaipai.bean.AuctionRecordOfferBean
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.tool.ListTool
import cn.wopaipai.tool.LogTool

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

class AuctionRecordOfferAdapter(
    private val context: Context
) : RecyclerView.Adapter<AuctionRecordOfferAdapter.viewHolder>() {
    private val TAG = AuctionRecordOfferAdapter::class.java.simpleName
    private var auctionRecordOfferBeans: ArrayList<AuctionRecordOfferBean> = arrayListOf()
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: viewHolder, position: Int) {
        auctionRecordOfferBeans?.let {
            //判断如果当前是最新的一条，那么需要显示不同的颜色
            var isFirstItem = position == 0
            viewHolder.tvUserName.setTextColor(context.resources.getColor(if (isFirstItem) R.color.orange_FAAE00 else R.color.grey_999999))
            viewHolder.tvCurrentPrice.setTextColor(context.resources.getColor(if (isFirstItem) R.color.orange_FAAE00 else R.color.grey_999999))
            viewHolder.tvIncrementPrice.setTextColor(context.resources.getColor(if (isFirstItem) R.color.orange_FAAE00 else R.color.grey_999999))
            val auctionRecordOfferBean = it[position]

            viewHolder.tvUserName.text = auctionRecordOfferBean.nickName
            viewHolder.tvCurrentPrice.text =
                "${auctionRecordOfferBean.currentPrice}\t\t${MessageConstants.AUCTION_TYPE}"
            viewHolder.tvIncrementPrice.text =
                "${auctionRecordOfferBean.offerPrice}\t\t${MessageConstants.AUCTION_TYPE}"
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): viewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_bidding_record, viewGroup, false)
        return viewHolder(view)
    }


    var onItemSelectListener: OnItemSelectListener? = null


    override fun getItemCount(): Int =
        if (ListTool.isEmpty(auctionRecordOfferBeans)) 0 else auctionRecordOfferBeans!!.size

    /**
     * 重新添加数据
     */
    fun addList(auctionRecordOfferBeans: ArrayList<AuctionRecordOfferBean>) {
        this.auctionRecordOfferBeans?.let {
            it.clear()
            it.addAll(auctionRecordOfferBeans)
            notifyDataSetChanged()
        }

    }

    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvUserName: TextView = view.findViewById(R.id.tv_user_name)
        var tvCurrentPrice: TextView = view.findViewById(R.id.tv_current_top_price)
        var tvIncrementPrice: TextView = view.findViewById(R.id.tv_increment_price)

    }
}