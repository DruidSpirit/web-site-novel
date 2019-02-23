package com.druidelf.novelstaticresource.enums.otherType;

public class ParametersOfTheRules {
    /**
     * 用户账号正则表达式规则
     */
    public static final String USER_NAME_RULES = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]{6,10}$";
    public static final String USER_NAME_RULES_EXPLAIN = "请输入6到10位有效字符，只能是中文、数字或者英文字母";
    /**
     * 用户密码正则表达式规则
     */
    public static final String USER_PASSWORD_RULES = "[a-zA-Z0-9]{6,12}$";
    public static final String USER_PASSWORD_RULES_EXPLAIN = "请输入6到12位有效字符，只能是数字或者英文字母";
    /**
     * 邮箱正则表达式
     */
    public static final String USER_Email_RULES = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
    public static final String USER_Email_RULES_EXPLAIN = "邮箱格式不正确";
    /**
     * 验证码正则表达式
     */
    public static final String  USER_VERIFYCODE_RULES = "[0-9]{6}$";
    public static final String USER_VERIFYCODE_RULES_EXPLAIN = "验证码格式不正确";
}
