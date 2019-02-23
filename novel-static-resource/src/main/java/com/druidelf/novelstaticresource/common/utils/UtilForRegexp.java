package com.druidelf.novelstaticresource.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.druidelf.novelstaticresource.enums.otherType.ParametersOfTheRules.*;

public class UtilForRegexp {

    /**
     * 正则表达式校验工具
     * @param rule
     * @param value
     * @return
     */
    public static boolean getRegexpResult ( String rule , String value ) {
        if ( rule == null || value == null ) return false;
        Pattern r = Pattern.compile(rule);
        Matcher m = r.matcher(value);
        return m.matches();
    }
    public static boolean getRegexpResult ( String rule , Integer value ) {
        if ( rule == null || value == null ) return false;
        Pattern r = Pattern.compile(rule);
        Matcher m = r.matcher(value.toString());
        return m.matches();
    }

    /**
     * 邮箱正则表达式校验
     * @param value
     * @return
     */
    public static boolean verifyEmailByRegexp ( String value ) {

        return getRegexpResult( USER_Email_RULES,value );
    }

    /**
     * 用户名正则表达式校验
     * @param value
     * @return
     */
    public static boolean verifyUsernameByRegexp ( String value ) {
        return getRegexpResult( USER_NAME_RULES, value );
    }

    /**
     * 验证码正则表达式校验
     * @param value
     * @return
     */
    public static boolean verifyCodeByRegexp ( Integer value ) {
        return getRegexpResult( USER_VERIFYCODE_RULES, value );
    }
}
