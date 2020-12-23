package com.martinwj.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.ShardedJedis;

/**
 * @ClassName: RedisCacheTransfer
 * @Description: TODO 静态注入中间类
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
public class RedisCacheTransfer {
    @Autowired
    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        RedisCache.setJedisConnectionFactory(jedisConnectionFactory);
    }
}
