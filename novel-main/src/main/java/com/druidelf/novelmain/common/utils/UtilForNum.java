package com.druidelf.novelmain.common.utils;

public class UtilForNum {
    private static int defaultDigit = 6;

    /**
     * 产生默认给定值的随机数
     * @return
     */
    public static int getRandom(){
        return getRandom( defaultDigit );
    }
    /**
     * 指定位数生成随机数
     * @param digit
     * @return
     */
    public static int getRandom ( int digit ) {
        if ( digit >= 10 ) return 0;
        int j = 1;
        for (int i = 1 ; i < digit; i++ ) {
            j = j*10;
        }
        return (int)((Math.random()*9+1)*j);
    }
}
