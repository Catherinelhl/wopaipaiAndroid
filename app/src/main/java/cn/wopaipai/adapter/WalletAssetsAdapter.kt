package cn.wopaipai.adapter

import android.view.View
import android.widget.ImageView
import cn.wopaipai.R
import cn.wopaipai.bean.CoinsBean
import cn.wopaipai.tool.decimal.DecimalTool
import cn.wopaipai.utils.DecimalFormatUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-07-04 09:59
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.adapter
+--------------+---------------------------------
+ description  +  钱包资产adapter
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class WalletAssetsAdapter(dataSize: List<CoinsBean>) :
    BaseQuickAdapter<CoinsBean, BaseViewHolder>(R.layout.recycle_wallet_item, dataSize) {

    override fun convert(helper: BaseViewHolder, item: CoinsBean) {
        helper.setText(R.id.tv_wallet_name, item.coinCode)
        val balance = item.balance
        val rate = item.exUsdRate
        helper.setText(R.id.tv_wallet_num, DecimalTool.transferDisplay(balance))
        helper.setText(
            R.id.tv_wallet_value,
            "≈$" + DecimalFormatUtils.getDecimalFormaTwo(balance * rate)
        )
        helper.setText(R.id.tv_quotation, "≈$" + DecimalFormatUtils.getDecimalFormaTwo(rate))
        Glide.with(mContext).load(item.logoUrl)
            .into(helper.getView<View>(R.id.iv_wallet_head) as ImageView)
    }
}