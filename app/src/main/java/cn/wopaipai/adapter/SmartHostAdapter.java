package cn.wopaipai.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.wopaipai.R;
import cn.wopaipai.constant.MessageConstants;
import cn.wopaipai.listener.OnItemSelectListener;

public class SmartHostAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private OnItemSelectListener onItemSelectListener;


    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }


    public SmartHostAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setOnClickListener(R.id.sb_intelligent_hosting, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectListener != null) {
                    onItemSelectListener.onItemSelect(null, MessageConstants.Empty);
                }
//                helper.setBackgroundRes(R.id.sb_intelligent_hosting,R.mipmap.host_close);
            }
        });
    }
}
