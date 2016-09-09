package com.app.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.app.aop.LoginCheck;
import com.app.model.Log;
import com.app.model.TUser;
import com.app.redis.SerializeUtil;
import com.app.service.log.LogInte;
import com.app.util.BaseController;
import redis.clients.jedis.JedisCluster;

/**
 * 是否需要登录验证
 * 
 * @author arui
 *
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);
	@Autowired
	JedisCluster jedisCluster;
	@Autowired 
	LogInte loginteimpl;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//pvtongji( request,response);
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
				//TUser redisuser = bas.getWebUserAttribute("user");
				BaseController bc=new BaseController();
				//System.out.println(bc.findcookie(httpRequest));
				byte[] person = jedisCluster.get(bc.findcookie(httpRequest).getBytes());

				TUser redisuser = (TUser) SerializeUtil.unserialize(person);

				if (redisuser != null) {// 如果验证成功返回true（这里直接写false来模拟验证失败的处理）
					return true;
				} else// 如果验证失败
				{
					// 返回到登录界面
					//logger.info(request.getContextPath() + " " + httpRequest.getRequestURI());
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
	/**
	 * 统计访问量和并发量
	 * @param servletRequest
	 * @param servletResponse
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void pvtongji(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {
		
		//HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpSession session = request.getSession();
		String ip = request.getRemoteAddr();          
		String page = request.getRequestURI();
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		
		logger.info("doFilter sessionId="+session.getId()+",ip="+ip+",page="+page+",contextPath="+contextPath+",servletPath="+servletPath);
		
		Log  logBean = new Log();
		logBean.setId(UUID.randomUUID().toString());
		logBean.setSessionId(session.getId());
		logBean.setIp(ip);
		logBean.setPage(page);
		logBean.setAccessTime(new Timestamp(new Date().getTime()));
		logBean.setStayTime(0l);
		
		//通过session id 和 ip，查出最近的一条访问记录
		Log bean = null;
		try {
			bean = loginteimpl.getLatestLog(session.getId(), ip);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		//更改最近访问记录的停留时间，这里把两次访问记录的间隔时间算成上一次页面访问的停留时间
		if(bean != null){
			long stayTime = (System.currentTimeMillis() - bean.getAccessTime().getTime())/1000;
			bean.setStayTime(stayTime);
			try {
				loginteimpl.updateByPrimaryKeySelective(bean);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		
		//保存当前访问记录
		try {
			loginteimpl.insert(logBean);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
