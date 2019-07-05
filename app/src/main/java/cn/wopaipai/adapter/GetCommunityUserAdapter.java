package cn.wopaipai.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.wopaipai.R;
import cn.wopaipai.bean.GetCommunityUser;

public class GetCommunityUserAdapter extends BaseQuickAdapter<GetCommunityUser, BaseViewHolder> {
    public GetCommunityUserAdapter(int layoutResId, @Nullable List<GetCommunityUser> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetCommunityUser item) {
        helper.setText(R.id.tv_community_phone,item.getPhoneNumber());
        helper.setText(R.id.tv_community_level,"("+item.getLevelName()+")");
    }
}
