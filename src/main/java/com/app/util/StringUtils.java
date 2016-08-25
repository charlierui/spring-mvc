package com.app.util;

/**
 * 字符串工具类
 * 
 *
 */
public final class StringUtils {
     
    /**
     * 默认的空值
     */
    public static final String EMPTY = "";
 
    /**
     * 检查字符串是否为空
     * @param str 字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        } else if (str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }
     
    /**
     * 检查字符串是否为空
     * @param str 字符串
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    public static boolean isEmpty(Object str) {
    	 if (str == null) {
             return true;
         }  else {
             return false;
         }
    }
    
    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }
    /**
     * 截取并保留标志位之前的字符串
     * @param str 字符串
     * @param expr 分隔符
     * @return
     */
    public static String substringBefore(String str, String expr) {
        if (isEmpty(str) || expr == null) {
            return str;
        }
        if (expr.length() == 0) {
            return EMPTY;
        }
        int pos = str.indexOf(expr);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }
 
    /**
     * 截取并保留标志位之后的字符串
     * @param str 字符串
     * @param expr 分隔符
     * @return
     */
    public static String substringAfter(String str, String expr) {
        if (isEmpty(str)) {
            return str;
        }
        if (expr == null) {
            return EMPTY;
        }
        int pos = str.indexOf(expr);
        if (pos == -1) {
            return EMPTY;
        }
        return str.substring(pos + expr.length());
    }
 
    /**
     * 截取并保留最后一个标志位之前的字符串
     * @param str 字符串
     * @param expr 分隔符
     * @return
     */
     public static String substringBeforeLast(String str, String expr) {
            if (isEmpty(str) || isEmpty(expr)) {
                return str;
            }
            int pos = str.lastIndexOf(expr);
            if (pos == -1) {
                return str;
            }
            return str.substring(0, pos);
        }
      
     /**
      * 截取并保留最后一个标志位之后的字符串
      * @param str
      * @param expr 分隔符
      * @return
      */
     public static String substringAfterLast(String str, String expr) {
            if (isEmpty(str)) {
                return str;
            }
            if (isEmpty(expr)) {
                return EMPTY;
            }
            int pos = str.lastIndexOf(expr);
            if (pos == -1 || pos == (str.length() - expr.length())) {
                return EMPTY;
            }
            return str.substring(pos + expr.length());
        }
      
     /**
      * 把字符串按分隔符转换为数组
      * @param string 字符串
      * @param expr 分隔符
      * @return
      */
     public static String[] stringToArray(String string, String expr){
         return string.split(expr);
     }
      
     /**
      * 去除字符串中的空格
      * @param str
      * @return
      */
     public static String noSpace(String str){
         str = str.trim();
         str = str.replace(" ", "_");
         return str;
     }
}