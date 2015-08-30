package com.veaer.gank.util;

import android.util.Patterns;

/**
 * Created by Veaer on 5/20/15.
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        return (str == null || str.trim().isEmpty());
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isDateEmpty(String str) {
        return isEmpty(str) || str.equals("0000-00-00");
    }

    public static boolean isValidEmail(String str) {
        return Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }

    public static boolean isValidPhone(String str) {
        return Patterns.PHONE.matcher(str).matches();
    }

    public static String arg2String(Object... args) {
        StringBuilder param = new StringBuilder("");
        int argCount = 0;
        for(Object arg : args) {
            param.append(arg.toString());
            argCount++;
            if(argCount < args.length) {
                param.append(",");
            }
        }
        return param.toString();
    }
}
