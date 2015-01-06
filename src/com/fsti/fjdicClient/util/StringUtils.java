package com.fsti.fjdicClient.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

public class StringUtils {

    private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    /**
     * 判断给定字符串是否空白串�?空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     * 
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || TextUtils.isEmpty(input.trim()) || "null".equals(input))
            return true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 字符串转整数
     */
    // public static int toInt(String str, int defValue) {
    // try {
    // return Tools.parseInt(str);
    // } catch (Exception e) {
    // }
    // return defValue;
    // }

    /**
     * 对象转整�?
     * 
     * @param obj
     * @return 转换异常返回 0
     */
    // public static int toInt(Object obj) {
    // if (obj == null)
    // return 0;
    // return toInt(obj.toString(), 0);
    // }

    /**
     * 对象转整�?
     * 
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔�?
     * 
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    public static String telToXing(String tel) {
        StringBuilder sb = new StringBuilder();
        sb.append(tel.substring(0, 3));
        for (int i = 4; i < tel.length() - 3; i++) {
            sb.append("*");
        }
        sb.append(tel.substring(7, tel.length()));
        return sb.toString();
    }

    public static String getContent(String normal) {
        if (!isEmpty(normal))
            return normal;
        return "";
    }

    public static int getIntContent(String normal) {
        if (!isEmpty(normal)) {
            try {
                return NumberUtil.parseInt(normal);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public static String getPatiantName(String normal) {
        if (!isEmpty(normal))
            return normal;
        return "匿名";
    }

    /**
     * @功能描述: TODO 电话号码显示：139****130
     * @修改: qius
     */
    public static String getPhoneReplacex(String phone) {
        if (!Pattern.matches("\\d{11}", phone)) {
            return phone;
        }
        char[] ch = phone.toCharArray();
        for (int i = 3; i < ch.length - 3; i++) {
            ch[i] = '*';
        }
        return new String(ch);
    }

    public static String getNameReplacex(String name) {
        if (isEmpty(name)) {
            return "";
        }
        char[] ch = name.toCharArray();
        for (int i = 1; i < ch.length; i++) {
            ch[i] = '*';
        }
        return new String(ch);
    }

    /**
     * @功能描述: TODO 判断是否手机号码 :^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * @功能描述: TODO 2~0个中文字
     * @修改: Q
     */
    public static boolean checkRealName(String str) {
        String regEx = "[\u4e00-\u9fa5]{2,10}";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * @功能描述: TODO 判断是否车牌
     */
    public static boolean isCarNO(String mobiles) {
        Pattern p = Pattern.compile("^[\u4e00-\u9fa5|WJ]{1}[A-Z0-9]{6}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean formatPwd(String pwd) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]{6,18}$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    // 校验Tag Alias 只能是数�?英文字母和中�?
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean formatcjh(String pwd) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]{16,17}$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    public static String pwdEnpt(String pwd) {
        StringBuffer sbuffer = new StringBuffer(pwd);
        sbuffer.reverse();
        return getStringToSha256(sbuffer.toString() + pwd + pwd.substring(0, 3));
    }

    /***
     * MD5加码 生成32位md5�?
     */
    public static String string2MD5(String inStr) {
        if (isEmpty(inStr)) {
            // Log.w("Tools", "string2MD5: String is null or  is empty!");
            return null;
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            // System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static String getStringToSha256(String text) {
        if (isEmpty(text)) {
            // Log.w("Tools", "getStringToSha256: String is null or  is empty!");
            return null;
        }
        MessageDigest md;
        String temp = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes("UTF-8"));
            temp = new String(encode(md.digest()));
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // try {
        // md = MessageDigest.getInstance("SHA-256");
        // md.update(text.getBytes("UTF-8"));
        // temp = bytes2Hex(md.digest()); // to HexString
        // return temp;
        // } catch (NoSuchAlgorithmException e) {
        // e.printStackTrace();
        // } catch (UnsupportedEncodingException e) {
        // e.printStackTrace();
        // }
        return "";

    }

    public static char[] encode(byte[] data) {

        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return out;
    }

    public static String urlEcode(String endname) {

        try {
            return URLEncoder.encode(endname, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String getCarReplacex(String car) {
        if (isEmpty(car))
            return "";
        char[] ch = car.toCharArray();
        for (int i = 2; i < ch.length - 1; i++) {
            ch[i] = '*';
        }
        return new String(ch);
    }

    /**
     * @功能描述: TODO 车型名称友好显示： 奥迪· A3
     * @修改: qius
     */
    public static String getCarTypeName(String wholeName) {
        if (isEmpty(wholeName))
            return "";
        if (!wholeName.contains(","))
            return wholeName;
        return wholeName.split(",")[0] + " · " + wholeName.split(",")[1];
    }

}
