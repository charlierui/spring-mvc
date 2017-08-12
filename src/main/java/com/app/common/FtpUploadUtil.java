package com.app.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletOutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.app.util.StringUtils;

public class FtpUploadUtil {
	private static final Logger logger = LoggerFactory.getLogger(FtpUploadUtil.class);
	private static Properties p;
	public static final  int MODULE_DOCTOR =0;
	public static final  int MODULE_RESIDENT =1; 
	public static final  int MODULE_COMMON = 2;

	public static final  int FILE_TYPE_VIDEO =4;
	public static final  int FILE_TYPE_DOCUMENT =3;
	public static final  int FILE_TYPE_AUDIO =2;
	public static final  int FILE_TYPE_IMG =1;
	
	public static final int NEWS_TYPE_HANDBOOK = 0;
	public static final int NEWS_TYPE_POLICY = 1;
	public static final int NEWS_TYPE_NOTICE =2;
	public static final int NEWS_TYPE_SKILLS =3;
	public static final int NEWS_TYPE_EDU =4;
	static{
		  p=new Properties();
		InputStream proFile= FtpUploadUtil.class.getClassLoader().getResourceAsStream("ftpConfig.properties");
		try {
			p.load(proFile);
		} catch (IOException e) {
			logger.error("load proFile Error");
		}
	}
	public FtpUploadUtil(){
		throw new Error("Not New This");
	}
	
	/**
	 * 默认连接，无参数
	 * @return
	 * @throws IOException
	 */
	public static FTPClient connect() throws IOException{
//		Properties p=new Properties();
//		InputStream proFile= FtpUploadUtil.class.getClassLoader().getResourceAsStream("ftpConfig.properties");
//		// 获取配置文件
//		p.load(proFile);
		String host=p.getProperty("ftp.host");
		String userName=p.getProperty("ftp.userName");
		String pwd=p.getProperty("ftp.pwd");
		String basePath=p.getProperty("ftp.basePath");
		int port =Integer.parseInt( String.valueOf( p.getProperty("port")==null?21:p.getProperty("port")));
		 FTPClient ftp = new FTPClient();    
	        int reply;    
	        ftp.setControlEncoding("UTF-8");
	        ftp.connect(host,port);  
	        ftp.login(userName,pwd.trim());
	        ftp.setBufferSize(1024);
	        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
	       
	        ftp.enterLocalPassiveMode();
	        // 模式 分为主动与被动  主动模式可以穿透425 但是会产生501
	        // 被动模式 容易出现425 
	        // 原因是 端口开放问题
//	        ftp.enterLocalActiveMode();
	        reply = ftp.getReplyCode();    
	        System.out.println(reply);
	        if (!FTPReply.isPositiveCompletion(reply)) {   
	        	logger.error("账号密码不对:"+userName+"@"+pwd);
	            ftp.disconnect();    
	            return null;    
	        }    
	        ftp.changeWorkingDirectory(basePath);    
	        
	        return ftp;    
		 
	}
	
	/** 
     *  
     * @param path 根目录 默认空  
     * @param addr 地址 
     * @param port 端口号 
     * @param username 用户名 
     * @param password 密码 
     * @return  一个带连接客户端
     * @throws Exception 
     */  
	private static  FTPClient connect(String path,String addr,int port,String username,String password) throws Exception {    
           
        FTPClient ftp = new FTPClient();    
        int reply;    
        ftp.connect(addr,port);  
        ftp.login(username,password);
        ftp.setBufferSize(1024);
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);    
        ftp.enterLocalPassiveMode();
        reply = ftp.getReplyCode();    
        if (!FTPReply.isPositiveCompletion(reply)) {   
        	logger.error("账号密码不对:"+username+"@"+password);
            ftp.disconnect();    
            return null;    
        }    
        ftp.changeWorkingDirectory(path);    
        
        return ftp;    
    }    
    
 
   
    /**
     * ftp上传方法 
     * @param module
     * @param biz
     * @param fileType
     * @param b
     * @param fromFileName 来自前台的后辍
     * @return
     */
    public static String ftpUpload(int module,String biz,int fileType,byte[] b,String fromFileName){
    	
    	if(fromFileName ==null || "".equals(fromFileName)){
    		logger.error("File  Ext  Is Null");;
    		return "F";
    	}

    	InputStream file = new ByteArrayInputStream(b);
    	String path="",fileName="";
    	boolean isUploadOver=false;
		FTPClient ftpClient = null;
		
    	try {
    		ftpClient=connect();
			if(ftpClient==null){
				return "F";
			}

			path=buildPathToFtp(module,biz,fileType );
			 
			boolean ready=pwd(path,ftpClient);
			fileName=System.currentTimeMillis()+"."+fromFileName;
			if(ready){
				isUploadOver=ftpClient.storeFile(fileName, file);
			}
		
			close(file,ftpClient);
     	} catch (Exception e) {
			return "F";
		}finally{
			close(file,ftpClient);
		}
    	if(isUploadOver){
     		return finishFilePath(path,fileName);
    	}else{
    		return "F";
    	}
    }
    
    /**
     * 上传方法  自动匹配文件路径
     * @param module
     * @param biz
     * @param b
     * @param fromFileName
     * @return
     */
    public static String ftpUpload(int module,String biz,byte[] b,String fromFileName){
    	if(fromFileName ==null || "".equals(fromFileName)){
    		logger.error("File  Ext  Is Null");;
    		return "F";
    	}

    	InputStream file = new ByteArrayInputStream(b);
    	String path="",fileName="";
		FTPClient ftpClient = null;
		boolean isUploadOver=false;
    	
		try {

    		//构建连接 自动创建目录
//			ftpClient=connect(basePath==null?"":basePath,host , 21, userName, pwd);
			ftpClient=connect();
			if(ftpClient==null){
				return "F";
			}
			int fileType=getFileType(fromFileName);
    		path=buildPathToFtp(module,biz,fileType);
			 
			boolean ready=pwd(path,ftpClient);
			fileName=System.currentTimeMillis()+"."+fromFileName;
			if(ready){
				isUploadOver=ftpClient.storeFile(fileName,file);
			}
			 
			close(file,ftpClient);
			
    	} catch (Exception e) {
			return "F";
		}finally{
			close(file,ftpClient);
		}
    	if(isUploadOver){
    		return finishFilePath(path,fileName);
    	}else{
    		return "F";
    	}
    }
    
     

    /**
     * 关闭资源
     * @param file
     * @param ftpClient
     */
    private static void close(InputStream file, FTPClient ftpClient) {
    	try {
			if(file!=null){
			file.close();
			}
			if(ftpClient!=null){
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			logger.error("File Close Error");
		}
	}

	/**
     * 将路径拼接为域名加路径
     * @param path
     * @param fileName
     * @return
     */
    private static String finishFilePath(String path, String fileName) {
		return (p.getProperty("ftp.ShowHost")+path+"/"+fileName).replace(" ", "");
	}
    
    
   
    
    /**
     * 删除方法
     * @param pathName 删除的路径
     * @return
     */
    public static boolean delFile(String pathName)  {
	   boolean isDel=false;
	   String host=p.getProperty("ftp.ShowHost");
	   try {
		   FTPClient ftp=connect();
		   isDel=ftp.dele(pathName.replace(host, ""))==250;
		   ftp.disconnect();
	   } catch (IOException e) {
		   isDel=false;
	   }
	   return isDel;
   }
    
   /**
    * 下载方法  UNOVER
    * @param module 模块名
 	* @param biz    业务名
 	* @param filePath 文件名
 	* @return
 	* @throws IOException
 	*/
    public static File downFileByFtp(int module,String biz,String filePath,ServletOutputStream out) throws IOException{
	   FTPClient ftp=connect();
//	   boolean b=ftp.changeWorkingDirectory(filePath.substring(filePath.lastIndexOf("/"), filePath.length()));
//	   if(!b){
//		   OutputStream out =new FileOutputStream(new File("E:/apa/down.jpg"));
		   boolean  ok= false; 
		   ok= ftp.retrieveFile(filePath.substring(filePath.indexOf(p.getProperty("ftp.host"))+p.getProperty("ftp.host").length(), filePath.length()), out);
		   System.out.println("OK:"+ok);
		   
//		   System.out.println(ok);
		   out.close();
	   	 
//		   boolean downOk=ftp.completePendingCommand();
//		   System.out.println("DOWN:"+downOk);
		   ftp.disconnect();
//	   } 
	   return null;
   }
	
    

   	/**
   	 * 拼装新闻公告等路径
   	 * @param moduleDoctor
   	 * @param fileType
   	 * @param FileName
   	 * @return
   	 */
   	public static String buildPathToNEWS(int module, int fileType  ){
   		StringBuilder path=new StringBuilder(50);
   		path.append("/news");
    	switch (module) {
		case MODULE_DOCTOR: path.append("/doctor"); break;
		case MODULE_RESIDENT: path.append("/resident"); break;
		case MODULE_COMMON: path.append("/common"); break;
		}
    	switch (fileType) {
		case NEWS_TYPE_HANDBOOK:     path.append("/handbook"); break;
		case NEWS_TYPE_POLICY :      path.append("/policy"); break;
		case NEWS_TYPE_NOTICE :    path.append("/notice"); break;
		case NEWS_TYPE_SKILLS :      path.append("/skills"); break;
		case NEWS_TYPE_EDU :      path.append("/edu"); break;
    	}
    	path.append("/"+ new SimpleDateFormat("yyyyMMdd").format(new Date()));
    	return path.toString();
   	}
   	public static void main(String[] args) throws Exception {
//   		String path=buildPathToFtp(MODULE_DOCTOR, "face", FILE_TYPE_IMG);
//   		FTPClient ftp=connect();
//   		System.out.println(pwd(path, ftp));
   			FtpUploadUtil.ftpUpload(1, "1", new byte[22], "ext");
   		//   		ftp.disconnect();
//   		System.out.println(p.getProperty("ftp.ShowHost")+path+"/消息类型.txt");
	}
  
	/**
	 * 匹配文件后辍
	 * @param file
	 * @return
	 */
	private  static int getFileType(String file) {
		int fileType=0;
		if(!".".equals( file.substring(0, 1))){
			file="."+file;
		}
		if (file.equals(".jpg") || file.equals(".gif") || file.equals(".png") || file.equals(".jpeg") || file.equals(".bmp")) {
			return fileType = FILE_TYPE_IMG;
		} else if (file.equals(".mp3") || file.equals(".wav") || file.equals(".wma") || file.equals(".ogg") || file.equals(".acc")) {
			return fileType = FILE_TYPE_AUDIO;
		} else if (file.equals(".avi") || file.equals(".wmv") || file.equals(".flv") || file.equals(".3gp") || file.equals(".f4v")) {
			return fileType = FILE_TYPE_VIDEO;
		} else if (file.equals(".txt") || file.equals(".doc") || file.equals(".docx")) {
			return fileType = FILE_TYPE_DOCUMENT;
		} else {
			return fileType;
		}
	}
	   
	/**
     * 创建PWD命令 进入工作目录
     * @param path 工作目录
     * @param ftpClient ftp客户端
     * @return
     */
    private static boolean pwd(String path, FTPClient ftpClient) {
		String[] paths=path.split("/"); 
		try {
			String tmpPath="";
			for(String p:paths){
				tmpPath += "/"+p;
				if(!ftpClient.changeWorkingDirectory(tmpPath)){
					logger.info("创建目录："+tmpPath);
					if(!ftpClient.makeDirectory(tmpPath)) return false;
				}
			}
			ftpClient.changeWorkingDirectory(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
     
 
    
     
    /**
     * 拼装路径 根据传递的参数
     * @param module     
     * @param biz      
     * @param fileType
     * @return
     * @throws IOException
     */
    private static String buildPathToFtp(int module, String biz, int fileType ) throws IOException {
    	StringBuilder path=new StringBuilder(50);
    	if(module==MODULE_DOCTOR){
    		path.append("/doctor");
    	}else if(module==MODULE_RESIDENT){
    		path.append("/resident");
    	}else{
    		path.append("/common");
    	}
    	if(biz !=null && !"".equals(biz)){
    		path.append("/"+biz);
    	}
    	switch (fileType) {
		case FILE_TYPE_AUDIO:     path.append("/audio"); break;
		case FILE_TYPE_DOCUMENT : path.append("/document"); break;
		case FILE_TYPE_VIDEO :    path.append("/video"); break;
		case FILE_TYPE_IMG :      path.append("/img"); break;
		default:			break;
		}
    	path.append("/"+new SimpleDateFormat("yyyyMMdd").format(new Date()));
    	return path.toString();
	}
    
    /**
     * 拼装路径  重載
     * @param String  路徑     
     * @return
     * @throws IOException
     */
    private static String buildPathToFtp(String ...parameters) throws IOException {
    	StringBuilder path=new StringBuilder(50);
    	for(String str : parameters){
    		path.append("/"+str);
    	}
    	//path.append("/"+new SimpleDateFormat("yyyyMMdd").format(new Date()));
    	return path.toString();
	}

    /**
	 * 上传文件
	 * @param file  文件体
	 * @param String 第一个参数  相关联的主键
	 * @param String 第二个参数  模块
	 * @param String 第三个参数  文件版本
	 * @return    完整文件url
	 * 			  
	 */
	public static String ftpUpload(MultipartFile fromFile, String[] parameters) {
 
		InputStream file =null;
    	String path="",fileName="";
    	boolean isUploadOver=false;
		FTPClient ftpClient = null;
		
		fileName=fromFile.getOriginalFilename();
		String preName = fileName.substring(0, fileName.lastIndexOf("."));
		String suffix = fileName.substring(fileName.lastIndexOf("."));
 
    	try {
    		byte[] b = fromFile.getBytes();
    		//System.out.println(b);
    		file = new ByteArrayInputStream(b);

    		ftpClient=connect();
			if(ftpClient==null){
				logger.info("ftp连接失败");
				return "F";
			}
			//app为apk根目录 
			path=buildPathToFtp("app",parameters[1] );
			 
			boolean ready=pwd(path,ftpClient);
			//fileName=System.currentTimeMillis()+"."+suffix;
			fileName = parameters[2]+suffix;
			if(ready){
				isUploadOver=ftpClient.storeFile(fileName, file);
			}
		
			close(file,ftpClient);
     	} catch (Exception e) {
			return "F";
		}finally{
			close(file,ftpClient);
		}
    	if(isUploadOver){
     		return finishFilePath(path,fileName);
    	}else{
    		logger.info("文件上传失败");
    		return "F";
    	}
	 
	}
	/**
	 * 上传文件     ueditor富文本编辑器专用
	 * @param file  文件体
	 * @param String 第一个参数  相关联的主键
	 * @param String 第二个参数  模块
	 * @param String 第三个参数  文件版本
	 * @return    完整文件url
	 *
	 */
	public static String ftpUpload(File file, String[] parameters) {
		FTPClient ftp=null;
		 FileInputStream input=null;
		String path="",fileName="";
		boolean isUploadOver=false;
		fileName=file.getName();
		String preName = fileName.substring(0, fileName.lastIndexOf("."));
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		try {
			ftp=connect();
			if(ftp==null){
				logger.info(" ueditor富文本,ftp连接失败");
				return "F";
			}
			if(file.isDirectory()){
	            ftp.makeDirectory(file.getName());                
	            ftp.changeWorkingDirectory(file.getName());      
	            String[] files = file.list();             
	            for (int i = 0; i < files.length; i++) {
	                File file1 = new File(file.getPath()+"\\"+files[i] );      
	                if(file1.isDirectory()){      
	                	ftpUpload(file1,parameters);      
	                    ftp.changeToParentDirectory();      
	                }else{
	                    File file2 = new File(file.getPath()+"\\"+files[i]);      
	                     input = new FileInputStream(file2);      
	                     
	                  //app为apk根目录 
	            		path=buildPathToFtp("app",parameters[1] );
	            		 
	            		boolean ready=pwd(path,ftp);
	            		//fileName=System.currentTimeMillis()+"."+suffix;
	            		fileName = preName+suffix;
	            		if(ready){
	            			isUploadOver=ftp.storeFile(file2.getName(), input);  
	            		}
	                    input.close();   
	                    close(input,ftp);
	                }                 
	            }
	           
	        }else{      
	        	logger.info("图片TOMCAT路径："+file.getPath());
	        	File file2 = new File(file.getPath());      
	             input = new FileInputStream(file2);      
	             
	          //app为apk根目录 
	    		path=buildPathToFtp("app",parameters[1] );
	    		 
	    		boolean ready=pwd(path,ftp);
	    		//fileName=System.currentTimeMillis()+"."+suffix;
	    		fileName = preName+suffix;
	    		if(ready){
	    			isUploadOver=ftp.storeFile(file2.getName(), input);  
	    		}else{
	    			logger.info("ueditor进入目录失败");
	    			return "F";
	    		}
	            input.close();   
	            
	        }
			
			logger.info("FTP图片上传路径："+finishFilePath(path,fileName));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 close(input,ftp);
			e.printStackTrace();
		}finally{
			 close(input,ftp);
		}
		if(isUploadOver){
     		return finishFilePath(path,fileName);
		}else{
			logger.info("ueditor文件上传失败");
			return "F";
		}
		
    	//return finishFilePath(path,fileName);
	}
//	 /**
//	  * 返回FTP目录下的文件列表
//	  * @param ftpDirectory
//	  * @return
//	  */
//	  public List<String> getFileNameList(String ftpDirectory) 
//	  { 
//			FTPClient ftp=null;
//
//	     List<String> list = new ArrayList<String>(); 
//	     try {
//				ftp=connect();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if(ftp==null){
//				 return list;
//			}
//	  
//	     try  
//	     { 
//	        DataInputStream dis = new  DataInputStream(ftp.nameList(ftpDirectory)); 
//	        String filename = ""; 
//	        while((filename=dis.readLine())!=null)   
//	        {
//	          list.add(filename);         
//	        }   
//	     } catch (Exception e)  
//	     { 
//	        e.printStackTrace(); 
//	     } 
//	     return list; 
//	  }
    /**  
	 @Deprecated
	public static String uploadFile(File file) throws Exception{
    	FileInputStream input;
    	Properties p=new Properties();
		InputStream proFile= FtpUploadUtil.class.getClassLoader().getResourceAsStream("ftpConfig.properties");
		p.load(proFile);
		String host=p.getProperty("ftp.host");
		String userName=p.getProperty("ftp.userName");
		String pwd=p.getProperty("ftp.pwd");
		try {
			input = new FileInputStream(file);
			FTPClient ftp=connect("", host, 21, userName, pwd);
			if(ftp==null){
			input.close();
			return "F";
			}
			ftp.storeFile(file.getName(), input);    
			input.close();   
		} catch (FileNotFoundException e) {
			return "F";
		} catch (IOException e) {
			return "F";
		}    
    	return "T";
    }
    
     * 上传文件方法,如果是文件夹则递归调用
     * 废弃
     * @param file 上传的文件或文件夹 
     * @throws Exception 
    private    void upload(File file) throws Exception{    
    	FTPClient ftp=connect("", "", 2, "", "");
        if(file.isDirectory()){  
        	// 创建文件夹
            ftp.makeDirectory(file.getName());
            // 进入文件夹
            ftp.changeWorkingDirectory(file.getName());  
            // 遍历文件夹下文件
            String[] files = file.list();           
            for (int i = 0; i < files.length; i++) {    
                File file1 = new File(file.getPath()+"\\"+files[i] );    
                if(file1.isDirectory()){    
                    upload(file1);    
                    ftp.changeToParentDirectory();    
                }else{                  
                    File file2 = new File(file.getPath()+"\\"+files[i]);    
                    FileInputStream input = new FileInputStream(file2);    
                    ftp.storeFile(file2.getName(), input);    
                    input.close();                          
                }               
            }    
        }else{    
            File file2 = new File(file.getPath());    
            FileInputStream input = new FileInputStream(file2);    
            ftp.storeFile(file2.getName(), input);    
            input.close();      
        }    
    }
     */  
    /**
     * 上传的主要接口
     * @param module   模块 int
     * @param biz      业务  String 
     * @param fileType 文件类型 int
     * @param file     待上传文件
     * @return        失败：F,正确：文件路径 
    private static String ftpUpload(int module,String biz,int fileType,File file){
    	String path="",fileName="";
		FTPClient ftpClient;
    	try {
    		//构建连接 自动创建目录
//			ftpClient=connect(basePath==null?"":basePath,host , 21, userName, pwd);
			ftpClient=connect();
    		path=buildPathToFtp(module,biz,fileType,ftpClient);
			 
			boolean ready=pwd(path,ftpClient);
			fileName=System.currentTimeMillis()+"_"+file.getName();
			if(ready){
				ftpClient.storeFile(fileName, new FileInputStream(file));
			}
			ftpClient.logout();
			ftpClient.disconnect();
			
    	} catch (Exception e) {
			return "F";
		}
    	return "static"+path+"/"+fileName;
    }
     */
    /**
     * 上传方法  自动匹配文件类型 
     * @param module   模块 0 医生  1患者
     * @param biz
     * @param file
     * @return
    public static String ftpUpload(int module,String biz,File file){
    	String path="",fileName="";
		FTPClient ftpClient;
    	try {

    		//构建连接 自动创建目录
//			ftpClient=connect(basePath==null?"":basePath,host , 21, userName, pwd);
			ftpClient=connect();
			int fileType=getFileType(file.getName());
    		path=buildPathToFtp(module,biz,fileType);
			 
			boolean ready=pwd(path,ftpClient);
			fileName=System.currentTimeMillis()+"_"+file.getName();
			if(ready){
//				ftpClient.storeFile(fileName, new FileInputStream(file));
			}
			ftpClient.logout();
			ftpClient.disconnect();
			
    	} catch (Exception e) {
			return "F";
		}
    	return "static"+path+"/"+fileName;
    }
     */
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	  public static String uploadFileTOLocal(MultipartFile file  ) {
	      String returnPath ="";  
		  String realPath =p.getProperty("realPath");
	        String fileName = file.getOriginalFilename();
	        InputStream is= null;; 
	        FileOutputStream os = null;
			try {
				is = file.getInputStream(); 
				returnPath=realPath+"/"+ new Date().getTime() + fileName; 
				is = file.getInputStream();
				os = new FileOutputStream(returnPath);
				int i = 0;
			     while ((i = is.read()) != -1) {
			            os.write(i);
			        }
			        os.flush();
			        os.close();
			        is.close();
			} catch (IOException e) {
		        logger.error("本地上传失败");
		        return "";
			} 
	        return returnPath;
	    }

 
	  
	  /**
	   * 上传图片到服务器
	   * @param MultipartFile file
	   * @param param entity的名字标记
	   * @return 
	   */
	public static String uploadFileTOLocal(MultipartFile file, String [] param,String entityName ) {
 
		      String returnPath ="";  
			  String realPath =p.getProperty("realPath");
	        String fileName = file.getOriginalFilename();
	        //后缀名
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			
	        InputStream is= null;; 
	        FileOutputStream os = null;
	        //根据参数组成文件名
	        //
	        if(entityName.indexOf(".")>-1){
	        	entityName =StringUtils.substringAfterLast(entityName, ".");
	        }
	        String  strParam=param[0]+"_"+entityName;
	        
			try {
				FileTool.mkdir(realPath);	  
				is = file.getInputStream(); 
				returnPath=realPath+"/"+ strParam + suffix; 
				is = file.getInputStream();
				os = new FileOutputStream(returnPath);
				int i = 0;
			     while ((i = is.read()) != -1) {
			            os.write(i);
			        }
			        os.flush();
			        os.close();
			        is.close();
			} catch (IOException e) {
		        logger.error("本地上传失败");
		        e.printStackTrace();
		        return "";
			} 
	        return returnPath;
	    
	}
	
	
}
