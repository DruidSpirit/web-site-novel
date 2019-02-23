package com.druidelf.novelmain.common.utils;

import org.junit.Test;

import java.io.File;
import java.io.IOException;


public class UtilForPictureVerifyCodeTest {

    @Test
    public void outputImage() throws IOException {
        File dir = new File("C:/Users/wang/Desktop/笔记");
        int w = 200, h = 80;
        String verifyCode = UtilForPictureVerifyCode.generateVerifyCode(4);
        File file = new File(dir, verifyCode + ".jpg");
         UtilForPictureVerifyCode.outputImage(w, h, file, verifyCode);
    }
}