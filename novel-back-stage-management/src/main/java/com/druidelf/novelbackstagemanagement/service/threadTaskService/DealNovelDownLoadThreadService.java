package com.druidelf.novelbackstagemanagement.service.threadTaskService;

import com.druidelf.novelbackstagemanagement.common.exception.MyException;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.HttpGetDownFile;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForString;
import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import com.druidelf.novelbackstagemanagement.response.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.concurrent.Callable;

import static com.druidelf.novelbackstagemanagement.enums.otherType.SystemParamEnum.SYSTEM_PARAM_NOVEL_IMG_FOLDER;
import static com.druidelf.novelbackstagemanagement.enums.otherType.SystemParamEnum.SYSTEM_PARAM_NOVEL_TXTMAIN_FOLDER;
import static com.druidelf.novelbackstagemanagement.enums.otherType.SystemParamEnum.SYSTEM_PARAM_ROOT_PATH_FOR_LOCAL;
import static com.druidelf.novelbackstagemanagement.enums.responseType.ResponseDataEnums.RESPONSE_RESOURCE_LOADDOWN_FAIL;
import static com.druidelf.novelbackstagemanagement.enums.responseType.ResponseDataEnums.RESPONSE_SRC_LOADDOWN_FAIL;
import static com.druidelf.novelbackstagemanagement.enums.responseType.ResponseDataEnums.RESPONSE_TXT_LOADDOWN_FAIL;
@Data
@Log4j2
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealNovelDownLoadThreadService implements Callable<DruidNovelResource> {

    private DruidNovelResource druidNovelResource;
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public DruidNovelResource call() throws Exception {

        String commonPath = SYSTEM_PARAM_ROOT_PATH_FOR_LOCAL.Param + druidNovelResource.getRepositoryPath()+"\\"+UtilForString.fileNameRemoveSpecificSymbol(druidNovelResource.getName())+"\\";
        String result = null; // txt小说下载结果

        // text 小说下载
        if (!druidNovelResource.getHasLoaddown()) {

            String storeAddress = commonPath +SYSTEM_PARAM_NOVEL_TXTMAIN_FOLDER.Param+"\\";
            log.info("开始下载text：" + druidNovelResource.getName());

            // 开始下载TXT文本小说
            try {
                result = HttpGetDownFile.filterLinkAndDownloadAndSave(druidNovelResource.getLinkTxt(), storeAddress, false);
                // 下载成功后修改实体类数据并回传到主线程
                String getSuccessRepositoryPath = druidNovelResource.getRepositoryPath()+ UtilForString.fileNameRemoveSpecificSymbol(druidNovelResource.getName())+"\\"+SYSTEM_PARAM_NOVEL_TXTMAIN_FOLDER.Param+"\\"+ result;
                druidNovelResource.setHasLoaddown(true);
                druidNovelResource.setRepositoryPath(getSuccessRepositoryPath);
            } catch (IOException e) {
                log.error(RESPONSE_TXT_LOADDOWN_FAIL.name,e);
            }

        }

        // src图片下载
        if (!druidNovelResource.getSrcHasLoaddown()) {
            String storeAdress2 = commonPath+ SYSTEM_PARAM_NOVEL_IMG_FOLDER.Param+"\\";

            log.info("开始下载图片：" + druidNovelResource.getName());
            String result2 = null; // src图片下载结果

            // 开始下载小说对应的图片
            try {
                result2 = HttpGetDownFile.filterLinkAndDownloadAndSave(druidNovelResource.getLinkSrc(), storeAdress2, false);
                // 下载成功后修改实体类数据并回传到主线程
                String getSuccessRepositoryPath = druidNovelResource.getSrcRepositoryPath()+UtilForString.fileNameRemoveSpecificSymbol(druidNovelResource.getName())+"\\"+SYSTEM_PARAM_NOVEL_IMG_FOLDER.Param+"\\"+ result2;
                druidNovelResource.setSrcHasLoaddown(true);
                druidNovelResource.setSrcRepositoryPath(getSuccessRepositoryPath);
            } catch (IOException e) {
                log.error(RESPONSE_SRC_LOADDOWN_FAIL.name,e);
            }

        }
        return druidNovelResource;
    }
}
