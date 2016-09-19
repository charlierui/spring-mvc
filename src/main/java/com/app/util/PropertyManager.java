

package com.app.util;
import java.util.*;
import java.io.*;
/**
 *
 * @author 陈瑞
 */
public class PropertyManager {
    private Properties properties = new Properties();
    File file;
    /** Creates a new instance of PropertyManager */
    public PropertyManager(String fileName) {
        String path = getClass().getResource("/").getPath();

        try{
            try{
                file = new File(path,fileName);
            }catch(Exception e){
                file = new File(path,"/"+fileName);
            }
            FileInputStream fis = new FileInputStream(file);
           
            properties.load(fis);
            fis.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String get(String key){
        return properties.getProperty(key.trim());
    }

    public String get(String key,String defaultValue){
        return properties.getProperty(key.trim(), defaultValue);
    }

    public void set(String key,String value){
        try{
            properties.setProperty(key,value);//new String(value.getBytes("UTF-8"), "UTF-8")
        }catch(Exception e){
            properties.setProperty(key, value);
            e.printStackTrace();
        }
    }
    
    public Properties getProperties(){
    	return properties;
    }

    synchronized public boolean store(){
        String path = getClass().getResource("/").getPath();
        boolean b = false;
        try{
            FileOutputStream fos = new FileOutputStream(file);
            properties.store(fos,"");
            fos.close();
            b = true;
        }catch(Exception e){
            b = false;
            e.printStackTrace();
        }
        file = null;
        return b;
    }


}
