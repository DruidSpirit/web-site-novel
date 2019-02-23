package com.druidelf.novelbackstagemanagement.service.threadTaskService;

import com.druidelf.novelbackstagemanagement.response.CrawlerElementDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.Callable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadTestServive  implements Callable<CrawlerElementDo> {
    private CrawlerElementDo crawlerElementDo;
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */

    @Override
    public CrawlerElementDo call() throws Exception {
        System.out.println(crawlerElementDo.getName());
        Thread.sleep(2000);
        return this.crawlerElementDo;
    }
}
