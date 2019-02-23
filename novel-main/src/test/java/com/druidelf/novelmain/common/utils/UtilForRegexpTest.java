package com.druidelf.novelmain.common.utils;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class UtilForRegexpTest {

    @Test
    public void getRegexpResult() {
//        boolean jduge = UtilForRegexp.getRegexpResult("^[0-9|第|一|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[章|卷]{1}","第1章 绝世仙厨");
//        System.out.println( jduge );
        String str = "第1章 绝世仙厨";
        String pattern = "^[0-9|第|一|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[章|卷]{1}.*";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.matches());
    }
}