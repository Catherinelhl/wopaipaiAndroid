package cn.wopaipai.tool.decimal;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import cn.wopaipai.constant.MessageConstants;
import cn.wopaipai.tool.StringTool;


/**
 * 工具類：DecimalTool
 */
public class DecimalTool {
    private static String TAG = DecimalTool.class.getSimpleName();

    /**
     * 第一個參數 - 第二個參數 ＝ 回傳的數值
     * <br>
     * FirstValue不能小於SecondValue
     *
     * @param firstValue
     * @param secondValue
     * @param canEqual    是否可以相等
     */
    public static String calculateFirstSubtractSecondValue(String firstValue, String secondValue, boolean canEqual) {
        return calculateFirstSubtractSecondValue(firstValue, secondValue, canEqual, "0.0000000000");
    }

    public static String calculateFirstSubtractSecondValue(String firstValue, String secondValue, boolean canEqual, String pattern) {

        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        // 計算小數八位，第九位無條件捨去
        BigDecimal bigDecimalFirstValue = new BigDecimal(firstValue).setScale(10, RoundingMode.FLOOR);
        BigDecimal bigDecimalSecondValue = new BigDecimal(secondValue).setScale(10, RoundingMode.FLOOR);

        // FirstValue必须大于SecondValue
        if (canEqual) {
            if (bigDecimalFirstValue.compareTo(bigDecimalSecondValue) == -1) {
                return MessageConstants.NO_ENOUGH_BALANCE;
            }
        } else {
            if (bigDecimalFirstValue.compareTo(bigDecimalSecondValue) != 1) {
                return MessageConstants.NO_ENOUGH_BALANCE;
            }
        }

        BigDecimal bigDecimalNum = bigDecimalFirstValue.subtract(bigDecimalSecondValue);

        String num = decimalFormat.format(bigDecimalNum);

        return num;
    }


    /**
     * 去掉分隔符返回可用于计算的数
     */
    public static String getStringReplaceComma(String number) {
        return number.replace(",", "");
    }

    /**
     * 轉換成顯示每三位數加逗號，小數只顯示八位
     *
     * @param decimal
     */
    public static String transferDisplay(String decimal) {
        if (StringTool.isEmpty(decimal)) {
            decimal = "0";
        }

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.0000000000");
        BigDecimal bigDecimal = new BigDecimal(decimal).setScale(10, RoundingMode.FLOOR);

        return decimalFormat.format(bigDecimal);
    }

    /**
     * 轉換成顯示每三位數加逗號，小數只顯示八位
     *
     * @param decimal
     */
    public static String transferDisplay(double decimal) {
        return transferDisplay(decimal + MessageConstants.Empty);
    }

    /**
     * chart用于显示数值后面保留多少位小数
     *
     * @param decimal
     */
    public static String transferDisplay(int newScale, String decimal, String pattern) {
        if (StringTool.isEmpty(decimal)) {
            decimal = "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        BigDecimal bigDecimal = new BigDecimal(decimal).setScale(newScale, RoundingMode.FLOOR);

        return decimalFormat.format(bigDecimal);
    }

    /**
     * 轉換成小數只顯示八位
     *
     * @param decimal
     */
    public static String transferStoreDatabase(int newScale, String decimal, String pattern) {

        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        BigDecimal bigDecimal = new BigDecimal(decimal).setScale(newScale, RoundingMode.FLOOR);

        return decimalFormat.format(bigDecimal);
    }


    public static void main(String[] args) {
        System.out.println(DecimalTool.calculateFirstSubtractSecondValue("1,999.99999999", "1", true));

//        System.out.println(transferDisplay("99999.10006080"));
        System.out.println(getStringReplaceComma("9,9999.10006080"));
//        System.out.println(transferStoreDatabase("9,9999.10006080"));

//		DecimalFormat decimalFormat = new DecimalFormat("0.00000000");
//
//		BigDecimal bigDecimal = new BigDecimal("99999.10006080").setScale(8, RoundingMode.FLOOR);
//		System.out.println(decimalFormat.format(bigDecimal));

//		BigDecimal bigDecimalCirculation = new BigDecimal("300000000000.0000000168").setScale(8, RoundingMode.FLOOR);
//		BigDecimal bigDecimalCoinBase = new BigDecimal("200000000000.0000000168").setScale(8, RoundingMode.FLOOR);
//		
//		if(bigDecimalCirculation.compareTo(bigDecimalCoinBase) == 1) {
//			System.out.println(bigDecimalCirculation.compareTo(bigDecimalCoinBase));
//		} else if(bigDecimalCirculation.compareTo(bigDecimalCoinBase) == -1) {
//			System.out.println(bigDecimalCirculation.compareTo(bigDecimalCoinBase));
//		} else if(bigDecimalCirculation.compareTo(bigDecimalCoinBase) == 0) {
//			System.out.println(bigDecimalCirculation.compareTo(bigDecimalCoinBase));
//		}

//		String num = decimalFormat.format(bigDecimalCirculation.subtract(bigDecimalCoinBase));
//
//		System.out.println(num);

//		BigDecimal bigDecimalNum = new BigDecimal(num).setScale(8, RoundingMode.FLOOR);
//		BigDecimal bigDecimal2 = new BigDecimal("10000000000.00000002").setScale(8, RoundingMode.FLOOR);
//
//		System.out.println(bigDecimalNum.subtract(bigDecimal2));

//		BigDecimal scaled = bigDecimalCirculation.setScale(8, RoundingMode.FLOOR);
//		System.out.println(bigDecimalCirculation + " -> " + scaled);
//
//		BigDecimal rounded = bigDecimalCoinBase.round(new MathContext(3, RoundingMode.FLOOR));
//		System.out.println(bigDecimalCoinBase + " -> " + rounded);

    }

    /**
     * 第一個參數 + 第二個參數 ＝ 回傳的數值 <br>
     *
     * @param firstValue
     * @param secondValue
     */
    public static String calculateFirstAddSecondValue(String firstValue, String secondValue) {
        if (StringTool.isEmpty(firstValue)) {
            return secondValue;
        }
        if (StringTool.isEmpty(secondValue)) {
            return firstValue;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.0000000000");

        // 計算小數八位，第九位無條件捨去
        BigDecimal bigDecimalFirstValue = new BigDecimal(firstValue).setScale(10, RoundingMode.FLOOR);
        BigDecimal bigDecimalSecondValue = new BigDecimal(secondValue).setScale(10, RoundingMode.FLOOR);

        // 任一value不能小於0
        if (bigDecimalFirstValue.compareTo(BigDecimal.ZERO) == -1
                || bigDecimalSecondValue.compareTo(BigDecimal.ZERO) == -1) {
            return MessageConstants.AMOUNT_EXCEPTION_CODE;
        }

        BigDecimal bigDecimalNum = bigDecimalFirstValue.add(bigDecimalSecondValue);

        String num = decimalFormat.format(bigDecimalNum);

        return num;
    }

    /**
     * 比較第一個值是否等於第二個值
     *
     * @param firstValue
     * @param secondValue
     * @return
     * @throws Exception
     */
    public static boolean compareFirstEqualSecondValue(String firstValue, String secondValue) throws Exception {
        // 計算小數八位，第九位無條件捨去
        BigDecimal bigDecimalFirstValue = new BigDecimal(firstValue).setScale(10, RoundingMode.FLOOR);
        BigDecimal bigDecimalSecondValue = new BigDecimal(secondValue).setScale(10, RoundingMode.FLOOR);

        if (bigDecimalFirstValue.compareTo(bigDecimalSecondValue) == 0) {
            return true;
        }
        return false;
    }

}
