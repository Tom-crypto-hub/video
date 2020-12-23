package com.martinwj.jedis;

import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.martinwj.util.SerializeUtil;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @ClassName: RedisCache
 * @Description: TODO Redis缓存
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
public class RedisCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private static JedisConnectionFactory jedisConnectionFactory;

    private final String id;

    private final ReadWriteLock rwl = new ReentrantReadWriteLock();


    public RedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("缓存实例需要一个ID");
        }
        logger.debug("MybatisRedisCache:id=" + id);
        this.id = id;
    }

    /**
     * 清空所有缓存
     */
    public void clear() {
        rwl.readLock().lock();
        JedisConnection connection = null;
        try {
            connection = (JedisConnection) jedisConnectionFactory.getConnection();
            connection.flushDb();
            connection.flushAll();
            logger.debug("清除缓存.......");
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            rwl.readLock().unlock();
        }
    }

    public String getId() {
        return this.id;
    }

    /**
     * 获取缓存总数量
     */
    public int getSize() {
        int result = 0;
        JedisConnection connection = null;
        try {
            connection = (JedisConnection) jedisConnectionFactory.getConnection();
            result = Integer.parseInt(connection.dbSize().toString());
            logger.info("添加mybaits二级缓存数量：" + result);
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public void putObject(Object key, Object value) {
        System.out.println("key: " + key + "\n value: "  + value);
        rwl.writeLock().lock();

        JedisConnection connection = null;
        try {
            connection = (JedisConnection) jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            connection.set(Objects.requireNonNull(SerializeUtil.serialize(key)), Objects.requireNonNull(SerializeUtil.serialize(value)));
            logger.info("添加mybaits二级缓存key=" + key + ",value=" + value);
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            rwl.writeLock().unlock();
        }
    }

    public Object getObject(Object key) {
        // 先从缓存中去取数据,先加上读锁
        rwl.readLock().lock();
        Object result = null;
        JedisConnection connection = null;
        try {
            connection = (JedisConnection) jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            result = serializer.deserialize(connection.get(serializer.serialize(key)));
            logger.info("命中mybaits二级缓存,value=" + result);

        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            rwl.readLock().unlock();
        }
        return result;
    }

    public Object removeObject(Object key) {
        rwl.writeLock().lock();

        JedisConnection connection = null;
        Object result = null;
        try {
            connection = (JedisConnection) jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            result = connection.expire(serializer.serialize(key), 0);
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            rwl.writeLock().unlock();
        }
        return result;
    }

    public static void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        RedisCache.jedisConnectionFactory = jedisConnectionFactory;
    }

    public ReadWriteLock getReadWriteLock() {
        // TODO 自动生成的方法存根
        return rwl;
    }

}
