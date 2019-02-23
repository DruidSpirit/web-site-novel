package com.druidelf.novelbackstagemanagement.common.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import net.sf.ehcache.CacheManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephen.Shi
 * @summary CacheManager配置
 * @since 2018/9/9
 */
@Log4j2
@Data
@Configuration
@ConfigurationProperties("cache-manager-config")
public class CacheManagerConfig {

    private List<String> cacheLists = new ArrayList<>();
    private String ehcachePath = "ehcache.xml";

    /**
     * ehcache 主要的管理器
     * @return
     */

    @Bean("ehCacheCacheManager")
    @Primary
    public EhCacheCacheManager ehCacheCacheManager() {

        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(ehCacheManagerFactory().getObject());
        CacheManager cacheManager = ehCacheCacheManager.getCacheManager();
        for ( String s : cacheLists ) {
            cacheManager.addCache(s);
        }

        ehCacheCacheManager.setCacheManager(cacheManager);
        return ehCacheCacheManager;
    }
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactory() {
        EhCacheManagerFactoryBean ehCacheBean = new EhCacheManagerFactoryBean();
        ehCacheBean.setConfigLocation(new ClassPathResource(ehcachePath));
        ehCacheBean.setShared(true);
        return ehCacheBean;
    }
    /**
     * 采用RedisCacheManager作为缓存管理器
     * @param connectionFactory
     */
    /*@Bean
    public RedisCacheManager getRedisCacheManager(RedisConnectionFactory connectionFactory){

        RedisCacheWriter cacheWriter = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
        ClassLoader loader = this.getClass().getClassLoader();
        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(loader);
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializer);

        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        cacheConfig = cacheConfig.entryTtl(cacheProperties.getRedis().getTimeToLive());//设置所有的超时时间
        //设置单个缓存的超时时间

        Map<String, RedisCacheConfiguration> initialCacheConfigurations = new HashMap<>();
        RedisCacheConfiguration finalCacheConfig = cacheConfig;
        for (Map.Entry entry : cacheMaps.entrySet()) {
            initialCacheConfigurations.put((String) entry.getKey(),cacheConfig.entryTtl((Duration) entry.getValue()));
        }
        RedisCacheManager cacheManager = new RedisCacheManager(cacheWriter, cacheConfig,initialCacheConfigurations);

        return cacheManager;
    }*/
    /*@Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        @SuppressWarnings("rawtypes")
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }*/

}
