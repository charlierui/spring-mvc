package com.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.aop.LoginCheck;
import com.app.aop.SystemControllerLog;
import com.app.dto.TuserDto;
import com.app.service.tuser.TUserServInte;
import com.app.util.BaseController;
import com.app.util.StringUtils;
@Controller
@RequestMapping(value="/authcon")
public class AuthController extends BaseController {
	@Autowired
	private TUserServInte tuserserv;
	
	
	
	
	
	@LoginCheck(description = true)
	@RequestMapping(value = "/authindex")
	@SystemControllerLog(description = "权限分配页面")
	public String authindex(){
		return "authlist/authlist";
	}
	@LoginCheck(description = true)
	@RequestMapping(value = "/listAllUser")
	@SystemControllerLog(description = "权限分配用户列表查询")
	@ResponseBody
	public void listAllUser(TuserDto tus,HttpServletResponse response){
		Map<String,Object> map=new HashMap<String,Object>();
		String deptid = null,name=null;
		map.put("deptid", tus.getDeptid());
		map.put("name", tus.getName());

		response_write(tuserserv.selectAll(map), response);
	}
}
