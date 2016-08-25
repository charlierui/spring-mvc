package com.app.util;


//用encodeBase64加解密放到cookie判断登陆

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;

public class CookieUtils {
  
  /**
   * 编码
   * @param cookieStr
   * @return
   */
  public static String encodeBase64(String cookieStr){
      
      try {
          cookieStr = new String(Base64.encodeBase64(cookieStr.getBytes()));
      } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
      return cookieStr;
  }

  /**
   * 解码
   * @param cookieStr
   * @return
   */
  public static String decodeBase64(String cookieStr){
      try {
          cookieStr = new String(Base64.decodeBase64(cookieStr.getBytes()));
      } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
      return cookieStr;
  }

  public static void main(String[] args) throws UnsupportedEncodingException {
      String str = "ffdd订单";
      String decode = new String(Base64.encodeBase64(str.getBytes()));    
      System.out.println("eee" + decode);
      
      String ddd = new String(Base64.decodeBase64(decode));
      System.out.println("ddd" + ddd);
  }
}



