package com.fsti.fjdicClient.util;

import java.text.DecimalFormat;

/**
 * @描述: TODO 数值工具类
 */
public class NumberUtil {
    /**
     * @功能描述: 分转 元保留两位小数
     * @修改: Q
     */
    public static String FengToYuan(String dou_str, int number) {
        double chargeAmount = 0;
        try {
            chargeAmount = Double.valueOf(dou_str.trim()) / 100;
        } catch (Exception e) {
        }
        String format = "%.0f";
        switch (number) {
        case 0:
            format = "%.0f";
            break;
        case 1:
            format = "%.1f";
            break;
        case 2:
            format = "%.2f";
            break;
        default:
            break;
        }
        return StringUtils.getContent(String.format(format, chargeAmount));
    }

    public static String getDistance(double value, int number) {
        double distance = 0;
        if (value >= 1000) {
            distance = value / 1000d;
            return DoubleToStr(distance, number) + "km";
        }
        return DoubleToStr(value, number) + "m";
    }

    public static String getDist(double value, int number) {
        double distance = 0;
        distance = value / 1000d;
        return DoubleToStr(distance, number);
    }

    public static String DoubleToStr(double value, int number) {
        String format = "%.0f";
        switch (number) {
        case 0:
            format = "%.0f";
            break;
        case 1:
            format = "%.1f";
            break;
        case 2:
            format = "%.2f";
            break;
        default:
            break;
        }
        return StringUtils.getContent(String.format(format, value));
    }

    public static String DoubleToFoolStr(double value, int number) {
        double tgls = Math.floor(value);
        return DoubleToStr(tgls, number);
    }

    /**
     * @功能描述: TODO 将数值保留小数点后两位
     * @修改: qius
     */
    public static String get2decimal(double d) {
        return new DecimalFormat("0.00").format(d);
    }

    //
    public static double getLiBei(int m) {
        if (m <= 3000) {// 小于3000米
            return 4.0;
        }
        double ceil = Math.ceil((m - 3000) / 1000.0 * 1);
        return (4.0 + ceil);
    }

    public static double getLiBeiLong(int m) {
        double ceil = Math.ceil((m * 0.35) / 1000.0 * 1);
        return (ceil);
    }

    public static int getPrice2Kilometre(double kilometre) {
        double tgls = Math.ceil(kilometre);
        if (tgls > 3)
            return (int) (4 + (tgls - 3));
        return 4;
    }

    public static int getPrice2KilometreSj(double kilometre) {
        double tgls = Math.floor(kilometre);
        if (tgls > 3)
            return (int) (4 + (tgls - 3));
        return 4;
    }

    public static String getqy(double m) {
        return DoubleToStr(m / 8.85, 1);
    }

    public static float getRating(int credit) {
        return credit / 100f * 3f < 0.5f ? 0.5f : credit / 100f * 3f;
    }

    /**
     * 字符串强转整形，避免出现将null等非法字段强转出现报错
     */
    public static int parseInt(String iString) {
        if (iString == null || "".equals(iString) || "null".equals(iString))
            return 0;
        try {
            return Integer.parseInt(iString);
        } catch (Exception e) {
            return 0;
        }
    }
}
