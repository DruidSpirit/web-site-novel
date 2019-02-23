package com.druidelf.novelmain.service;

public interface CacheManagerService {

    /**
     * 激活账号手动发送邮件操作是否过于频繁
     * @param count 操作上限次数
     * @param isUpdateCount 是否跟新上限次数缓存(上限次数加一)
     * @return
     */
    boolean isOverCountWithSendEmailForUser (int count, String cacheKey, boolean isUpdateCount);

    /**
     * 激活账号得到记录邮箱发送操作次数的缓存
     * @param sessionId
     * @return
     */
    int getCacheWithUserHandle (String sessionId);

    /**
     * 激活账号更新记录邮箱发送操作次数的缓存
     * @param sessionId
     * @param count
     * @return
     */
    int putCacheWithUserHandle(String sessionId, int count);

    /**
     * 得到频繁登入验证码缓存
     * @param sessionCacheKey 缓存唯一标识
     * @param initCode 验证码缓存值初始化
     * @return 已缓存的验证码,如果没有缓存则返回初始化的验证码
     */
    String getVerifyCodeCacheWithLogin ( String sessionCacheKey, String initCode );
    /**
     * 跟新频繁登入验证码缓存
     * @param sessionCacheKey 缓存唯一标识
     * @param initCode 验证码缓存值初始化
     * @return 已缓存的验证码,如果没有缓存则返回初始化的验证码
     */
    String putVerifyCodeCacheWithLogin ( String sessionCacheKey, String initCode );
}
