package cn.wopaipai.adapter;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-13 15:30
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.adapter
+--------------+---------------------------------
+ description  +  适配类：热门商品
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.wopaipai.R;
import cn.wopaipai.base.BaseApplication;
import cn.wopaipai.bean.ProductsBean;
import cn.wopaipai.constant.MessageConstants;
import cn.wopaipai.listener.OnItemSelectListener;
import cn.wopaipai.tool.ListTool;
import cn.wopaipai.tool.LogTool;
import cn.wopaipai.utils.GlideImgManager;

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-13 16:23
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.adapter
+--------------+---------------------------------
+ description  +  數據適配器：热门商品
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/
public class HotCommoditiesAdapter extends BaseAdapter {
    private String TAG = HotCommoditiesAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<ProductsBean> productsBeans=new ArrayList<>();
    private int width, height;
    private OnItemSelectListener onItemSelectListener;

    public HotCommoditiesAdapter(Context context) {
        this.context = context;
        width = (new BaseApplication().getScreenWidth() - context.getResources().getDimensionPixelOffset(R.dimen.d31)) / 2;
        height = width * 256 / 190;//172
    }

    public void addList(ArrayList<ProductsBean> productsBeans) {
        this.productsBeans.clear();
        this.productsBeans.addAll(productsBeans);
        notifyDataSetChanged();
    }

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    @Override
    public int getCount() {
        return ListTool.isEmpty(productsBeans) ? 0 : productsBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return ListTool.isEmpty(productsBeans) ? new ProductsBean() : productsBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_main_gridview, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            //根据屏幕的大小调整item的大小
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) convertView.getLayoutParams();
            if (params == null) {
                params = new LinearLayout.LayoutParams(width, height);
                convertView.setLayoutParams(params);
            } else {
                params.width = width;
                params.height = height;
            }

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ProductsBean productsBean = productsBeans.get(position);
        if (productsBean != null) {
            viewHolder.tvInfo.setText(productsBean.getProductName());
            viewHolder.tvPrice.setText(String.format(context.getString(R.string.double_s),
                    context.getString(R.string.symbol_dollar), String.valueOf(productsBean.getPrice())));
            GlideImgManager.displayImage(context, productsBean.getImageUrl(), viewHolder.imageView);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectListener != null) {
                    onItemSelectListener.onItemSelect(productsBean, MessageConstants.Empty);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView tvPrice;
        private TextView tvInfo;
        private ImageView imageView;

        public ViewHolder(View view) {
            super();
            tvInfo = view.findViewById(R.id.tv_info);
            tvPrice = view.findViewById(R.id.tv_price);
            imageView = view.findViewById(R.id.iv_cover);
        }


    }
}
