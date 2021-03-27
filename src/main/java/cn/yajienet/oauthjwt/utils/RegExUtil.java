package cn.yajienet.oauthjwt.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-03-27 23:00
 */
public class RegExUtil {

    public static boolean isEmail(String s) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return regex(check, s);
    }

    public static boolean isPhone(String s) {
        String check = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
        return regex(check, s);
    }

    private static boolean regex(String regex, String s) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

}
