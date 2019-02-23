package other;

import com.druidelf.novelmain.common.utils.GetEnumInfo;
import com.druidelf.novelmain.enums.bussinessType.NovelTypeEunm;
import com.druidelf.novelmain.enums.bussinessType.PeriodTypeEnum;
import com.druidelf.novelmain.enums.responseType.ResponseDataEnums;
import com.druidelf.novelmain.enums.responseType.ShiroExceptionType;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class enumInterfaceTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
       // System.out.println(GetEnumInfo.getNameByStatusCode( ResponseDataEnums.class,3000));
        ;
        String tes = "你还发";
        String dsf= URLDecoder.decode(tes,"utf-8");
        String fs = URLEncoder.encode(tes,"utf-8");
        System.out.println(dsf);
        System.out.println(fs);
    }
}
