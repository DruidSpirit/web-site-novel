package com.druidelf.novelbackstagemanagement.common.utils.UtilForNet;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UtilForSplittingChaptersTest {

    @Test
    public void saveAndSplittingChapters() {
        String basePath = "D:\\IT_Study\\idea\\resource\\testNovel\\";
        String txtBase = "RepositoryNovel2\\\\穿越小说\\\\七十年代学霸\\txtMain\\七十年代学霸.txt";
        String txtBase2 ="RepositoryNovel1\\\\武侠小说\\\\《太平客栈》全集下载\\txtMain\\太平客栈.txt";
        String regexRule = "^[0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s|章|卷]{1}[\\s|：].*$";
        String regexRule2 = "^[0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s|章|卷]{1}.*$";
        try {
            UtilForSplittingChapters.saveAndSplittingChapters(basePath+txtBase,regexRule,regexRule2);
        } catch (IOException e) {
            System.out.println("IO流读写异常");
            e.printStackTrace();
        }
    }

    @Test
    public void saveAndSplittingChapters1() {
    }

    @Test
    public void writeJosnFileToLocal() {
    }
}