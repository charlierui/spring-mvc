package com.app.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.app.model.TUser;
import com.app.redis.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
/**
 * 
 * @author arui
 *
 */
public class BaseController {
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
	@Autowired
	JedisCluster jedisCluster;
	/**
	 * 成功
	 */
	protected static final String SUCCESS = "1";

	/**
	 * 失败
	 */
	protected static final String UNSUCCESS = "-1";

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
	public String set(String key, String value) {
		logger.info("添加集群缓存：" + key);
		return jedisCluster.set(key, value);
	}

	/**
	 * redis保存一个byte[] 的内容 例如 ： 单个变量
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(byte[] key, byte[] value) {
		logger.info("添加集群缓存：" + key);
		return jedisCluster.set(key, value);

	}

	/**
	 * redis保存一个byte[] 的内容,可以设置过期时间 例如 ： 单个变量
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String setex(byte[] key, int seconds, byte[] value) {
		logger.info("添加集群缓存：" + key);
		return jedisCluster.setex(key, seconds, value);
	}

	/**
	 * redis保存一个String 的内容,可以设置过期时间 例如 ： 单个变量
	 * 
	 * @param key
	 * @param seconds
	 *            秒
	 * @param value
	 * @return
	 */
	public String setex(String key, int seconds, String value) {
		logger.info("添加集群缓存：" + key);
		return jedisCluster.setex(key, seconds, value);
	}

	/**
	 * redis保存一个String 的内容,可以设置过期时间 例如 ： 单个变量
	 * 
	 * @param key
	 * @param milliseconds
	 *            毫秒
	 * @param value
	 * @return
	 */
	public String setex(String key, long milliseconds, String value) {
		logger.info("添加集群缓存：" + key);
		return jedisCluster.psetex(key, milliseconds, value);
	}

	/**
	 * redis 根据byte[] 获取 缓存内容
	 * 
	 * @param key
	 * @return
	 */
	public byte[] get(String key,String key1) {
		boolean res = checkexists((key+key1).getBytes());
		if (res == true) {
			logger.info((key+key1).getBytes()+" 命中集群缓存：" + (key+key1).getBytes());
		}
		return jedisCluster.get((key+key1).getBytes());

	}
	public byte[] get(byte[] key) {
		boolean res = checkexists(key);
		if (res == true) {
			logger.info((key+" 命中集群缓存："+SerializeUtil.unserialize(jedisCluster.get(key))));
		}
		return jedisCluster.get(key);

	}
	
	/**
	 * redis 根据String 获取 缓存内容
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		boolean res = checkexists(key);
		if (res == true) {
			logger.info("命中集群缓存：" + key);
		}
		return jedisCluster.get(key);
	}

	/**
	 * 根据key删除缓存
	 * 
	 * @param key
	 * @return
	 */
	public Long del(String key) {
		logger.info("删除集群缓存：" + key);
		return jedisCluster.del(key);
	}

	/**
	 * 根据String[]删除缓存 例如 {"key201", "key202"}
	 * 
	 * @param key
	 * @return
	 */
	public Long del(String[] key) {
		logger.info("删除集群缓存：" + key.toString());
		return jedisCluster.del(key);
	}

	/**
	 * 判断key键是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean checkexists(String key) {
		return jedisCluster.exists(key);
	}

	public boolean checkexists(byte[] key) {
		return jedisCluster.exists(key);
	}
	/**  
     * 设置key的过期时间  
     *   
     * @param key  
     * @param second  
     * @return  
     */  
    public Long ttl(String key) {  
        return jedisCluster.ttl(key);  
    }  
    /**  
     * 判断key是否过期  
     *   
     * @param key  
     * @return  
     */  
    public Long hdel(String key, String item) {  
         return jedisCluster.hdel(key, item);  
    }  
    /**  
     * 设置hash数据类型  
     *   
     * @param key  
     * @param item  
     * @param value  
     * @return  
     */  
    public Long hset(String key, String item, String value) {  
        return jedisCluster.hset(key, item, value);  
    }  
      
    /**  
     * 获取hash数据类型  
     *   
     * @param key  
     * @param item  
     * @return  
     */  
    public String hget(String key, String item) {  
        return jedisCluster.hget(key, item);  
    }  
      
    /**  
     * 删除hash数据  
     * @param key  
     * @param item  
     * @return  
     */  
    public Long incr(String key) {  
        return jedisCluster.incr(key);  
    }  
    /**
     * 获取所有的key
     * @param pattern
     * @return
     */
    public TreeSet<String> keys(String pattern){  
        logger.debug("Start getting keys...");  
        TreeSet<String> keys = new TreeSet<>();  
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();  
        for(String k : clusterNodes.keySet()){  
            logger.debug("Getting keys from: {}", k);  
            JedisPool jp = clusterNodes.get(k);  
            Jedis connection = jp.getResource();  
            try {  
                keys.addAll(connection.keys(pattern));  
            } catch(Exception e){  
                logger.error("Getting keys error: {}", e);  
            } finally{  
                logger.debug("Connection closed.");  
                connection.close();//用完一定要close这个链接！！！  
            }  
        }  
        logger.debug("Keys gotten!");  
        return keys;  
    }  
	/**
	 * 分页
	 * 
	 * @param request
	 * @return
	 */
	protected int row(HttpServletRequest request) {

		return Integer.parseInt(request.getParameter("rows"));
	}

	/**
	 * 输出返回结果
	 * 
	 * @param json
	 * @param response
	 */
	protected void response_write(ReturnMSG json, HttpServletResponse response) {
		try {
			JSONObject result = new JSONObject();

			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(result.toJSONString(json));
			logger.info("json::::::" + result.toJSONString(json));
			response.getWriter().flush();
		} catch (IOException e) {
			// logger.error(e.getLocalizedMessage(),e);
		}
	}

	/**
	 * 输出返回结果
	 * 
	 * @param json
	 * @param response
	 */
	protected void response_write(List json, HttpServletResponse response) {
		try {
			JSONObject result = new JSONObject();

			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(result.toJSONString(json));
			logger.info("json::::::" + result.toJSONString(json));
			response.getWriter().flush();
		} catch (IOException e) {
			// logger.error(e.getLocalizedMessage(),e);
		}
	}

	protected void response_write(Object json, HttpServletResponse response) {
		try {
			JSONObject result = new JSONObject();

			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(result.toJSONString(json));
			logger.info("json::::::" + result.toJSONString(json));
			response.getWriter().flush();
		} catch (IOException e) {
			// logger.error(e.getLocalizedMessage(),e);
		}
	}

	protected ReturnMSG getRM(String statuscode, String message) {
		JSONObject result = new JSONObject();
		ReturnMSG rm = new ReturnMSG();
		rm.setStatuscode(statuscode);
		rm.setMsg(message);
		return rm;
	}

	/**
	 * session保存
	 * 
	 * @param name
	 * @param webuser
	 */
	public void addSessionWebUser(String name, TUser webuser) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		request.getSession().setAttribute(name, webuser);

	}

	/**
	 * session获取
	 * 
	 * @param name
	 * @return
	 */
	public TUser getWebUserAttribute(String name) {
		TUser tuser = null;
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		if (ra != null) {
			HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
			if (!name.trim().equals("")) {
				tuser = (TUser) request.getSession().getAttribute(name);
			}
		}
		return tuser;
	}

	/**
	 * session删除
	 * 
	 * @param name
	 * @return
	 */
	public void delWebUserAttribute(String name) {

		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		if (ra != null) {
			HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
			if (!name.trim().equals("")) {
				request.getSession().removeAttribute(name);
			}
		}

	}
	/**
	 * 文件保存
	 * @param request
	 * @param file
	 * @param foldername 要保存的文件夹名称
	 * @return
	 */
    public static String saveFile(HttpServletRequest request, MultipartFile file,String foldername) {  
    	String fileName = "";
		String logImageName = "";
        // 判断文件是否为空  
        if (!file.isEmpty()) {  
            try {             	
                // 文件保存路径  
                String filePath = request.getSession().getServletContext().getRealPath("/") + foldername+"/";
                String _fileName = file.getOriginalFilename();
    			String suffix = _fileName.substring(_fileName.lastIndexOf("."));
    			// /**使用UUID生成文件名称**/
    			logImageName = UUID.randomUUID().toString() + suffix;
    			fileName = filePath + File.separator + logImageName;
    			File restore = new File(filePath);
    			//验证路径是否存在，不存在则创建
    			if (!restore.exists()) {
    				restore.mkdirs();
    			}
    			try {
    				file.transferTo(new File(fileName));
    			} catch (Exception e) {
    				throw new RuntimeException(e);
    			}
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return fileName;  
    }  
}
