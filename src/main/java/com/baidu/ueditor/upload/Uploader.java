package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.State;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class Uploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;

	public Uploader(HttpServletRequest request, Map<String, Object> conf) {
		this.request = request;
		this.conf = conf;
	}

	public final State doExec() {
		String filedName = (String) this.conf.get("fieldName");
		State state = null;
		//涂鸦图片上传
		if ("true".equals(this.conf.get("isBase64"))) {
			state = Base64Uploader.save(this.request.getParameter(filedName),
					this.conf);
		} else {
			//ftp图片上传
			 if("true".equals(this.conf.get("useFtpUpload"))){
	              state = FtpUploadUtilbaidu.save(request, conf);
			 } else{
				//原始图片上传
			state = BinaryUploader.save(this.request, this.conf);
			 }
		}

		return state;
	}
}
