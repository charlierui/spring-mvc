package com.app.redis;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisCache implements Cache {
	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

	private static JedisConnectionFactory jedisConnectionFactory;

	private final String id;

	/**
	 * The {@code ReadWriteLock}.
	 */
	private final ReadWriteLock rwl = new ReentrantReadWriteLock();
	

	public RedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		logger.debug("MybatisRedisCache:id=" + id);
		this.id = id;
	}

	/**
	 * 清空所有缓存
	 */
	@Override
	public void clear() {
		rwl.readLock().lock();
		JedisConnection connection = null;
		try {
			connection = jedisConnectionFactory.getConnection();
			connection.flushDb();
			connection.flushAll();
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			rwl.readLock().unlock();
		}
	}

	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * 获取缓存总数量
	 */
	@Override
	public int getSize() {
		int result = 0;
		JedisConnection connection = null;
		try {
			connection = jedisConnectionFactory.getConnection();
			result = Integer.valueOf(connection.dbSize().toString());
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

	@Override
	public void putObject(Object key, Object value) {
		rwl.writeLock().lock();

		JedisConnection connection = null;
		try {
			connection = jedisConnectionFactory.getConnection();
			RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
			connection.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
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

	@Override
	public Object getObject(Object key) {
		// 先从缓存中去取数据,先加上读锁
		rwl.readLock().lock();
		Object result = null;
		JedisConnection connection = null;
		try {
			connection = jedisConnectionFactory.getConnection();
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

	@Override
	public Object removeObject(Object key) {
		rwl.writeLock().lock();

		JedisConnection connection = null;
		Object result = null;
		try {
			connection = jedisConnectionFactory.getConnection();
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

	@Override
	public ReadWriteLock getReadWriteLock() {
		// TODO Auto-generated method stub
		return rwl;
	}

}
