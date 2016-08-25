package com.baidu.ueditor.hunter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.common.FtpUploadUtil;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.PathFormat;

public class FileManager {
	private static final Logger logger = LoggerFactory.getLogger(FtpUploadUtil.class);

	private static Properties p;
	static{
		  p=new Properties();
		InputStream proFile= FtpUploadUtil.class.getClassLoader().getResourceAsStream("ftpConfig.properties");
		try {
			p.load(proFile);
		} catch (IOException e) {
			logger.error("load proFile Error");
		}
	}
	private String dir = null;
	private String rootPath = null;
	private String[] allowFiles = null;
	private int count = 0;
	
	public FileManager ( Map<String, Object> conf ) {

		this.rootPath = (String)conf.get( "rootPath" );
		this.dir = this.rootPath + (String)conf.get( "dir" );
		this.allowFiles = this.getAllowFiles( conf.get("allowFiles") );
		this.count = (Integer)conf.get( "count" );
		
	}
	
	public State listFile ( int index ) {
		
		File dir = new File( this.dir );
		State state = null;

		if ( !dir.exists() ) {
			return new BaseState( false, AppInfo.NOT_EXIST );
		}
		
		if ( !dir.isDirectory() ) {
			return new BaseState( false, AppInfo.NOT_DIRECTORY );
		}
		
		Collection<File> list = FileUtils.listFiles( dir, this.allowFiles, true );
		
		if ( index < 0 || index > list.size() ) {
			state = new MultiState( true );
		} else {
			Object[] fileList = Arrays.copyOfRange( list.toArray(), index, index + this.count );
			state = this.getState( fileList );
		}
		
		state.putInfo( "start", index );
		state.putInfo( "total", list.size() );
		
		return state;
		
	}
	
	private State getState ( Object[] files ) {
		
		MultiState state = new MultiState( true );
		BaseState fileState = null;
		
		File file = null;
		
		for ( Object obj : files ) {
			if ( obj == null ) {
				break;
			}
			file = (File)obj;
			fileState = new BaseState( true );
			fileState.putInfo( "url", PathFormat.format( this.getPath( file ) ) );
			state.addState( fileState );
		}
		
		return state;
		
	}
	//在线管理路径返回
	private String getPath ( File file ) {
		
		// String path = file.getAbsolutePath();  
		 String fileName=file.getName();
		 
		// System.out.println("old:"+path);   
		
		 //String str=path.replace(this.rootPath.replaceAll("\\/", "\\\\"), "\\" );    
		
		 //System.out.println("new:"+str);    
		
		 
		 return p.getProperty("ftp.ShowHost")+"/app/imagesjkzx/"+fileName;
		
	}
//源码方法
//private String getPath ( File file ) {
//		
//		String path = file.getAbsolutePath();
//		
//		return path.replace( this.rootPath, "/" );
//		
//	}
	private String[] getAllowFiles ( Object fileExt ) {
		
		String[] exts = null;
		String ext = null;
		
		if ( fileExt == null ) {
			return new String[ 0 ];
		}
		
		exts = (String[])fileExt;
		
		for ( int i = 0, len = exts.length; i < len; i++ ) {
			
			ext = exts[ i ];
			exts[ i ] = ext.replace( ".", "" );
			
		}
		
		return exts;
		
	}
	
}
