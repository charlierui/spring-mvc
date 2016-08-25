package com.app.controller;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.aop.LoginCheck;
import com.app.aop.SystemControllerLog;
import com.app.exception.SystemException;
import com.app.model.TUser;
import com.app.redis.SerializeUtil;
import com.app.util.BaseController;
import com.app.util.CookieUtils;

@Controller
@RequestMapping(value="indexcon")
public class indexController extends BaseController{
	@RequestMapping(value = "index")
	@SystemControllerLog(description = "进入登录页面")
	public ModelAndView index(Model model,HttpServletRequest request) throws SystemException {
		String name = "";
		String pass = ""; 
		HashMap<String,Object> map=null;
		try {
			map = new HashMap<String, Object>();
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("liangyiuserName")) {
						name = cookies[i].getValue();
						map.put("username", CookieUtils.decodeBase64(name));
					}
					if (cookies[i].getName().equals("liangyipassword")) {
						pass = cookies[i].getValue();
						map.put("passw", CookieUtils.decodeBase64(pass));
					}
				}
			} 
		} catch (Exception e) {
		    throw new SystemException("系统异常");
		}
	return new ModelAndView("index",map);
	}
	@LoginCheck(description=true)
	@RequestMapping(value = "main")
	@SystemControllerLog(description = "进入main页面")
	public String main(Model model,TUser tus) {
//		TUser redisuser = (TUser) SerializeUtil.unserialize(this.get("user".getBytes()));
//		model.addAttribute("user", redisuser);
		return "main";
		
	}
}
