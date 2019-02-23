package com.druidelf.novelstaticresource.common.utils;

import com.druidelf.novelstaticresource.entity.DruidUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class ShiroMd5Util {
    
    /**
     * 添加user的密码加密方法
     * @param druidUser
     * @return
     */
    public static DruidUser SysMd5(DruidUser druidUser) {

        if (druidUser == null) return null;

        if (druidUser.getSalt() == null) druidUser.setSalt(createRandom(false, 8));

        String hashAlgorithmName = "MD5";//加密方式

        Object crdentials =druidUser.getPassword();//密码原值

        ByteSource salt = ByteSource.Util.bytes(druidUser.getSalt());//以账号作为盐值

        int hashIterations = 1024;//加密1024次

        SimpleHash hash = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations);

        druidUser.setPassword(hash.toString());

        return druidUser;
    }

    /**
     * 根据提交的原密码和盐值得到加密后的密码
     * @param password 原密码
     * @param saltFromUser 盐值
     * @return
     */
    public static String SysMd5(String password, String  saltFromUser) {

        if (password == null) return null;

        String hashAlgorithmName = "MD5";//加密方式

        Object crdentials = password;//密码原值

        ByteSource salt = ByteSource.Util.bytes(saltFromUser);//以账号作为盐值

        int hashIterations = 1024;//加密1024次

        SimpleHash hash = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations);

        return hash.toString();
    }

    /**
     * 创建指定数量的随机字符串
     *
     * @param numberFlag
     *            是否是数字
     * @param length
     * @return
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890"
                : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }
}

