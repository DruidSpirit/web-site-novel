package com.druidelf.novelbackstagemanagement.enums.otherType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserAgentForEnum {

    USERAGENT1("Mozilla/5.0(Macintosh;U;IntelMacOSX10_6_8;en-us)AppleWebKit/534.50(KHTML,likeGecko)Version/5.1Safari/534.50", "safari5.1"),
    USERAGENT2("Mozilla/4.0(compatible;MSIE8.0;WindowsNT6.0;Trident/4.0)", "IE8.0"),
    USERAGENT3("Mozilla/5.0(WindowsNT6.1;rv:2.0.1)Gecko/20100101Firefox/4.0.1", "Firefox4.0.1"),
    USERAGENT4("Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Trident/4.0;SE2.XMetaSr1.0;SE2.XMetaSr1.0;.NETCLR2.0.50727;SE2.XMetaSr1.0)", ""),
    USERAGENT5("Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;360SE)", "360"),
    USERAGENT6("NOKIA5700/UCWEB7.0.2.37/28/999", "UC"),
    USERAGENT7("Mozilla/5.0(Macintosh;IntelMacOSX10_7_0)AppleWebKit/535.11(KHTML,likeGecko)Chrome/17.0.963.56Safari/535.11", "Chrome"),
    USERAGENT8("Opera/9.80(WindowsNT6.1;U;en)Presto/2.8.131Version/11.11", "Opera"),
    USERAGENT9("Mozilla/5.0 (Windows NT 5.1; rv:2.0b6) Gecko/2010010 Firefox/4.0b6", "Firefox"),
    USERAGENT10("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0", "Firefox");

    public String UserAgent;
    public String name;
}
