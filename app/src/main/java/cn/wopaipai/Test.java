package cn.wopaipai;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-21 17:47
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.wopaipai.tool.decimal.DecimalTool;

import static cn.wopaipai.tool.regex.RegexTool.PASSWORD;

public class Test {

    public static void main(String[] args) {
//        System.out.println(isRightPassword("999999999q"));
//        System.out.println(isRightPassword("999999999q*"));
//        System.out.println(isRightPassword("999999999*"));
//        System.out.println(isRightPassword("999999999"));
//        System.out.println(isRightPassword("99999*"));
        System.out.println(DecimalTool.calculateFirstSubtractSecondValue("0","1",true));

    }

    public static boolean isRightPassword(String version) {

        Pattern pattern = getPattern(PASSWORD);

        Matcher matcher = pattern.matcher(version);

        return matcher.matches();
    }

    private static Pattern getPattern(String regex) {

        Pattern pattern = Pattern.compile(regex);

        return pattern;
    }


}
