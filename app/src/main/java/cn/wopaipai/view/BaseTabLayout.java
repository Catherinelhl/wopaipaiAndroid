package cn.wopaipai.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.wopaipai.R;
import cn.wopaipai.base.BaseApplication;
import cn.wopaipai.base.BaseException;
import cn.wopaipai.constant.Constants;
import cn.wopaipai.tool.LogTool;

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-14 16:35
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.view
+--------------+---------------------------------
+ description  +  自定义tabLayout
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/
public class BaseTabLayout extends FrameLayout {
    private String TAG = BaseTabLayout.class.getSimpleName();
    private TabLayout mTabLayout;
    private List<String> mTabList;
    private List<View> mCustomViewList;
    private int mSelectIndicatorColor;
    private int mSelectTextColor;
    private int mUnSelectTextColor;
    private int mIndicatorHeight;
    private int mIndicatorWidth;
    private int mTabMode;
    private boolean fixThree;//是否鎖定三個
    private int mTabTextSize;
    private Context context;
    private int tabSize = 3;


    public BaseTabLayout(@NonNull Context context) {
        super(context);
        this.context = context;
        init(context, null);
    }

    public BaseTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    public BaseTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void readAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseTabLayout);
        mSelectIndicatorColor = typedArray.getColor(R.styleable.BaseTabLayout_tabIndicatorColor, context.getResources().getColor(R.color.button_color));
        mUnSelectTextColor = typedArray.getColor(R.styleable.BaseTabLayout_tabTextColor, context.getResources().getColor(R.color.grey_999999));
        mSelectTextColor = typedArray.getColor(R.styleable.BaseTabLayout_tabSelectTextColor, context.getResources().getColor(R.color.button_color));
        mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.BaseTabLayout_tabIndicatorHeight, 1);
        mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.BaseTabLayout_tabIndicatorWidth, 0);
        mTabTextSize = typedArray.getDimensionPixelSize(R.styleable.BaseTabLayout_tabTextSize, 13);
        mTabMode = typedArray.getInt(R.styleable.BaseTabLayout_tab_Mode, 2);
        fixThree = typedArray.getBoolean(R.styleable.BaseTabLayout_fixThree, false);
        typedArray.recycle();
    }

    private void init(Context context, AttributeSet attrs) {
        readAttr(context, attrs);

        mTabList = new ArrayList<>();
        mCustomViewList = new ArrayList<>();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tablayout, this, true);
        mTabLayout = view.findViewById(R.id.tab_view);//


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // onTabItemSelected(tab.getPosition());
                // Tab 选中之后，改变各个Tab的状态
                for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                    View view = mTabLayout.getTabAt(i).getCustomView();
                    if (view == null) {
                        return;
                    }
                    TextView text = (TextView) view.findViewById(R.id.tab_item_text);
                    View indicator = view.findViewById(R.id.tab_item_indicator);
                    ImageView imageView = view.findViewById(R.id.iv_icon);
                    if (i == tab.getPosition()) { // 选中状态
                        text.setTextColor(mSelectTextColor);
                        text.setTypeface(Typeface.DEFAULT_BOLD);
                        indicator.setBackgroundColor(mSelectIndicatorColor);
                        indicator.setVisibility(View.VISIBLE);
                        imageView.setAlpha(1f);
                    } else {// 未选中状态
                        text.setTextColor(mUnSelectTextColor);
                        text.setTypeface(Typeface.DEFAULT);
                        indicator.setVisibility(View.INVISIBLE);
                        imageView.setAlpha(0.5f);

                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public List<View> getCustomViewList() {
        return mCustomViewList;
    }

//    private TabLayout.OnTabSelectedListener onTabSelectedListener;

    public void addOnTabSelectedListener(TabLayout.OnTabSelectedListener onTabSelectedListener) {
        mTabLayout.addOnTabSelectedListener(onTabSelectedListener);
    }

    /**
     * 与TabLayout 联动
     *
     * @param viewPager
     */
    public void setupWithViewPager(@Nullable ViewPager viewPager, TabLayout.OnTabSelectedListener onTabSelectedListener) {
        this.setupWithViewPager(new BaseApplication().getScreenWidth(), viewPager, onTabSelectedListener);
    }

    /**
     * 与TabLayout 联动
     *
     * @param viewPager
     */
    public void setupWithViewPager(int width, @Nullable ViewPager viewPager, TabLayout.OnTabSelectedListener onTabSelectedListener) {
        mTabLayout.addOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager, this, onTabSelectedListener));
        measureLayoutParams(fixThree, width);
    }

    /**
     * 重新测量布局参数
     *
     * @param fixThree
     */
    public void measureLayoutParams(boolean fixThree, int screenWidth) {
        Class<?> tabLayout = mTabLayout.getClass();
        int widthTemp;
        if (fixThree) {
            if (tabSize <= 3) {
                widthTemp = screenWidth / tabSize;
            } else {
                widthTemp = (int) (screenWidth / 3.5);
            }
        } else {
            widthTemp = screenWidth / tabSize;
        }

        Field tabStrip = null;
        try {
            //INVALID_WIDTH
            tabStrip = tabLayout.getDeclaredField(Constants.TabLayout.SLIDING_TAB_INDICATOR);
            tabStrip.setAccessible(true);
            LinearLayout ll_tab = (LinearLayout) tabStrip.get(mTabLayout);
            for (int i = 0; i < ll_tab.getChildCount(); i++) {
                View child = ll_tab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params;
                if (fixThree) {
                    if (tabSize <= 3) {
                        params = new LinearLayout.LayoutParams(widthTemp, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    } else {
                        params = new LinearLayout.LayoutParams(widthTemp, LinearLayout.LayoutParams.MATCH_PARENT);
                    }
                } else {
                    params = new LinearLayout.LayoutParams(widthTemp, LinearLayout.LayoutParams.MATCH_PARENT, 1);

                }
                //强制设置item没有编剧
                params.setMarginStart(0);
                params.setMarginEnd(0);
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (Exception e) {
            BaseException.INSTANCE.print(e);
            LogTool.e(TAG, e.getMessage());
        }
    }


    /**
     * retrive TabLayout Instance
     *
     * @return
     */
    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    /**
     * 添加tab
     *
     * @param tab
     */
    public void addTab(String tab, int index) {
        mTabList.add(tab);
        View customView = getTabView(getContext(), tab, mIndicatorWidth, mIndicatorHeight, mTabTextSize);
        mCustomViewList.add(customView);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(customView), index == 0);
        // 根据当前显示的个数来判断是否需要滚动
        mTabLayout.setTabMode(mTabList.size() <= tabSize ? TabLayout.MODE_FIXED : TabLayout.MODE_SCROLLABLE);
    }

    /**
     * 如果当前tabLayout不为空，那么移除内部已经加载的所有的View
     */
    public void removeTabLayout() {
        if (mTabLayout != null) {
            mTabLayout.removeAllTabs();
        }
    }

    public static class ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener {

        private final ViewPager mViewPager;
        private final WeakReference<BaseTabLayout> mTabLayoutRef;
        private final TabLayout.OnTabSelectedListener onTabSelectedListener;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager, BaseTabLayout bcaasTabLayout, TabLayout.OnTabSelectedListener tabSelectedListener) {
            mViewPager = viewPager;
            mTabLayoutRef = new WeakReference<BaseTabLayout>(bcaasTabLayout);
            this.onTabSelectedListener = tabSelectedListener;
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (this.onTabSelectedListener != null) {
                onTabSelectedListener.onTabSelected(tab);
            }
            mViewPager.setCurrentItem(tab.getPosition());
            BaseTabLayout mTabLayout = mTabLayoutRef.get();
            if (mTabLayoutRef != null) {
                List<View> customViewList = mTabLayout.getCustomViewList();
                if (customViewList == null || customViewList.size() == 0) {
                    return;
                }
                for (int i = 0; i < customViewList.size(); i++) {
                    View view = customViewList.get(i);
                    if (view == null) {
                        return;
                    }
                    TextView text = (TextView) view.findViewById(R.id.tab_item_text);
                    View indicator = view.findViewById(R.id.tab_item_indicator);
                    ImageView imageView = view.findViewById(R.id.iv_icon);
                    if (i == tab.getPosition()) { // 选中状态
                        text.setTextColor(mTabLayout.mSelectTextColor);
                        text.setTypeface(Typeface.DEFAULT_BOLD);
                        indicator.setBackgroundColor(mTabLayout.mSelectIndicatorColor);
                        indicator.setVisibility(View.VISIBLE);
                        imageView.setAlpha(1f);
                    } else {// 未选中状态
                        text.setTypeface(Typeface.DEFAULT);
                        text.setTextColor(mTabLayout.mUnSelectTextColor);
                        indicator.setVisibility(View.INVISIBLE);
                        imageView.setAlpha(0.5f);

                    }
                }
            }

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            // No-op
            if (this.onTabSelectedListener != null) {
                onTabSelectedListener.onTabUnselected(tab);
            }
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            // No-op
            if (this.onTabSelectedListener != null) {
                onTabSelectedListener.onTabReselected(tab);
            }
        }
    }

    /**
     * 获取Tab 显示的内容
     *
     * @param context
     * @param
     * @return
     */
    public View getTabView(Context context, String text, int indicatorWidth, int indicatorHeight, int textSize) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tab_layout, null);
        TextView tabText = (TextView) view.findViewById(R.id.tab_item_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
        if (indicatorWidth > 0) {
            View indicator = view.findViewById(R.id.tab_item_indicator);
            ViewGroup.LayoutParams layoutParams = indicator.getLayoutParams();
            layoutParams.width = indicatorWidth;
            layoutParams.height = indicatorHeight;
            indicator.setLayoutParams(layoutParams);
        }
        tabText.setTextSize(textSize);
        tabText.setText(text);
//        if (StringTool.notEmpty(text)) {
//            imageView.setVisibility(VISIBLE);
//            switch (text) {
//                case "BTC":
//                    imageView.setImageResource(R.mipmap.icon_coin_btc);
//                    break;
//                case "ETH":
//                    imageView.setImageResource(R.mipmap.icon_coin_eth);
//                    break;
//                case "ZBB":
//                    imageView.setImageResource(R.mipmap.icon_coin_zbb);
//                    break;
//                case "BCC":
//                    imageView.setImageResource(R.mipmap.icon_coin_bcc);
//                    break;
//                case "CNYC":
//                    imageView.setImageResource(R.mipmap.icon_coin_cnyc);
//                    break;
//                default:
//                    imageView.setVisibility(GONE);
//                    break;
//            }
//        }
        return view;
    }

    /**
     * 设置选中的tab
     *
     * @param position
     */
    public void selectTab(int position) {
        if (mTabLayout != null) {
            mTabLayout.getTabAt(position).select();
        }
    }

    public void setTabSize(int tabSize) {
        this.tabSize = tabSize;
    }
}
