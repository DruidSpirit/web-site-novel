package com.druidelf.novelbackstagemanagement.common.utils.UtilForNet;

import com.druidelf.novelbackstagemanagement.common.utils.UtilForRegexp;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForString;
import com.druidelf.novelbackstagemanagement.response.CrawlerElementDo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.druidelf.novelbackstagemanagement.enums.otherType.SystemParamEnum.SYSTEM_PARAM_ROOT_PATH_FOR_LOCAL;

@SuppressWarnings({"WeakerAccess", "unused"})
@Log4j2
public class UtilForSplittingChapters {


    /**
     * 分拆章节多规则遍历
     * @param repositoryPath 被分拆文件的路径
     * @return String
     * @throws IOException 被分拆文件或者写入的目录文件流异常
     */
    public static String saveAndSplittingChapters ( String repositoryPath ) throws IOException {
        String regexRule = "^[\\s+|0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s+|章|卷]{1}[\\s+|：].*$";
        String regexRule2 = "^[\\s+|0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s+|章|卷]{1}.*$";
        return saveAndSplittingChapters( repositoryPath, regexRule, regexRule2 );
    }

    /**
     * 分拆章节多规则遍历
     * @param repositoryPath 被分拆文件的路径
     * @param regexRules 分拆规则
     * @return String
     * @throws IOException 被分拆文件或者写入的目录文件流异常
     */
    public static String saveAndSplittingChapters ( String repositoryPath, String... regexRules ) throws IOException {
        if (regexRules==null||regexRules.length==0){
            regexRules = new String[]{
                    "^[\\s+|0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s+|章|卷]{1}[\\s+|：].*$",
                    "^[\\s+|0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s+|章|卷]{1}.*$"
            };
        }
        String catalogJsonPath = null;
        for (String regexRule : regexRules ) {
            catalogJsonPath = saveAndSplittingChaptersForSingle( repositoryPath, regexRule,true );
            if ( catalogJsonPath != null ) {
                break;
            }
        }
        return catalogJsonPath;
    }
    /**
     * 分拆章节多规则遍历
     * @param repositoryPath 被分拆文件的路径
     * @param regexRules 分拆规则
     * @param isRegexRuleCompare 是否开启规则合理选择
     * @return String
     * @throws IOException 被分拆文件或者写入的目录文件流异常
     */
    public static String saveAndSplittingChapters ( String repositoryPath,boolean isRegexRuleCompare, String... regexRules ) throws IOException {
        if (regexRules==null||regexRules.length==0){
            regexRules = new String[]{
                    "^[\\s+|0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s+|章|卷]{1}[\\s+|：].*$",
                    "^[\\s+|0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s+|章|卷]{1}.*$"
            };
        }
        String catalogJsonPath = null;
        for (String regexRule : regexRules ) {
            catalogJsonPath = saveAndSplittingChaptersForSingle( repositoryPath, regexRule,isRegexRuleCompare );
            if ( catalogJsonPath != null ) {
                break;
            }
        }
        return catalogJsonPath;
    }

    /**
     * 分拆章节并写入本地磁盘
     * @param repositoryPath 被分拆文件的路径
     * @param regexRule 分拆规则
     * @param isRegexRuleCompare 是否开启规则合理选择
     * @return 被写入的目录文件路径
     * @throws IOException 被分拆文件或者写入的目录文件流异常
     */
    public static String saveAndSplittingChaptersForSingle ( String repositoryPath, String regexRule, boolean isRegexRuleCompare ) throws IOException {
        List<CrawlerElementDo> crawlerElementDoList = new ArrayList<>();
        StringBuilder basePath = new StringBuilder();
        String temArray[] = repositoryPath.split("\\\\");
        for ( int i = 0;i< temArray.length-2;i++ ) {
            basePath.append(temArray[i]).append("\\");
        }
        File file = new File(repositoryPath);
        List<String> list = FileUtils.readLines(file,UtilForString.getFilecharset(file));
        List<String> contain = new ArrayList<>();
        String capName = null;
        int sort = 1;
        for (String s : list) {
            String jdugeLine = s.trim();
            boolean jduge = UtilForRegexp.getRegexpResult(regexRule,jdugeLine);
            if (jduge) {
                if ( capName != null && contain.size()>5 ){
                    // 替换会影响文件写出的特殊符号
                    capName = UtilForString.fileNameRemoveSpecificSymbol(capName);
                    String relPath = basePath.toString()+"chapter/"+"("+sort+")"+capName+".txt";
                    CrawlerElementDo crawlerElementDo = CrawlerElementDo.builder()
                            .Name(capName)
                            .storePosition( relPath.replace(SYSTEM_PARAM_ROOT_PATH_FOR_LOCAL.Param,"\\").replaceAll("\\\\","/") )
                            .relUrl(repositoryPath)
                            .build();
                    try {
                        FileUtils.writeLines(
                                new File( relPath )
                                ,contain
                        );
                        crawlerElementDo.setCrawlingSuccess(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    crawlerElementDoList.add(crawlerElementDo);
                    sort++;
                }
                contain.clear();
                capName = jdugeLine;
            }
            contain.add(s);
        }
        // 选择是否开启正则表达式筛选最合理的章节规则(一个章节不超过2万字)
        if (isRegexRuleCompare) {
            if (sort > (list.size() / 2000) && writeJosnFileToLocal(basePath + "catalog.json", crawlerElementDoList)) {
                return basePath + "catalog.json";
            }else {
                log.warn("路径为"+repositoryPath+"的小说正则表达式无匹配到任何章节");
            }
        }else {
            if (sort > 1 && writeJosnFileToLocal(basePath + "catalog.json", crawlerElementDoList)) {
                return basePath + "catalog.json";
            }else {
                log.warn("路径为"+repositoryPath+"的小说正则表达式无匹配到任何章节");
            }
        }
        return null;
    }

    /**
     * 将对象转成json字符串并写入磁盘
     * @param filePath 写入的文件路径
     * @param object 需要转化的对象
     * @return boolean
     */
    public static boolean writeJosnFileToLocal ( String filePath, Object object ) {

        // 将小说章节的目录及存放地址放入到实体类并生成json文件存放到本地文件夹
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] objectBytes = null;
        try {
            objectBytes = objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("爬取目录实体转换成json字符串异常",e);
        }
        if (objectBytes==null) return false;
        try {
            System.out.println("开始写入json格式目录数据");
            FileUtils.writeByteArrayToFile(
                    new File(filePath)
                    ,objectBytes
            );
            return true;
        } catch (IOException e) {
            log.error("json文件写入本地出现异常呢",e);
        }
        return false;
    }
}
