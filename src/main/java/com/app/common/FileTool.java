package com.app.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTool { 
	private static final Logger log = LoggerFactory.getLogger(FileTool.class);
    private static Properties p;
	static{
		  p=new Properties();
		InputStream proFile= FtpUploadUtil.class.getClassLoader().getResourceAsStream("ftpConfig.properties");
		try {
			p.load(proFile);
		} catch (IOException e) {
			log.error("load proFile Error");
		}
	}
    /**
     * 创建目录
     * 
     * @param dir 目录
     */
    public static boolean mkdir(String path) {   
        File file = null;   
        try {   
            file = new File(path);   
            if (!file.exists()) {   
                return file.mkdirs();   
            }   
        } catch (RuntimeException e) {   
            throw e;
        } finally {   
            file = null;   
        }   
        return false;   
    }


    /**
     * 新建文件
     * 
     * @param fileName
     *            String 包含路径的文件名 如:E:\phsftp\src\123.txt
     * @param content
     *            String 文件内容
     *            
     */
    public static void createNewFile(String fileName, String content) {
        try {
            String fileNameTemp = fileName;
            File filePath = new File(fileNameTemp);
         
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            String strContent = content;
            pw.println(strContent);
            pw.flush();
            pw.close();
            fw.close();
        } catch (Exception e) {
            log.error("新建文件操作出错: "+e.getMessage());
            e.printStackTrace();
        }

    }
    
    /**
     * 删除文件
     * 
     * @param fileName 包含路径的文件名
     */
    public static void delFile(String fileName) {
        try {
            String filePath = fileName;
            java.io.File delFile = new java.io.File(filePath);
            delFile.delete();
        } catch (Exception e) {
            log.error("删除文件操作出错: "+e.getMessage());
            e.printStackTrace();
        }
    }
    

    /**
     * 删除文件夹
     * 
     * @param folderPath  文件夹路径
     */
    public static void delFolder(String folderPath) {
        try {
            // 删除文件夹里面所有内容
            delAllFile(folderPath); 
            String filePath = folderPath;
            java.io.File myFilePath = new java.io.File(filePath);
            // 删除空文件夹
            myFilePath.delete(); 
        } catch (Exception e) {
            log.error("删除文件夹操作出错"+e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 删除文件夹里面的所有文件
     * 
     * @param path 文件夹路径
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] childFiles = file.list();
        File temp = null;
        for (int i = 0; i < childFiles.length; i++) {
            //File.separator与系统有关的默认名称分隔符
            //在UNIX系统上，此字段的值为'/'；在Microsoft Windows系统上，它为 '\'。
            if (path.endsWith(File.separator)) {
                temp = new File(path + childFiles[i]);
            } else {
                temp = new File(path + File.separator + childFiles[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + childFiles[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + childFiles[i]);// 再删除空文件夹
            }
        }
    }

    
    /**
     * 复制单个文件
     * 
     * @param srcFile
     *            包含路径的源文件 如：E:/phsftp/src/abc.txt
     * @param dirDest
     *            目标文件目录；若文件目录不存在则自动创建  如：E:/phsftp/dest
     * @throws IOException
     */
    public static void copyFile(String srcFile, String dirDest) {
        try {
            FileInputStream in = new FileInputStream(srcFile);
            mkdir(dirDest);
            FileOutputStream out = new FileOutputStream(dirDest+"/"+new File(srcFile).getName());
            int len;
            byte buffer[] = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.close();
            in.close();
        } catch (Exception e) {
            log.error("复制文件操作出错:"+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 复制文件夹
     * 
     * @param oldPath
     *            String 源文件夹路径 如：E:/phsftp/src
     * @param newPath
     *            String 目标文件夹路径 如：E:/phsftp/dest
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            // 如果文件夹不存在 则新建文件夹
            mkdir(newPath);
            File file = new File(oldPath);
            String[] files = file.list();
            File temp = null;
            for (int i = 0; i < files.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + files[i]);
                } else {
                    temp = new File(oldPath + File.separator + files[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath
                            + "/" + (temp.getName()).toString());
                    byte[] buffer = new byte[1024 * 2];
                    int len;
                    while ((len = input.read(buffer)) != -1) {
                        output.write(buffer, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + files[i], newPath + "/" + files[i]);
                }
            }
        } catch (Exception e) {
            log.error("复制文件夹操作出错:"+e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 移动文件到指定目录
     * 
     * @param oldPath 包含路径的文件名 如：E:/phsftp/src/ljq.txt
     * @param newPath 目标文件目录 如：E:/phsftp/dest
     */
    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }
    
    /**
     * 移动文件到指定目录，不会删除文件夹
     * 
     * @param oldPath 源文件目录  如：E:/phsftp/src
     * @param newPath 目标文件目录 如：E:/phsftp/dest
     */
    public static void moveFiles(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delAllFile(oldPath);
    }
    
    /**
     * 移动文件到指定目录，会删除文件夹
     * 
     * @param oldPath 源文件目录  如：E:/phsftp/src
     * @param newPath 目标文件目录 如：E:/phsftp/dest
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }
    
     
    
    
    /**
     * 读取数据
     * 
     * @param inSream
     * @param charsetName
     * @return
     * @throws Exception
     */
    public static String readData(InputStream inSream, String charsetName) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while( (len = inSream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inSream.close();
        return new String(data, charsetName);
    }
    
    /**
     * 一行一行读取文件，适合字符读取，若读取中文字符时会出现乱码
     * 
     * @param path
     * @return
     * @throws Exception
     */
    public static Set<String> readFile(String path) throws Exception{
        Set<String> datas=new HashSet<String>();
        FileReader fr=new FileReader(path);
        BufferedReader br=new BufferedReader(fr);
        String line=null;
        while ((line=br.readLine())!=null) {
            datas.add(line);
        }
        br.close();
        fr.close();
        return datas;
    } /*
	  * 查找指定文件加下所有文件
	  */
	 public static  String[] GetFileNames() throws IOException{ 
	 String path =p.getProperty("realPath");
	  File rootDir = new File(path);
	   if (!rootDir.exists()) {
		   rootDir.mkdirs();  
       }
	  
	   String[] fileList =  rootDir.list();
	   String returnPath[]=new String[fileList.length];
	   
	   for (int i = 0; i < fileList.length; i++) {
		     path = rootDir.getAbsolutePath()+"\\"+fileList[i]; 
		     returnPath[i]=path; 
		     
	   }
	   if(fileList.length>0){
		   return returnPath;
	   }else{
		   return null;    
	   }
	
	 }
	
}