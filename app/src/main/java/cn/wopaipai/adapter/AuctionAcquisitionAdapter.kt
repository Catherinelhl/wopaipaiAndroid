package cn.wopaipai.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseException
import cn.wopaipai.bean.AuctionAcquisitionBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.tool.ListTool
import com.jakewharton.rxbinding2.view.RxView
import java.util.concurrent.TimeUnit

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
+ description  +   适配器：竞拍所得
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class AuctionAcquisitionAdapter(
    private val context: Context?,
    private var auctionAcquisitionBeans: ArrayList<AuctionAcquisitionBean>
) : RecyclerView.Adapter<AuctionAcquisitionAdapter.viewHolder>() {
    var width: Int = 0//得到当前图片应该的宽度
    var onItemSelectListener: OnItemSelectListener? = null

    init {
        context?.let {
            var itemWidth =
                BaseApplication.screenWidth - (it.resources.getDimensionPixelOffset(R.dimen.d12) * 2)
            //设置图片的大小 130：221；
            width = (itemWidth - it.resources.getDimensionPixelOffset(R.dimen.d10)) / 351 * 130
        }

    }


    override fun onBindViewHolder(viewHolder: viewHolder, position: Int) {
        auctionAcquisitionBeans?.let {
            val acquisitionBean = it[position]
            //设置图片的大小 130：112
            var layoutParams = viewHolder.ivCover.layoutParams
            layoutParams.width = width
            layoutParams.height = width * 112 / 130
            viewHolder.ivCover.layoutParams = layoutParams

            viewHolder.tvName.text = acquisitionBean.name
            viewHolder.tvInfo.text =context!!.resources.getString(R.string.auction_price)+"\t"+
                    acquisitionBean.auctionPrice+"\t"+MessageConstants.AUCTION_TYPE+"\t"+context.resources.getString(R.string.get_in)
            viewHolder.tvTime.text = acquisitionBean.time
            RxView.clicks(viewHolder.tvApplyAuction)
                .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
                .subscribe({
                    onItemSelectListener?.let {
                        it.onItemSelect(
                            acquisitionBean,
                            Constants.ActionFrom.APPLY_AUCTION
                        )
                    }
                },
                    { e -> BaseException.print(e) })

            RxView.clicks(viewHolder.tvSwitch)
                .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
                .subscribe({
                    onItemSelectListener?.let {
                        it.onItemSelect(
                            acquisitionBean,
                            Constants.ActionFrom.REPLACEMENT_COMMODITY
                        )
                    }
                },
                    { e -> BaseException.print(e) })

            RxView.clicks(viewHolder.tvGet)
                .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
                .subscribe({
                    onItemSelectListener?.let {
                        it.onItemSelect(
                            acquisitionBean,
                            Constants.ActionFrom.RECEIVE_COMMODITY
                        )
                    }
                },
                    { e -> BaseException.print(e) })
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): viewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_auction_acquisition, viewGroup, false)
        return viewHolder(view)
    }


    override fun getItemCount(): Int =
        if (ListTool.isEmpty(auctionAcquisitionBeans)) 0 else auctionAcquisitionBeans!!.size

    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.findViewById(R.id.tv_auction_commodity_name)
        var tvInfo: TextView = view.findViewById(R.id.tv_info)
        var tvTime: TextView = view.findViewById(R.id.tv_time)
        var tvApplyAuction: TextView = view.findViewById(R.id.tv_apply_auction)
        var tvSwitch: TextView = view.findViewById(R.id.tv_switch)
        var tvGet: TextView = view.findViewById(R.id.tv_get)
        var ivCover: ImageView = view.findViewById(R.id.iv_auction_acquisition_cover)

    }
}