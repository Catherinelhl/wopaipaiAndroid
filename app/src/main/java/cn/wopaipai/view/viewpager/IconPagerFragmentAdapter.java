package cn.wopaipai.view.viewpager;


import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.wopaipai.R;

/**
 * 对加载带图标页码的viewpager的数据的适配
 */
public class IconPagerFragmentAdapter extends PagerAdapter implements IconPagerAdapter {
    List<Integer> icons = new ArrayList<>();
    List<View> views;
    int mCount;

    public IconPagerFragmentAdapter(List<View> views) {
        this.views = views;
        mCount = views.size();
        for (int i = 0; i < mCount; i++) {
            icons.add(R.drawable.selector_dot_indicator);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view, 0);
        return view;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getIconResId(int index) {
        return icons.get(index % icons.size());
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}
