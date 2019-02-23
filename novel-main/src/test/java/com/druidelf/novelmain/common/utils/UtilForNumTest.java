package com.druidelf.novelmain.common.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilForNumTest {

    @Test
    public void getRandom() {
        for (int i = 0 ;i<500;i++){
            System.out.println(UtilForNum.getRandom());
        }

    }

    @Test
    public void getRandom1() {
    }
}