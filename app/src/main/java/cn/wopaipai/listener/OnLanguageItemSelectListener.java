package cn.wopaipai.listener;

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 21:38
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.listener
+--------------+---------------------------------
+ description  +   回調監聽：语言切换條目选择器監聽回調響應
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/
public interface OnLanguageItemSelectListener {
    /**
     * 返回當前選擇欄目的數據類
     */
    <T> void onItemSelect(T type, String from);

    void changeItem(boolean b);
}
