package com.app.common;

import java.util.Map;
import java.util.TreeSet;

import com.app.redis.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public interface RedisUtil {
	/**
	 * redis操作公共方法
	 */

	/**
	 * redis保存一个String 的内容 例如 ： 单个变量
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value) ;
	/**
	 * redis保存一个byte[] 的内容 例如 ： 单个变量
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(byte[] key, byte[] value);

	/**
	 * redis保存一个byte[] 的内容,可以设置过期时间 例如 ： 单个变量
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String setex(byte[] key, int seconds, byte[] value);

	/**
	 * redis保存一个String 的内容,可以设置过期时间 例如 ： 单个变量
	 * 
	 * @param key
	 * @param seconds
	 *            秒
	 * @param value
	 * @return
	 */
	public String setex(String key, int seconds, String value);

	/**
	 * redis保存一个String 的内容,可以设置过期时间 例如 ： 单个变量
	 * 
	 * @param key
	 * @param milliseconds
	 *            毫秒
	 * @param value
	 * @return
	 */
	public String setex(String key, long milliseconds, String value);

	/**
	 * redis 根据byte[] 获取 缓存内容
	 * 
	 * @param key
	 * @return
	 */
	public byte[] get(String key, String key1);

	public byte[] get(byte[] key);

	

	/**
	 * redis 根据String 获取 缓存内容
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key);

	/**
	 * 根据key删除缓存
	 * 
	 * @param key
	 * @return
	 */
	public Long del(String key);

	public Long del(byte[] key);

	/**
	 * 根据String[]删除缓存 例如 {"key201", "key202"}
	 * 
	 * @param key
	 * @return
	 */
	public Long del(String[] key);

	/**
	 * 判断key键是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean checkexists(String key);

	public boolean checkexists(byte[] key);

	/**
	 * 设置key的过期时间
	 * 
	 * @param key
	 * @param second
	 * @return
	 */
	public Long ttl(String key);

	/**
	 * 判断key是否过期
	 * 
	 * @param key
	 * @return
	 */
	public Long hdel(String key, String item);

	/**
	 * 设置hash数据类型
	 * 
	 * @param key
	 * @param item
	 * @param value
	 * @return
	 */
	public Long hset(String key, String item, String value);

	/**
	 * 获取hash数据类型
	 * 
	 * @param key
	 * @param item
	 * @return
	 */
	public String hget(String key, String item);

	/**
	 * 删除hash数据
	 * 
	 * @param key
	 * @param item
	 * @return
	 */
	public Long incr(String key);
	/**
	 * 获取所有的key
	 * 
	 * @param pattern
	 * @return
	 */
	public TreeSet<String> keys();

}
