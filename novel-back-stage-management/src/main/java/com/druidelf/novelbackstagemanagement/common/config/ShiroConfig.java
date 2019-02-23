package com.druidelf.novelbackstagemanagement.common.config;


import com.druidelf.novelbackstagemanagement.common.config.redisCache.SessionDaoConfig;
import com.druidelf.novelbackstagemanagement.common.config.redisCache.UserSessionCache;
import com.druidelf.novelbackstagemanagement.common.config.shiroRealm.UserRealm;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("JavaDoc")
@Log4j2
@Data
@Configuration
@ConfigurationProperties("shiro-config")
public class ShiroConfig {

    private Map<String,String> filterChainMaps = new LinkedHashMap<>();
    private Duration sessionLiveTime;

    /**
     * shiro核心过滤器
     * @param securityManager
     * @return
     */
    @Bean("shirFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/unauth"); // 前后端分离，链接跳转交给前端

        Map<String,String> shiroFilterChainMaps = new LinkedHashMap<>();
        for (Map.Entry entry : filterChainMaps.entrySet()) {
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            if ( !key.equals(val) ){
                shiroFilterChainMaps.put(val,key);
            }
        }
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainMaps);
        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1024);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    /**
     * 自定义Realm
     * @return
     */
    @Bean
    public UserRealm myShiroRealm() {
        UserRealm myShiroRealm = new UserRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }
    /**
     * shiro安全管理
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(SessionDaoConfig sessionDaoConfig, EhCacheCacheManager ehCacheCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 将HTTPSession的sessionId赋值给shiro并返回一个DefaultWebSessionManager
        UserSessionCache userSessionCache = new UserSessionCache();
        userSessionCache.setGlobalSessionTimeout(sessionLiveTime.getSeconds()*1000);
        // 使用redis缓存来存储shiro会话
        userSessionCache.setSessionDAO(sessionDaoConfig);
        securityManager.setSessionManager(userSessionCache);
        securityManager.setRealm(myShiroRealm());
        // 将shiro缓存整合到ehcache缓存中
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(ehCacheCacheManager.getCacheManager());
        securityManager.setCacheManager(ehCacheManager);
        return securityManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持
     * @param securityManager
     * @return
     */
    @Bean("authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }



}
