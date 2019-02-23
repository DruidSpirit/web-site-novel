package com.druidelf.novelstaticresource.service.impl;

import com.druidelf.novelstaticresource.service.CacheManagerService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class CacheManagerServiceImpl implements CacheManagerService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 激活账号手动发送邮件操作是否过于频繁
     *
     * @param count
     * @return
     */
    @Override
    public boolean isOverCountWithSendEmailForUser(int count, String cacheKey, boolean isUpdateCount) {
        String sessionId = httpServletRequest.getSession().getId();
        if ( count ==0 || sessionId==null ) return true;
        sessionId += cacheKey;
        int actualCount = ((CacheManagerService) AopContext.currentProxy()).getCacheWithUserHandle(sessionId);
        if ( actualCount < count ){
            if ( isUpdateCount ) {
                // 跟新缓存（使用SpringAOP对象代理调用本类方法）
                ((CacheManagerService) AopContext.currentProxy()).putCacheWithUserHandle(sessionId, actualCount);
            }
            return false;
        }
        return true;
    }
    @Cacheable( cacheNames = "userHandleCache", key = "#sessionId" )
    public int getCacheWithUserHandle ( String sessionId ) {
        return 0;
    }

    /**
     * 激活账号更新记录邮箱发送操作次数的缓存
     * @param sessionId
     * @param count
     * @return
     */
    @SuppressWarnings("UnusedReturnValue")
    @CachePut(cacheNames = "userHandleCache", key = "#sessionId" )
    public int putCacheWithUserHandle ( String sessionId, int count ) {
        return count+1;
    }

    /**
     * 得到频繁登入验证码缓存
     *
     * @param sessionCacheKey 缓存唯一标识
     * @param initCode        验证码缓存值初始化
     * @return 已缓存的验证码, 如果没有缓存则返回初始化的验证码
     */
    @Override
    @Cacheable( cacheNames = "loginVerifyCode", key = "#sessionCacheKey" )
    public String getVerifyCodeCacheWithLogin( String sessionCacheKey, String initCode ) {
        return initCode;
    }

    /**
     * 跟新频繁登入验证码缓存
     *
     * @param sessionCacheKey 缓存唯一标识
     * @param initCode        验证码缓存值初始化
     * @return 已缓存的验证码, 如果没有缓存则返回初始化的验证码
     */
    @Override
    @CachePut( cacheNames = "loginVerifyCode", key = "#sessionCacheKey" )
    public String putVerifyCodeCacheWithLogin(String sessionCacheKey, String initCode) {
        return initCode;
    }
}
