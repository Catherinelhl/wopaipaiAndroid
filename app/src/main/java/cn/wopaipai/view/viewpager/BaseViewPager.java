package cn.wopaipai.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/4/9 13:46
+--------------+---------------------------------
+ projectName  +   ExchangeAndroid
+--------------+---------------------------------
+ packageName  +   io.bcaas.exchange.view.viewpager
+--------------+---------------------------------
+ description  +   自定义一个解决滑动冲突的viewpager
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

public class BaseViewPager extends ViewPager {
    private String TAG = BaseViewPager.class.getSimpleName();

    public BaseViewPager(Context context) {
        super(context);
    }

    public BaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean canSlide = true;

    public boolean isCanSlide() {
        return canSlide;
    }

    public void setCanSlide(boolean canSlide) {
        this.canSlide = canSlide;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (canSlide) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (canSlide) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }
}
