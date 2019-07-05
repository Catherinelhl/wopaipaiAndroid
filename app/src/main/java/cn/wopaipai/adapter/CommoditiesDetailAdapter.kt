package cn.wopaipai.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cn.wopaipai.R
import cn.wopaipai.adapter.CommoditiesDetailAdapter.viewHolder
import cn.wopaipai.bean.AuctionDetailImagesBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.tool.ListTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.utils.GlideImgManager
import com.jakewharton.rxbinding2.view.RxView
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-13 20:54
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.adapter
+--------------+---------------------------------
+ description  +   适配器：商品详情
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class CommoditiesDetailAdapter(
    private val context: Context,
    private var images: ArrayList<AuctionDetailImagesBean>?
) : RecyclerView.Adapter<viewHolder>() {


    private val TAG = CommoditiesDetailAdapter::class.java.simpleName
    var onItemSelectListener: OnItemSelectListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): viewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_commodity_detail, viewGroup, false)
        return viewHolder(view)
    }

    fun addList(images: List<AuctionDetailImagesBean>) {
        this.images?.let {
            it.clear()
            it.addAll(images)
            notifyDataSetChanged()
        }
    }


    override fun getItemCount(): Int = if (ListTool.isEmpty(images)) 0 else images!!.size

    override fun onBindViewHolder(viewHolder: viewHolder, position: Int) {
        images ?: return
        val detailImages = images!![position]
        GlideImgManager.displayImage(context, detailImages.imageUrl, viewHolder.ivCommoditiesDetail)
        val subscribe = RxView.clicks(viewHolder.ivCommoditiesDetail)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe {
                onItemSelectListener?.let {
                    it.onItemSelect(detailImages, MessageConstants.Empty)
                }
            }
    }

    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivCommoditiesDetail: ImageView = view.findViewById(R.id.iv_commodity_detail)

    }
}