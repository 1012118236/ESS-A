package com.ning.service.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/7/15 14:16
 */
@Configuration
public class RedisConfig {

    /*
    通过源码可以看出，SpringBoot自动帮我们在容器中生成了一个RedisTemplate和一个StringRedisTemplate。
    但是，这个RedisTemplate的泛型是<Object,Object>，写代码不方便，需要写好多类型转换的代码；我们需要一个泛型为<String,Object>形式的RedisTemplate。
    并且，这个RedisTemplate没有设置数据存在Redis时，key及value的序列化方式。
    看到这个@ConditionalOnMissingBean注解后，就知道如果Spring容器中有了RedisTemplate对象了，这个自动配置的RedisTemplate不会实例化。
    因此我们可以直接自己写个配置类，配置RedisTemplate。
    */
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
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

    /**
     * 带有事务的redis
     * @param factory
     * @return
     */
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String,Object> redisTransactionTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> trmplate = redisTemplate(factory);
        /**
         * description 开启redis事务（仅支持单机，不支持cluster）
         */
        trmplate.setEnableTransactionSupport(true);
        return trmplate;
    }

}
