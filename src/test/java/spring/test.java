package spring;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.util.JedisClusterCRC16;

public class test {
	private static Logger logger = LoggerFactory.getLogger(test.class);

	public static void main(String[] args) {
		String key = "1";
        // 这东西 可以直接看到key 的分片数，就能知道放哪个 节点
        System.out.println("1:::"+JedisClusterCRC16.getSlot(key));
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7000));
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7001));
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7002));
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7003));
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7004));
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7005));
        // 3个master 节点
        JedisCluster jc = new JedisCluster(jedisClusterNodes);
       
//        jc.set(key, "bar1");
//        jc.set("test", "bar1333333333");
//
//        String value = jc.get(key);
//        System.out.println(":::::"+value);
//        System.out.println(jc.get("test"));
        jc.del("count");
       // logger.debug("Start getting keys...");  
        TreeSet<String> keys = new TreeSet<>();  
        Map<String, JedisPool> clusterNodes = jc.getClusterNodes();  
        for(String k : clusterNodes.keySet()){  
            
            JedisPool jp = clusterNodes.get(k);  
            Jedis connection = jp.getResource();  
            
            try {  
                //keys.addAll(connection.keys("*"));  
            	logger.debug("Getting keys from: {},内容是:{}",k,connection.keys("*"));  
            } catch(Exception e){  
                logger.error("Getting keys error: {}", e);  
            } finally{  
                logger.debug("Connection closed.");  
                connection.close();//用完一定要close这个链接！！！  
            }  
        }
        System.out.println(keys);
	    
	}
}
