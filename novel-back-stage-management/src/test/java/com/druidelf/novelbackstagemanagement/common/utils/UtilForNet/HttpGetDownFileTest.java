package com.druidelf.novelbackstagemanagement.common.utils.UtilForNet;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class HttpGetDownFileTest {

    @Test
    public void filterLinkAndDownloadAndSave() throws IOException {
        String name = HttpGetDownFile.filterLinkAndDownloadAndSave(
                "https://www.sjxs.la/files/article/image/84/84650/84650s.jpg",
                "D:\\IT_Study\\idea\\resource\\testNovel\\RepositoryNovel1\\武侠小说\\1《永恒国度》全集下载\\img\\",
                false
        );
        System.out.println(name);
    }
}