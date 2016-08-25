package com.baidu.ueditor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.hunter.FileManager;
import com.baidu.ueditor.hunter.ImageHunter;
import com.baidu.ueditor.upload.Uploader;

public class ActionEnter {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ActionEnter.class);

	private HttpServletRequest request = null;
	
	private String rootPath = null;
	private String contextPath = null;
	
	private String actionType = null;
	
	private ConfigManager configManager = null;

	public ActionEnter ( HttpServletRequest request, String rootPath ) {
		
		this.request = request;
		this.rootPath = rootPath;
		this.actionType = request.getParameter( "action" );
		this.contextPath = request.getContextPath();
		this.configManager = ConfigManager.getInstance( this.rootPath, this.contextPath, request.getRequestURI() );
		
	}
	
	public String exec () throws JSONException {
		
		String callbackName = this.request.getParameter("callback");
		
		if ( callbackName != null ) {

			if ( !validCallbackName( callbackName ) ) {
				return new BaseState( false, AppInfo.ILLEGAL ).toJSONString();
			}
			
			return callbackName+"("+this.invoke()+");";
			
		} else {
			return this.invoke();
		}

	}
	
	public String invoke() throws JSONException {
		
		if ( actionType == null || !ActionMap.mapping.containsKey( actionType ) ) {
			return new BaseState( false, AppInfo.INVALID_ACTION ).toJSONString();
		}
		
		if ( this.configManager == null || !this.configManager.valid() ) {
			return new BaseState( false, AppInfo.CONFIG_ERROR ).toJSONString();
		}
		
		State state = null;
		
		int actionCode = ActionMap.getType( this.actionType );
		
		Map<String, Object> conf = null;
		
		switch ( actionCode ) {
		
			case ActionMap.CONFIG:
				return this.configManager.getAllConfig().toString();
				
			case ActionMap.UPLOAD_IMAGE:
			case ActionMap.UPLOAD_SCRAWL:
			case ActionMap.UPLOAD_VIDEO:
			case ActionMap.UPLOAD_FILE:
				conf = this.configManager.getConfig( actionCode );
				//用户ftp上传
				conf.put("useFtpUpload",this.configManager.getAllConfig().getString("useFtpUpload"));
			    conf.put("keepLocalFile",this.configManager.getAllConfig().getString("keepLocalFile"));
				state = new Uploader( request, conf ).doExec();
				break;
				
			case ActionMap.CATCH_IMAGE:
				conf = configManager.getConfig( actionCode );
				String[] list = this.request.getParameterValues( (String)conf.get( "fieldName" ) );
				state = new ImageHunter( conf ).capture( list );
				break;
				
			case ActionMap.LIST_IMAGE:
			case ActionMap.LIST_FILE:
				conf = configManager.getConfig( actionCode );
				int start = this.getStartIndex();
				state = new FileManager( conf ).listFile( start );
				break;
				
		}
		logger.info("最终给前台JSON："+state.toJSONString());
		return state.toJSONString();
		
	}
	
	public int getStartIndex () {
		
		String start = this.request.getParameter( "start" );
		
		try {
			return Integer.parseInt( start );
		} catch ( Exception e ) {
			return 0;
		}
		
	}
	
	/**
	 * callback参数验证
	 */
	public boolean validCallbackName ( String name ) {
		
		if ( name.matches( "^[a-zA-Z_]+[\\w0-9_]*$" ) ) {
			return true;
		}
		
		return false;
		
	}
	
}