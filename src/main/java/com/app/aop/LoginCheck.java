package com.app.aop;
import java.lang.annotation.*;  

/** 
 *自定义注解 拦截service 
 */  
  
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public  @interface LoginCheck {  
  
    boolean description()  default true;  
    
  
  
}  