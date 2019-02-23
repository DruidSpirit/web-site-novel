package com.druidelf.novelbackstagemanagement.service.threadTaskService;

public class OtherTest {
    public static void main(String[] args) {
        String basePath2 = "D:\\IT_Study\\idea\\resource\\testNovel\\";
        String txtBase = "RepositoryNovel2\\\\穿越小说\\\\七十年代学霸\\txtMain\\七十年代学霸.txt";
        String txtBase2 ="RepositoryNovel1\\\\武侠小说\\\\《太平客栈》全集下载\\txtMain\\太平客栈.txt";
        String regexRule = "^[0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s|章|卷]{1}[\\s|：].*$";
        String regexRule2 = "^[0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s|章|卷]{1}.*$";
        String repositoryPath = basePath2+txtBase2;
//        String basePath = "";
//        String temArray[] = repositoryPath.split("\\\\");
//        for ( int i = 0;i< temArray.length-3;i++ ) {
//            basePath += temArray[i]+"\\";
//        }
        repositoryPath = repositoryPath.replace("D:\\IT_Study\\idea\\resource\\testNovel\\","\\");
        System.out.println(repositoryPath);
    }
}
