package com.druidelf.novelbackstagemanagement.service.impl;

import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.response.vm.SplittingChaptersVm;
import com.druidelf.novelbackstagemanagement.service.DealResourceService;
import com.druidelf.novelbackstagemanagement.service.DruidNovelResourceService;
import com.druidelf.novelbackstagemanagement.service.threadTaskService.DealResourceThreadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Log4j2
@Service
public class DealResourceServiceImpl implements DealResourceService {

    @Autowired
    private DruidNovelResourceService druidNovelResourceService;

    /**
     * 小说章节分拆
     * @param druidNovelResourceList 分拆小说集合
     * @param threadCount 线程条数
     * @param isRegexRuleCompare 是否开启规则优先级比较
     * @param regexRules 爬取规则正则表达式
     * @return ResponseData
     */
    @Override
    public ResponseData saveAndSplittingChapters(List<DruidNovelResource> druidNovelResourceList, Integer threadCount,boolean isRegexRuleCompare,String...regexRules ) {
        Long startTime = System.currentTimeMillis();
        // 建立信息返回体
        SplittingChaptersVm splittingChaptersVm = SplittingChaptersVm.builder().build();
        List<SplittingChaptersVm.StatusModel> statusModelList = new ArrayList<>();

        // 建立线程池
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        // 多线程回调
        ArrayList<Future<DruidNovelResource>> futures = new ArrayList<>();
        // 装拆分成功实体类的容器
        List<DruidNovelResource> druidNovelResourceList1 = new ArrayList<>();
        // 装拆分失败实体类的容器
        List<DruidNovelResource> druidNovelResourceList2 = new ArrayList<>();
        // 开启多线程
        for ( DruidNovelResource druidNovelResource: druidNovelResourceList ) {
            Future<DruidNovelResource> f = executorService.submit(
                            DealResourceThreadService.builder()
                            .druidNovelResource(druidNovelResource)
                            .isRegexRuleCompare(isRegexRuleCompare)
                            .regexRules(regexRules)
                            .build()
            );
            futures.add(f);
        }
        // 得到线程的返回结果
        for (Future<DruidNovelResource> f : futures) {
            try {
                if (f.get().getChapterRepositoryPath()!=null){
                    statusModelList.add(SplittingChaptersVm.StatusModel.builder().status(true).druidNovelResource(f.get()).build());
                    druidNovelResourceList1.add(f.get());
                }else {
                    statusModelList.add(SplittingChaptersVm.StatusModel.builder().druidNovelResource(f.get()).build());
                    druidNovelResourceList2.add(f.get());
                }
            } catch (Exception e) {
                log.error("分拆章节的线程发生异常",e);
            }
        }
        // 修改数据库章节储存位置信息
        for ( DruidNovelResource druidNovelResource : druidNovelResourceList1 ) {
            druidNovelResourceService.updateNotNull(
                    DruidNovelResource.builder()
                            .id( druidNovelResource.getId() )
                            .chapterRepositoryPath( druidNovelResource.getChapterRepositoryPath() )
                    .build()
            );
        }
        splittingChaptersVm.setSpendTime(System.currentTimeMillis()-startTime);
        splittingChaptersVm.setErrorDruidNovelResourceList(druidNovelResourceList2);
        splittingChaptersVm.setStatusModelList(statusModelList);
        splittingChaptersVm.setSuccessTotal(druidNovelResourceList1.size());
        return ResponseData.SUCCESS(splittingChaptersVm);
    }
}