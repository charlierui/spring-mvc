package com.app.dataswitch;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.core.Ordered;
  
public class DataSourceAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice, Ordered{  
	private Logger logger = LoggerFactory.getLogger(DataSourceAdvice.class);
	private Map<String,String> sourceMap;
	
    // service方法执行之前被调用  
    public void before(Method method, Object[] args, Object target) throws Throwable {  
    	String name = method.getName();
    	logger.debug("读写分离日志：切入点: " + target.getClass().getName() + "类中" + name + "方法");
    	boolean isSwitch = false;
    	for(String source :sourceMap.keySet()){
    		if(name.matches(sourceMap.get(source))){
    			//logger.debug("读写分离日志：切换到 "+source+"数据源");  
    			DataSourceSwitcher.setDataSource(source);
    			isSwitch = true;
    			break;
    		}
    	}
        if(!isSwitch){  
        	//logger.debug("读写分离日志：切换到只读数据源");
        	DataSourceSwitcher.clearDataSource(); 
        }  
    }  
  
    // service方法执行完之后被调用  
    public void afterReturning(Object arg0, Method method, Object[] args, Object target) throws Throwable {  
    }  
  
    // 抛出Exception之后被调用  
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {  
        DataSourceSwitcher.clearDataSource();
        logger.error("数据源读写分离日志：切换数据源时出现异常！",ex);
    }

	public void setSourceMap(Map<String, String> sourceMap) {
		this.sourceMap = sourceMap;
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}
}