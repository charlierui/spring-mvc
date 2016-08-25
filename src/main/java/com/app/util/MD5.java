package com.app.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MD5 {
	// 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * 返回形式为数字跟字符串
     * 
     * @param bByte
     * @return
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    /**
     * 转换字节数组为16进制字串
     * 
     * @param bByte
     * @return
     */
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * @param strObj
     * @return
     */
    public static String getMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
	
    
    /**
     * @param strObj
     * @return
     */
    public static String GetMD5CodeToUpper(String strObj){
    	return getMD5Code(strObj).toUpperCase();
    }
    
    /**
     * @param strObj
     * @return
     */
    public static String GetMD5CodeToLower(String strObj){
    	return getMD5Code(strObj).toLowerCase();
    }
    
    /**
     * @param text
     * @return
     */
    public static String MD5(String text) {
    	StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
	    	byte b[] = md.digest();
	    	int i;
	    	for (int offset = 0; offset < b.length; offset++) {
	    		i = b[offset];
	    		if(i<0) i+= 256;
	    		if(i<16)
	    		buf.append("0");
	    		buf.append(Integer.toHexString(i));
	    	}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	return buf.toString();
    }
 
    /**
     * @param text
     * @return
     */
    public static String MD5Digest(String... arr) {
		try {
			Arrays.sort(arr);
		    StringBuilder sb = new StringBuilder();
		    for (String a : arr) {
		      sb.append(a);
		    }
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sb.toString().getBytes());
			return new BigInteger(1, md.digest()).toString(16); 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	return "";
    }
    
    /**
     * 串接arr参数，生成sha1 digest
     *
     * @param arr
     * @return
     */
    public static String genSHA1(String... arr) throws NoSuchAlgorithmException {
      Arrays.sort(arr);
      StringBuilder sb = new StringBuilder();
      for (String a : arr) {
        sb.append(a);
      }
      return SHA1(sb.toString());
    }

    /**
     * 用&串接arr参数，生成sha1 digest
     *
     * @param arr
     * @return
     */
    public static String genWithAmpleSHA1(String... arr) throws NoSuchAlgorithmException {
      Arrays.sort(arr);
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < arr.length; i++) {
        String a = arr[i];
        sb.append(a);
        if (i != arr.length - 1) {
          sb.append('&');
        }
      }
      return SHA1(sb.toString());
    }

    public static String SHA1(String text) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(text.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
 
    public static String SHA(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /**
     * 加密
     *
     * @param content
     *            需要加密的内容
     * @param password
     *            加密密码
     * @return
     */
    public static byte[] encryptAES(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /**
     * 解密
     *
     * @param content
     *            待解密内容
     * @param password
     *            解密密钥
     * @return
     */
    public static byte[] decryptAES(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(MD5("sys.post.token"));
	}
}

