package com.app.filter;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.app.aop.LoginCheck;
import com.app.model.TUser;
import com.app.redis.SerializeUtil;
import com.app.util.BaseController;

import redis.clients.jedis.JedisCluster;

/**
 * 是否需要登录验证
 * 
 * @author arui
 *
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
	private final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);
	@Autowired
	JedisCluster jedisCluster;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			LoginCheck authPassport = ((HandlerMethod) handler).getMethodAnnotation(LoginCheck.class);
			// 没有声明需要权限,或者声明不验证权限
			if (authPassport == null || authPassport.description() == false) {
				return true;
			} else {
				// 在这里实现自己的权限验证逻辑
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				// HttpSession session = httpRequest.getSession();
				BaseController bas = new BaseController();
				TUser redisuser = bas.getWebUserAttribute("user");

				// byte[] person = jedisCluster.get(("user").getBytes());

				// TUser redisuser = (TUser) SerializeUtil.unserialize(person);

				if (redisuser != null) {// 如果验证成功返回true（这里直接写false来模拟验证失败的处理）
					return true;
				} else// 如果验证失败
				{
					// 返回到登录界面
					log.info(request.getContextPath() + " " + httpRequest.getRequestURI());
					// httpResponse.sendRedirect(request.getContextPath()+"/indexcon/index");
					httpResponse.setContentType("text/html;charset=gbk");
					httpResponse.setCharacterEncoding("utf-8");
					httpResponse.setHeader("Cache-Control", "no-cache");
					String html = "<script language='javascript'> alert('登录超时，请重新登录');top.location='"
							+ request.getContextPath() + "/indexcon/index';  </script>";
					httpResponse.getWriter().write(html);
					// httpResponse.sendRedirect(request.getContextPath()+"/indexcon/index");
					return false;
				}
			}
		} else {
			return true;
		}

	}
}
