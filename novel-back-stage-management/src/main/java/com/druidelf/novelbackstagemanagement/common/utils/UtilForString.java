package com.druidelf.novelbackstagemanagement.common.utils;

import java.io.*;

public class UtilForString {
    /**
     * 文件或者文件夹名称去除特殊字符
     * @param fileName 文件或者文件夹名称
     * @return 处理后的名称
     */
    public static String fileNameRemoveSpecificSymbol ( String fileName ) {
        if (fileName==null) return null;
        return fileName.replace("/","~")
                .replace("\\","~")
                .replace("*","#")
                .replace(":","")
                .replace("?","")
                .replace("\"","")
                .replace("<","")
                .replace(">","")
                .replace("|","");
    }

    /**
     * 根据文件路径读取该文件的java编码格式
     * @param path 文件路径
     * @return 编码格式
     */
    public static String getFileCodedFormat ( String path ) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "utf-8";
        }
        byte[] head = new byte[3];
        try {
            inputStream.read(head);
        } catch (IOException e) {
            e.printStackTrace();
            return "utf-8";
        }
        String code;

        code = "gb2312";
        if (head[0] == -1 && head[1] == -2 )
            code = "UTF-16";
        if (head[0] == -2 && head[1] == -1 )
            code = "Unicode";
        if(head[0]==-17 && head[1]==-69 && head[2] ==-65)
            code = "UTF-8";
        return code;
    }
    //判断编码格式方法
    public static  String getFilecharset(File sourceFile) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset; //文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF
                    && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; //文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; //文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; //文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80
                            // - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }

}
