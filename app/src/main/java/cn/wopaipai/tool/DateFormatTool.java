package cn.wopaipai.tool;

import android.annotation.SuppressLint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import cn.wopaipai.R;
import cn.wopaipai.base.BaseApplication;
import cn.wopaipai.base.BaseException;
import cn.wopaipai.constant.Constants;
import cn.wopaipai.constant.MessageConstants;

/**
 * @author Costa Peng
 * @version 1.0.0
 * @since 2018/01/01
 * 工具類：日期格式轉換
 */

public class DateFormatTool {
    @SuppressWarnings("unused")
    private static final String TAG = "DateFormatTool";

    private final static String DATETIMEFORMAT_TZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    // private final static String DATETIMEFORMAT_TZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    // private final static String DATETIMEFORMAT_TZ = "MM/dd/yyyy KK:mm:ss a Z";

    private final static String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String DATETIMEFORMAT = "HH:mm:ss";

    private final static String DATETIMEFORMAT_AMPM = "yyyy/MM/dd hh:mm aa";
    private final static String DATETIMEFORMAT_HMS = "yyyy/MM/dd HH:mm:ss";

    // Greenwich Mean Time
    private final static String TIMEZONE_GMT = "GMT";
    // Coordinated Universal Time
    private final static String TIMEZONE_UTC = "UTC";

    // UTC -> Current TimeZone 更改日期時區
    public static String dateFormat(String strDate) throws Exception {

        SimpleDateFormat simpleDateFormat_GMT = new SimpleDateFormat(DATETIMEFORMAT_TZ);
        simpleDateFormat_GMT.setTimeZone(TimeZone.getTimeZone(TIMEZONE_GMT));
        Date date = simpleDateFormat_GMT.parse(strDate);

        SimpleDateFormat simpleDateFormat_SystemDefault = new SimpleDateFormat(DATETIMEFORMAT_TZ);
        simpleDateFormat_SystemDefault.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
        strDate = simpleDateFormat_SystemDefault.format(date);

        strDate = strDate.substring(0, strDate.indexOf("T")) + " "
                + strDate.substring(strDate.indexOf("T") + 1, strDate.lastIndexOf(":"));

        return strDate;
    }

    // Current TimeZone -> UTC 更改日期時區
    public static String dateFormatUTC(Date date) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIMEFORMAT_TZ);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));

        String strUTCDate = simpleDateFormat.format(date);
        System.out.println(strUTCDate);

        return strUTCDate;
    }

    // Current TimeZone -> UTC String to Date
    public static Date stringFormatUTCDate(String dateString) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIMEFORMAT_TZ);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));

        Date dateUTC = simpleDateFormat.parse(dateString);

        return dateUTC;
    }

    // 取出日期(年,月,日)
    public static Calendar getCalendar(String strDate) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIMEFORMAT_TZ);
        Date date = simpleDateFormat.parse(strDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }

    /**
     * 取得使用者當下的時區時間, 時 & 分
     *
     * @throws Exception
     */
    public static String getCurrentDate() throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
        Date date = new Date();
        String strDate = simpleDateFormat.format(date);

        strDate = strDate.substring(0, strDate.lastIndexOf(":"));

        return strDate;
    }

    public static long getTimeParseString(String time) {

        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));

            return simpleDateFormat.parse(time).getTime();
        } catch (ParseException e) {
            BaseException.INSTANCE.print(e);
        }
        return 0l;
    }

    /**
     * 取得使用者當下的時區時間, 時 & 分
     *
     * @throws Exception
     */
    public static String getConvertTime(String time) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
        SimpleDateFormat simpleDateFormatConvert = new SimpleDateFormat(DATETIMEFORMAT);
        String strDate = simpleDateFormatConvert.format(simpleDateFormat.parse(time).getTime());

//        strDate = strDate.substring(0, strDate.lastIndexOf(":"));

        return strDate;
    }

    //取得当前时间
    public static String getCurrentTime() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIMEFORMAT);
        Date date = new Date();
        String strDate = simpleDateFormat.format(date);

        strDate = strDate.substring(0, strDate.lastIndexOf(":"));

        return strDate;
    }

    /**
     * TZ Date format convert to General format
     *
     * @throws Exception
     */
    public static String dateConvertTZFormat(String strDate) throws Exception {

        strDate = strDate.substring(0, strDate.indexOf("T")) + " "
                + strDate.substring(strDate.indexOf("T") + 1, strDate.indexOf("Z"));

        return strDate;
    }

    /**
     * Get UTC TimeStamp
     *
     * @throws Exception
     */
    public static String getUTCTimeStamp() throws Exception {
//		Instant instant = Instant.now();
//		long timeStampMillis = instant.toEpochMilli();
        return String.valueOf(new Date().getTime());
    }

    /**
     * Get UTC Date for AM & PM
     *
     * @throws Exception
     * @format TimeMillis
     */
    public static String getUTCDateForAMPMFormat(String timeStamp) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIMEFORMAT_AMPM);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));
        Date date = new Date();
        date.setTime(Long.valueOf(timeStamp));
        String dataAMPM = simpleDateFormat.format(date);
        return dataAMPM;
    }

    /**
     * Get UTC Date transfer Current TimeZone
     *
     * @throws Exception
     * @format TimeMillis
     */
    public static String getUTCDateTransferCurrentTimeZone(String timeStamp) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIMEFORMAT_AMPM);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
        Date date = new Date();
        date.setTime(Long.valueOf(timeStamp));
        String dataAMPM = simpleDateFormat.format(date);

        return dataAMPM;
    }

    /**
     * Get UTC Date transfer Current TimeZone
     * return hh:mm:ss
     *
     * @throws Exception
     * @format TimeMillis
     */
    public static String getUTCDateTransferCurrentTimeZoneHMS(String timeStamp) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIMEFORMAT_HMS);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
        Date date = new Date();
        date.setTime(Long.valueOf(timeStamp));
        return simpleDateFormat.format(date);

    }

    public static String getCurrentTimeOfAMPM(String timeStamp) {
        if (StringTool.isEmpty(timeStamp)) {
            return "";
        }
        try {
            String currentTime = getUTCDateTransferCurrentTimeZone(timeStamp);
            String dateOfAMPM = currentTime.replace(Constants.ValueMaps.MORNING, Constants.ValueMaps.AM)
                    .replace(Constants.ValueMaps.AFTERROON, Constants.ValueMaps.PM).toUpperCase();
            return dateOfAMPM;
        } catch (Exception e) {
            BaseException.INSTANCE.print(e);
        }
        return "";
    }


    @NotNull
    public static long getCountDownTime(@Nullable String timeParseString, @Nullable String timeParseString1) {
        return (getTimeParseString(timeParseString) - getTimeParseString(timeParseString1)) / 1000;
    }

    public static String getDisplayCountDownTime(String day, long date) {
        long days = date / (60 * 60 * 24);
        long hours = (date - days * (60 * 60 * 24)) / (60 * 60);
        long minutes = (date - days * (60 * 60 * 24) - hours * (60 * 60)) / 60;
        long second = date - days * (60 * 60 * 24) - hours * (60 * 60) - minutes * (60);
        String strHours = hours < 10 ? "0" + hours : "" + hours;
        String strMinute = minutes < 10 ? "0" + minutes : "" + minutes;
        String strSecond = second < 10 ? "0" + second : "" + second;
        if (days == 0) {
            return strHours + ":" + strMinute + ":" + strSecond;
        } else {
            return days + day + MessageConstants.Space + strHours + ":" + strMinute + ":" + strSecond;
        }
    }

}
