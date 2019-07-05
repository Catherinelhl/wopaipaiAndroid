package cn.wopaipai.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.wopaipai.R;
import cn.wopaipai.bean.ContactDetailsBean;

public class ContactListAdapter extends BaseQuickAdapter<ContactDetailsBean, BaseViewHolder> {
    public ContactListAdapter(int layoutResId, @Nullable List<ContactDetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactDetailsBean item) {
        helper.setText(R.id.tv_contact_name,item.getName());
        helper.setText(R.id.tv_contact_address,item.getAddress());
        helper.setText(R.id.tv_contact_coin_type,item.getCoinCode());
    }
}
