package com.druidelf.novelbackstagemanagement.enums.otherType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SystemParamEnum {

    SYSTEM_PARAM_ROOT_PATH_FOR_LOCAL("D:\\IT_Study\\idea\\resource\\testNovel\\","文件读取写入磁盘根目录"),
    SYSTEM_PARAM_NOVEL_TXTMAIN_FOLDER("txtMain","存放小说txt文本的文件夹名称"),
    SYSTEM_PARAM_NOVEL_IMG_FOLDER("img","存放小说图片的文件夹名称");

    public String Param;
    public String name;
}
