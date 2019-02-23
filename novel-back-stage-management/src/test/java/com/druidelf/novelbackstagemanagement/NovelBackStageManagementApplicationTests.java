package com.druidelf.novelbackstagemanagement;

import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import com.druidelf.novelbackstagemanagement.service.DruidNovelResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NovelBackStageManagementApplicationTests {

    @Autowired
    private DruidNovelResourceService druidNovelResourceService;
    @Test
    public void contextLoads() {
        DruidNovelResource druidNovelResource = DruidNovelResource.builder()
                .id(1)
                .repositoryPath("测试")
                .srcRepositoryPath("图片路径测试")
                .hasLoaddown(true)
                .srcHasLoaddown(true)
                .build();
        if (druidNovelResourceService.updateNotNull(druidNovelResource)>0){
            System.out.println("修改成功!");
        }else {
            System.out.println("修改失败!");
        }
    }

}

