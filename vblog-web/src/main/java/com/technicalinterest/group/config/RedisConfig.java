package com.technicalinterest.group.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.blackshadowwalker.spring.distributelock.interceptor.LockAspectSupport;
import com.github.blackshadowwalker.spring.distributelock.redis.RedisLockManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @package: com.shuyu.blog.config
 * @className: RedisConfig
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-07-14 14:39
 * @since: 0.1
 **/
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.maxIdle}")
    private int maxIdle;

    @Value("${spring.redis.maxTotal}")
    private int maxTotal;

    @Value("${spring.redis.minIdle}")
    private int minIdle;

    @Value("${spring.redis.testOnBorrow}")
    private boolean testOnBorrow;


    @Value("${spring.redis.database}")
    private int database;

    @Value("${redis.lock.prefix:vBlog:}")
    private String lockPrefix;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 21)
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setTestOnBorrow(testOnBorrow);
        return poolConfig;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 22)
    public JedisConnectionFactory getJedisConnectionFactory() {
        JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
        jedisConnFactory.setHostName(host);
        jedisConnFactory.setPort(port);
        jedisConnFactory.setPassword(password);
        jedisConnFactory.setTimeout(timeout);
        jedisConnFactory.setUsePool(true);
        jedisConnFactory.setDatabase(database);
        jedisConnFactory.setPoolConfig(getJedisPoolConfig());
        return jedisConnFactory;
    }
    @Bean(value = {"redis", "redisTemplate"})
    @Order(Ordered.HIGHEST_PRECEDENCE + 23)
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(getJedisConnectionFactory());
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 33)
    @ConditionalOnClass(RedisLockManager.class)
    @ConditionalOnBean(RedisTemplate.class)
    public RedisLockManager getRedisLockManager() {
        RedisLockManager redisLockManager = new RedisLockManager();
        redisLockManager.setLockPrefix(lockPrefix);
        redisLockManager.setCacheLock(false);
        redisLockManager.setRedisTemplate(redisTemplate());
        return redisLockManager;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 33)
    @ConditionalOnClass(value = {RedisLockManager.class, LockAspectSupport.class})
    @ConditionalOnBean(RedisLockManager.class)
    public LockAspectSupport getLockAspectSupport() {
        LockAspectSupport lockAspectSupport = new LockAspectSupport();
        lockAspectSupport.setLockManager(getRedisLockManager());
        return lockAspectSupport;
    }
}
