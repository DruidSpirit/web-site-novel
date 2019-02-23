package com.druidelf.novelbackstagemanagement.common.utils.UtilForNet;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.druidelf.novelbackstagemanagement.enums.otherType.UserAgentForEnum.USERAGENT7;

public class HttpGetDownFile {
    /**
     * 线程锁
     */
    private static Lock lock = new ReentrantLock();
    /**
     * 得到文件地址并下载文件(已经处理过的正常地址)
     *
     * @param link 下载链接
     * @param storageAddress 下载文件的储存地址(这里的路径名称包括文件名及其后缀)
     * @throws IOException IO异常
     */
    public static boolean downloadAndSaveFile(String link, String storageAddress) throws IOException {


            Connection.Response request = Jsoup.connect(link).referrer(link)
                    .userAgent(USERAGENT7.UserAgent)
                    .timeout(60000)
                    .maxBodySize(1024 * 1024 * 20)
                    .ignoreContentType(true)
                    .execute();

            System.out.println(request.url());
            byte[] fileData = request.bodyAsBytes();

            FileUtils.writeByteArrayToFile(new File(storageAddress), fileData);

            return true;

    }

    /**
     * 过滤下载链接，清除重定向、链接中包含中文字符问题并下载存储文件
     *
     * @param link 下载链接
     * @param storageAddress 下载文件的储存地址(这里的文件名称不包括文件名)
     * @throws IOException IO异常
     */
    public static String filterLinkAndDownloadAndSave(String link, String storageAddress, boolean ismkdirsByFileName) throws IOException {
        lock.lock(); // 这里给上锁防止多线程重复创建文件夹
        if (!new File(storageAddress).isDirectory()) {
            new File(storageAddress).mkdirs();
        }
        lock.unlock();

        Connection request = Jsoup.connect(link).referrer(link)
                .userAgent(USERAGENT7.UserAgent)
                .timeout(5 * 60000)//请求超时时间设置
                .maxBodySize(1024 * 1024 * 20)//得到的文件最大值20M
                .ignoreContentType(true)//忽略返回数据类型
                .followRedirects(false);//不允许重定向

        Connection.Response req = request.execute();//开始执行(连接网站服务器)

        String location = req.header("Location");
        if (location == null) location = link;
        URL redir = StringUtil.resolve(req.url(), location);//取出服务器真正的下载链接

        //得到链接的后缀名称即文件名称
        String[] url = redir.toString().split("");//
        String names[] = redir.toString().split("/");
        String names2[] = link.split("/");
        String fileName = names[names.length - 1];//文件名称
        String fileName2 = names2[names2.length - 1];//文件名称
        StringBuilder resultUrl = new StringBuilder();

        //对链接中出现的中文字符进行筛选
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        for (String string : url) {
            Matcher matcher = pat.matcher(string);
            if (matcher.find()) string = URLEncoder.encode(string, "utf-8");
            resultUrl.append(string);
        }

        Matcher matcher2 = pat.matcher(fileName);
        if (!matcher2.find()) fileName = fileName2;
        // 将没有后缀名的图片转成有后缀名称
        if (!fileName.contains(".")) {
            fileName += fileName + ".jpg";
        }
        // 这里用文件名新建一个文件夹
        String address;
        if (ismkdirsByFileName) {
            address = storageAddress + "\\" + fileName.split("\\.")[0] + "\\" + fileName;
        } else {
            address = storageAddress + fileName;
        }

        if (downloadAndSaveFile(resultUrl.toString(), address)) {
            return fileName;
        } else {
            return null;
        }
    }

}
