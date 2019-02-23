package com.druidelf.novelbackstagemanagement.service.threadTaskService;

import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.ConnectionPoolSetting;
import com.druidelf.novelbackstagemanagement.response.CrawlerElementDo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class DealChapterDetailServiceTest {

    @Test
    public void call() {
        String test = "";
        ArrayList<Future<CrawlerElementDo>> futures = new ArrayList<Future<CrawlerElementDo>>();
        for (int i = 0; i < 10; i++) {
            Future<CrawlerElementDo> f =  ConnectionPoolSetting.executorService.submit(new ThreadTestServive(
                    CrawlerElementDo.builder()
                            .Name(i+"")
                            .build()
            ));
            futures.add(f);
        }
        System.out.println("获取结果中...");
        for (Future<CrawlerElementDo> f : futures) {
            try {
                 //if(f.isDone()) {
                     test += f.get().getName();
                     System.out.println(f.get().getName());
                // }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ConnectionPoolSetting.executorService.shutdown();
        System.out.println("得到结果."+test);

        // 关闭线程池。


    }
}