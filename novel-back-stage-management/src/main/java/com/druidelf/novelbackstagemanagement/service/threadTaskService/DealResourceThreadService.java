package com.druidelf.novelbackstagemanagement.service.threadTaskService;

import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.UtilForSplittingChapters;
import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.Callable;

import static com.druidelf.novelbackstagemanagement.enums.otherType.SystemParamEnum.SYSTEM_PARAM_ROOT_PATH_FOR_LOCAL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealResourceThreadService implements Callable<DruidNovelResource> {
    /**
     * 被读取文件路径
     */
    private DruidNovelResource druidNovelResource;
    private String[] regexRules;
    private boolean isRegexRuleCompare = true;
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public DruidNovelResource call() throws Exception {
        if (druidNovelResource!=null){
                druidNovelResource.setChapterRepositoryPath(
                        UtilForSplittingChapters.saveAndSplittingChapters(
                                SYSTEM_PARAM_ROOT_PATH_FOR_LOCAL.Param + druidNovelResource.getRepositoryPath()
                                ,isRegexRuleCompare
                                , regexRules
                        )
                );
        }
        return druidNovelResource;
    }
}
