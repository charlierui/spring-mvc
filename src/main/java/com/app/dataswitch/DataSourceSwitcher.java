package com.app.dataswitch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class DataSourceSwitcher {
	private static Logger logger = LoggerFactory.getLogger(DataSourceSwitcher.class);

    private static final ThreadLocal contextHolder = new ThreadLocal();  
  
    public static void setDataSource(String dataSource) {  
        Assert.notNull(dataSource, "dataSource cannot be null");  
        //logger.info("当前数据源"+dataSource);
        contextHolder.set(dataSource);  
    }  
      
    public static String getDataSource() {  
    	//logger.info("获取："+(String) contextHolder.get());
        return (String) contextHolder.get();  
    }  
  
    public static void clearDataSource() {
        contextHolder.remove();  
    }  
}
