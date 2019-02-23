package com.druidelf.novelmain.common.utils;

import com.druidelf.novelmain.enums.otherType.SendUrlEnum;



public class UtilForHtmlAndLink {

    /**
     * 根据链接和参数将其包装成a标签的形式
     * @param sendUrlEnum
     * @param params
     * @return
     */
    public static String dealWithALabel ( SendUrlEnum sendUrlEnum, Object... params ) {

        if ( sendUrlEnum.paramNames.length != params.length ) return null;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "<a href = \"" );
        stringBuilder.append( sendUrlEnum.url );
        stringBuilder.append( "?");

        int i = 0;
        for (String paramName : sendUrlEnum.paramNames) {
            stringBuilder.append(paramName).append("=").append(params[i]).append("&");
            i++;
        }

        stringBuilder.deleteCharAt( stringBuilder.length() - 1 );
        stringBuilder.append("\">");
        stringBuilder.append( sendUrlEnum.message );
        stringBuilder.append("</a>");
        return stringBuilder.toString();
    }

    /**
     * 根据链接和参数将其包装成链接的形式
     * @param sendUrlEnum
     * @param params
     * @return
     */
    public static String dealWithLink ( SendUrlEnum sendUrlEnum, Object... params ) {

        if ( sendUrlEnum.paramNames.length != params.length ) return null;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( sendUrlEnum.url );
        stringBuilder.append( "?");

        int i = 0;
        for (String paramName : sendUrlEnum.paramNames) {
            stringBuilder.append(paramName).append("=").append(params[i]).append("&");
            i++;
        }

        stringBuilder.deleteCharAt( stringBuilder.length() - 1 );
        return stringBuilder.toString();
    }
}
