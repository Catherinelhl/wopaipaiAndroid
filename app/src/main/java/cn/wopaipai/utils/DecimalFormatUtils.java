package cn.wopaipai.utils;

// 保留10位小数

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import cn.wopaipai.constant.MessageConstants;
import cn.wopaipai.tool.StringTool;

public class DecimalFormatUtils {
    public static String getDecimalForma(float num) {
        BigDecimal bd = new BigDecimal(num + "");
        bd = bd.setScale(10, BigDecimal.ROUND_DOWN);
        return bd.toString();
    }

    public static String getDecimalFormaTwo(double num) {
        BigDecimal bd = new BigDecimal(num + "");
        bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
        return bd.toString();
    }


    /***
     *  a 除以 b  保留四位小数点
     * @param a
     * @param b
     * @return
     */
    public static double getDivideValue(double a, double b) {
        BigDecimal value = new BigDecimal(a + "");
        value = value.divide(new BigDecimal(b + ""), 4, BigDecimal.ROUND_DOWN);
        return value.doubleValue();
    }
}
