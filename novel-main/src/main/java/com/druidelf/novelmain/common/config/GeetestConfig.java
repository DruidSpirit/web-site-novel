package com.druidelf.novelmain.common.config;

public class GeetestConfig {
    // 填入自己的captcha_id和private_key
    //19a8f6ff387dda5caf99676de5053af7  6b2c13b333f7ae39dab6630379bb6f48
    //29a5138459b5f55f7ce92992b5efb063  647ea1a0917e230c236cdcdae2a16ff2
    private static final String geetest_id = "29a5138459b5f55f7ce92992b5efb063";
    private static final String geetest_key = "647ea1a0917e230c236cdcdae2a16ff2";
    private static final boolean newfailback = true;

    public static final String getGeetest_id() {
        return geetest_id;
    }

    public static final String getGeetest_key() {
        return geetest_key;
    }

    public static final boolean isnewfailback() {
        return newfailback;
    }
}
