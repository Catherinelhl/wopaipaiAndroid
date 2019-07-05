package cn.wopaipai.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class CommonPopupWindow extends PopupWindow {

    final PopupController controller;

    @Override
    public int getWidth() {
        return controller.mPopupView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return controller.mPopupView.getMeasuredHeight();
    }

    public interface ViewInterface {
        void getChildView(View view, int layoutResId);
    }

    private CommonPopupWindow(Context context) {
        controller = new PopupController(context, this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        controller.setBackGroundLevel(1.0f);
    }

    public static class Builder {
        private final PopupController.PopupParams params;
        private ViewInterface listener;

        public Builder(Context context) {
            params = new PopupController.PopupParams(context);
        }

        /**
         * @param layoutResId 设置PopupWindow 布局ID
         * @return Builder
         */
        public Builder setView(int layoutResId) {
            params.mView = null;
            params.layoutResId = layoutResId;
            return this;
        }

        /**
         * @param view 设置PopupWindow布局
         * @return Builder
         */
        public Builder setView(View view) {
            params.mView = view;
            params.layoutResId = 0;
            return this;
        }

        /**
         * 设置子View
         *
         * @param listener ViewInterface
         * @return Builder
         */
        public Builder setViewOnclickListener(ViewInterface listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 设置宽度和高度 如果不设置 默认是wrap_content
         *
         * @param width 宽
         * @return Builder
         */
        public Builder setWidthAndHeight(int width, int height) {
            params.mWidth = width;
            params.mHeight = height;
            return this;
        }

        /**
         * 设置背景灰色程度
         *
         * @param level 0.0f-1.0f
         * @return Builder
         */
        public Builder setBackGroundLevel(float level) {
            params.isShowBg = true;
            params.bg_level = level;
            return this;
        }

        /**
         * 是否可点击Outside消失
         *
         * @param touchable 是否可点击
         * @return Builder
         */
        public Builder setOutsideTouchable(boolean touchable) {
            params.isTouchable = touchable;
            return this;
        }

        /**
         * 设置动画
         *
         * @return Builder
         */
        public Builder setAnimationStyle(int animationStyle) {
            params.isShowAnim = true;
            params.animationStyle = animationStyle;
            return this;
        }

        public CommonPopupWindow create() {
            final CommonPopupWindow popupWindow = new CommonPopupWindow(params.mContext);
            params.apply(popupWindow.controller);
            if (listener != null && params.layoutResId != 0) {
                listener.getChildView(popupWindow.controller.mPopupView, params.layoutResId);
            }
            return popupWindow;
        }
    }

    static class PopupController {
        private int layoutResId;//布局id
        private Context context;
        private PopupWindow popupWindow;
        View mPopupView;//弹窗布局View
        private View mView;
        private Window mWindow;

        PopupController(Context context, PopupWindow popupWindow) {
            this.context = context;
            this.popupWindow = popupWindow;
        }

        public void setView(int layoutResId) {
            mView = null;
            this.layoutResId = layoutResId;
            installContent();
        }

        public void setView(View view) {
            mView = view;
            this.layoutResId = 0;
            installContent();
        }

        private void installContent() {
            if (layoutResId != 0) {
                mPopupView = LayoutInflater.from(context).inflate(layoutResId, null);
            } else if (mView != null) {
                mPopupView = mView;
            }
            popupWindow.setContentView(mPopupView);
        }

        /**
         * 设置宽度
         *
         * @param width  宽
         * @param height 高
         */
        private void setWidthAndHeight(int width, int height) {
            if (width == 0 || height == 0) {
                //如果没设置宽高，默认是WRAP_CONTENT
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                popupWindow.setWidth(width);
                popupWindow.setHeight(height);
            }
        }


        /**
         * 设置背景灰色程度
         *
         * @param level 0.0f-1.0f
         */
        void setBackGroundLevel(float level) {
            mWindow = ((Activity) context).getWindow();
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.alpha = level;
            mWindow.setAttributes(params);
        }


        /**
         * 设置动画
         */
        private void setAnimationStyle(int animationStyle) {
            popupWindow.setAnimationStyle(animationStyle);
        }

        /**
         * 设置Outside是否可点击
         *
         * @param touchable 是否可点击
         */
        private void setOutsideTouchable(boolean touchable) {
            popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置透明背景
            popupWindow.setOutsideTouchable(touchable);//设置outside可点击
            popupWindow.setFocusable(touchable);
        }


       static class PopupParams {
            public int layoutResId;//布局id
            public Context mContext;
            public int mWidth, mHeight;//弹窗的宽和高
            public boolean isShowBg, isShowAnim;
            public float bg_level;//屏幕背景灰色程度
            public int animationStyle;//动画Id
            public View mView;
            public boolean isTouchable = true;

            public PopupParams(Context mContext) {
                this.mContext = mContext;
            }

            public void apply(PopupController controller) {
                if (mView != null) {
                    controller.setView(mView);
                } else if (layoutResId != 0) {
                    controller.setView(layoutResId);
                } else {
                    throw new IllegalArgumentException("PopupView's contentView is null");
                }
                controller.setWidthAndHeight(mWidth, mHeight);
                controller.setOutsideTouchable(isTouchable);//设置outside可点击
                if (isShowBg) {
                    //设置背景
                    controller.setBackGroundLevel(bg_level);
                }
                if (isShowAnim) {
                    controller.setAnimationStyle(animationStyle);
                }
            }
        }
    }
}
