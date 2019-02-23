package other;

import com.druidelf.novelmain.common.utils.UtilForRegexp;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class txtSplit {
    public static void main(String[] args) throws IOException {
        String name = "崩坏神话";
        String regexRule = "^[0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s|章|卷]{1}[\\s|：].*$";
        String regexRule2 = "^[0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s|章|卷]{1}.*$";
       if (test1(name,regexRule)==1){
           if (test1(name,regexRule2)==1){
               System.out.println("正则表达式没有配置到任何相关的章节");
           }else {
               System.out.println(name+"--章节拆分成功!");
           }
       }else {
           System.out.println(name+"--章节拆分成功!");
       }
    }
    public static int test1( String name, String regexRule ) throws IOException {
        if (regexRule==null) return 0;
        String splitNovelName = name;
        String basePath = "C:/Users/wang/Desktop/testNovel/RepositoryNovel/武侠小说/"+splitNovelName+"/";
        String path = ""+splitNovelName+".txt";
        path = basePath + path;
        File file = new File(path);
        List<String> list = FileUtils.readLines(file,"utf-8");
        List<String> contain = new ArrayList<>();
        String capName = null;
        int sort = 1;
        for (String s : list) {
            String jdugeLine = s.trim();
            boolean jduge = UtilForRegexp.getRegexpResult(regexRule,jdugeLine);
            if (jduge) {
                if ( capName != null && contain.size()>5 ){
                    // 替换会影响文件写出的特殊符号
                    capName = capName.replace("/","~")
                            .replace("\\","~")
                            .replace("*","#")
                            .replace(":","")
                            .replace("?","")
                            .replace("\"","")
                            .replace("<","")
                            .replace(">","")
                            .replace("|","");
                    FileUtils.writeLines(
                            new File(basePath+"chapter/"+"("+sort+")"+capName+".txt")
                            ,contain
                    );
                    sort++;
                }
                contain.clear();
                capName = jdugeLine;
            }
            contain.add(s);
        }
        return sort;
    }
}
