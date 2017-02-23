package com.core.op.lib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/3/10
 */
public class Verify {

    /**
     * 验证是否是纯数字
     * @param mobiles
     * @return
     */
    public static boolean isNumble(String mobiles) {
        Pattern p = Pattern.compile("[0-9]{1,}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证是否是手机号
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证密码
     * @param mobiles
     * @return
     */
    public static boolean isPassword(String mobiles) {
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
