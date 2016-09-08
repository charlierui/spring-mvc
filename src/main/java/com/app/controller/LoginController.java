package com.app.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.aop.SystemControllerLog;
import com.app.exception.SystemException;
import com.app.model.TUser;
import com.app.redis.RedisCache;
import com.app.redis.SerializeUtil;
import com.app.service.tuser.TUserServInte;
import com.app.util.BaseController;
import com.app.util.CookieUtils;
import com.app.util.MD5;
import com.app.util.StringUtils;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping(value = "/logincon")
public class LoginController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	private static ThreadLocal<LoginController> connThreadLocal = new ThreadLocal<LoginController>(); 

	@Autowired
	private TUserServInte tuserserv;
	
	/**
	 * 登录
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/login")
	@SystemControllerLog(description = "后台用户登录")
	public void userLogin(TUser tus, String saveCookie, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			if (StringUtils.isNotEmpty(saveCookie)) {
				if (saveCookie.equals("yes")) {// 如果选择了记住用户名和密码，写入cookie
					Cookie cookie = new Cookie("liangyiuserName", CookieUtils.encodeBase64(tus.getUsername()));
					cookie.setMaxAge(864000);
					cookie.setPath(request.getContextPath());
					response.addCookie(cookie);
					cookie = new Cookie("liangyipassword", CookieUtils.encodeBase64(tus.getPsw()));
					cookie.setMaxAge(864000);
					cookie.setPath(request.getContextPath());
					response.addCookie(cookie);
				}
			}
			TUser redisuser = (TUser) SerializeUtil.unserialize(this.get(this.findcookie(request).getBytes()));
			
			if (redisuser != null) {
				if (redisuser.getStatus() == TUser.USER_STATUS_LOCKED) {
					response_write(getRM(UNSUCCESS, "该用户已锁定，请与管理员联系。"), response);
				} else if (!redisuser.getPsw().equals(MD5.MD5(tus.getPsw()))) {
					response_write(getRM(UNSUCCESS, "密码错误"), response);
				} else {
					response_write(getRM(SUCCESS, "登录成功"), response);
				}
			} else {
				TUser tu = tuserserv.selectByLogin(tus);
				if (tu == null || tu.getId() == 0) {
					response_write(getRM(UNSUCCESS, "登陆失败，用户名不存在"), response);
				} else if (tu.getStatus() == TUser.USER_STATUS_LOCKED) {
					response_write(getRM(UNSUCCESS, "该用户已锁定，请与管理员联系。"), response);
				} else if (!tu.getPsw().equals(MD5.MD5(tus.getPsw()))) {
					response_write(getRM(UNSUCCESS, "密码错误"), response);
				} else {
					logger.info("缓存失效，重新添加缓存");
					this.setex(request.getSession().getId().getBytes(), 3600, SerializeUtil.serialize(tu));
					Cookie cookieuser = new Cookie("liangyicookie", request.getSession().getId());
					cookieuser.setMaxAge(3600);
					cookieuser.setPath(request.getContextPath());
					response.addCookie(cookieuser);
					// this.set("user".getBytes(), SerializeUtil.serialize(tu));
					// model.addAttribute("user", tu);
					// addSessionWebUser("user", tu);
					
					response_write(getRM(SUCCESS, "登录成功"), response);
				}

			}
			// throw new RuntimeException("aaaaaaaaaaaa");

		} catch (Exception e) {
			response_write(getRM(UNSUCCESS, "未知原因登录失败，联系管理员"), response);
			e.printStackTrace();
//			try {
//				response_write(getRM(UNSUCCESS, "未知原因登录失败，联系管理员"), response);
//				throw new SystemException("系统异常,登录失败");
//				
//			} catch (SystemException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		}

	}

	@RequestMapping(value = "/logout")
	@SystemControllerLog(description = "后台用户退出")
	public String logout(HttpServletRequest request) {
		TUser redisuser = (TUser) SerializeUtil.unserialize(this.get(this.findcookie(request).getBytes()));
		// TUser redisuser = (TUser)
		// SerializeUtil.unserialize(this.get("user".getBytes()));
		// if (redisuser != null) {
		// this.del("user");
		// }
		if (redisuser != null) {
			del(this.findcookie(request).getBytes());
		}

		return "redirect:/";
	}

}
