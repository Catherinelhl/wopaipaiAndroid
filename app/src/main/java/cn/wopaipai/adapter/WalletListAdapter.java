package cn.wopaipai.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DecimalFormat;
import java.util.List;

import cn.wopaipai.R;
import cn.wopaipai.bean.GetWalletListBean;
import cn.wopaipai.utils.DecimalFormatUtils;

public class WalletListAdapter extends BaseQuickAdapter<GetWalletListBean, BaseViewHolder> {
    public WalletListAdapter( @Nullable List<GetWalletListBean> data) {
        super(R.layout.recycler_wallet_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetWalletListBean item) {
        double amount = Double.parseDouble(item.getAmountFormat());
        helper.setText(R.id.tv_time,item.getCreateTime());
        if (item.getAmount()<0){
            helper.setTextColor(R.id.tv_money,Color.RED);
        }
        switch (item.getFlowType()){
            case 1:  // 转入
                helper.setBackgroundRes(R.id.iv_head,R.mipmap.zhuanru);
                helper.setText(R.id.tv_msg,item.getTypeName());
                if (amount<0){
                    helper.setTextColor(R.id.tv_money,Color.RED);
                }else {
                    helper.setTextColor(R.id.tv_money, Color.GREEN);
                }
                helper.setText(R.id.tv_money,item.getAmountFormat());
                break;
            case 2:  // 转出
                helper.setBackgroundRes(R.id.iv_head,R.mipmap.zhuanchu);
                helper.setText(R.id.tv_msg,item.getTypeName());
                if (amount<0){
                    helper.setTextColor(R.id.tv_money,Color.RED);
                }else {
                    helper.setTextColor(R.id.tv_money, Color.GREEN);
                }
                helper.setText(R.id.tv_money,item.getAmountFormat());
                break;
            case 3: // 兑换
                helper.setBackgroundRes(R.id.iv_head,R.mipmap.exchange);
                helper.setText(R.id.tv_msg,item.getTypeName());
                if (amount<0){
                    helper.setTextColor(R.id.tv_money,Color.RED);
                }else {
                    helper.setTextColor(R.id.tv_money, Color.GREEN);
                }
                helper.setText(R.id.tv_money,item.getAmountFormat());
                break;
            case 4: // 竞拍加价
                helper.setBackgroundRes(R.id.iv_head,R.mipmap.jingpai);
                helper.setText(R.id.tv_msg,item.getTypeName());
                if (amount<0){
                    helper.setTextColor(R.id.tv_money,Color.RED);
                }else {
                    helper.setTextColor(R.id.tv_money, Color.GREEN);
                }
                helper.setText(R.id.tv_money,item.getAmountFormat());
                break;
            case 5: // 竞拍红包
                helper.setBackgroundRes(R.id.iv_head,R.mipmap.redcard);
                helper.setText(R.id.tv_msg,item.getTypeName());
                if (amount<0){
                    helper.setTextColor(R.id.tv_money,Color.RED);
                }else {
                    helper.setTextColor(R.id.tv_money, Color.GREEN);
                    helper.setText(R.id.tv_money,item.getAmountFormat());
                }
                break;
            case 6: // 购买
                helper.setBackgroundRes(R.id.iv_head,R.mipmap.shopping);
                helper.setText(R.id.tv_msg,item.getTypeName());
                if (amount<0){
                    helper.setTextColor(R.id.tv_money,Color.RED);
                }else {
                    helper.setTextColor(R.id.tv_money, Color.GREEN);
                }
                helper.setText(R.id.tv_money,item.getAmountFormat());
                break;
            case 7: // 托管
                helper.setBackgroundRes(R.id.iv_head,R.mipmap.tuoguan);
                helper.setText(R.id.tv_msg,item.getTypeName());
                if (amount<0){
                    helper.setTextColor(R.id.tv_money,Color.RED);
                }else {
                    helper.setTextColor(R.id.tv_money, Color.GREEN);
                }
                helper.setText(R.id.tv_money,item.getAmountFormat());
                break;
            case 8: // 竞拍成功押金扣除
                helper.setBackgroundRes(R.id.iv_head,R.mipmap.jingpai);
                helper.setText(R.id.tv_msg,item.getTypeName());
                if (amount<0){
                    helper.setTextColor(R.id.tv_money,Color.RED);
                }else {
                    helper.setTextColor(R.id.tv_money, Color.GREEN);
                }
                helper.setText(R.id.tv_money,item.getAmountFormat());
                break;
            case 9: // 直推会员加价奖励
                helper.setBackgroundRes(R.id.iv_head,R.mipmap.redcard);
                helper.setText(R.id.tv_msg,item.getTypeName());
                if (amount<0){
                    helper.setTextColor(R.id.tv_money,Color.RED);
                }else {
                    helper.setTextColor(R.id.tv_money, Color.GREEN);
                }
                helper.setText(R.id.tv_money,item.getAmountFormat());
                break;
        }
    }
}
