package com.app.aop;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.app.dto.SystemOption;
import com.app.model.TLogbook;
import com.app.model.TNavigation;
import com.app.model.TUser;
import com.app.redis.SerializeUtil;
import com.app.service.navigation.TNavigationInte;
import com.app.service.tlogbook.TLogbookInte;
import com.app.util.BaseController;
import com.app.util.NetworkUtil;
import com.app.util.StringUtils;

/**
 * 切点类 监控用户访问操作
 * 
 * @author arui
 *
 */
@Aspect
@Component
public class SystemLogAspect extends BaseController implements Ordered {
	@Autowired
	private TLogbookInte logbookserv;
	@Autowired
	private TNavigationInte navigationserv;
	ThreadLocal<Long> time = new ThreadLocal<Long>();
	ThreadLocal<String> tag = new ThreadLocal<String>();
	// 本地异常日志记录对象
	private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

	// Service层切点
	@Pointcut("@annotation(com.app.aop.SystemServiceLog)")
	public void serviceAspect() {
		logger.info("我是一个切入点");
	}

	// Controller层切点
	@Pointcut("@annotation(com.app.aop.SystemControllerLog)")
	public void controllerAspect() {
		logger.info("我是一个切入点");

	}

	/**
	 * 日志保存
	 * 
	 * @param user
	 * @param request
	 * @param joinPoint
	 * @param ip
	 * @param checktim
	 * @throws Exception
	 */
	public void addLogbook(TUser user, HttpServletRequest request, JoinPoint joinPoint, String ip, String checktim)
			throws Exception {
		TLogbook tlob = null;

		String uri = request.getRequestURI();
		TNavigation tnv = new TNavigation();
		// 得到不添加日志的uri关键字，配置在system.properties
		String[] ind = SystemOption.getInstance().getFilterurl().split(",");

		// 得到匹配结果
		boolean res = true;
		for (int i = 0; i < ind.length; i++) {
			if (uri.endsWith(ind[i])) {
				res = uri.endsWith(ind[i]);
				break;
			} else {
				res = uri.endsWith(ind[i]);
				continue;
			}
		}
		if (res == true) {
			//logger.info("不添加该路径的访问日志" + uri);
		} else {
			tnv.setUrl(uri);
			int count = navigationserv.selectAllByTNavigation(tnv);
			if (count > 0) {
				//logger.info("不添加该路径的访问日志" + uri);
			} else {
				if (StringUtils.isNotEmpty(user)) {
					logger.info("请求人:【" + user.getName() + "】请求IP:" + ip);
					tlob = new TLogbook(new Date(), getControllerMethodDescription(joinPoint), ip, checktim,
							user.getName(), request.getRequestURL().toString(), user.getUsername());
					logbookserv.insert(tlob);
				} else {
					// 针对已经退出的处理
					if (uri.endsWith("logout")) {
						//logger.info("过滤退出日志保存");
					} else {

						tlob = new TLogbook(new Date(), getControllerMethodDescription(joinPoint), ip, checktim, null,
								request.getLocalAddr(), null);
						logbookserv.insert(tlob);

					}

				}

			}
		}
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 * @throws IOException
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) throws IOException {
		time.set(System.currentTimeMillis());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		
		
		// HttpSession session = request.getSession();
		// 读取session中的用户
		// TUser user = (TUser)SerializeUtil.unserialize(this.get(this.findcookie(request).getBytes()));
		TUser user = this.getWebUserAttribute("user");
		// 请求的IP
		String ip = NetworkUtil.getIpAddress(request);

		try {
			// *========控制台输出=========*//
			logger.info("=====Controller前置通知开始=====");
			logger.info("sessionid:"+request.getSession().getId());
//			logger.info("请求方法:"+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
//			logger.info("方法描述:" + getControllerMethodDescription(joinPoint));
//			logger.info("请求人:" + user + "请求IP:" + ip);
			if (request.getRequestURI().endsWith("logout")) {
				addLogbook(user, request, joinPoint, ip,"方法执行时长：" + (System.currentTimeMillis() - time.get()) / 1000 + "秒");
			}
			logger.info("请求方法:{}-方法描述:{}-请求人:{}-请求IP:{}",(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"),getControllerMethodDescription(joinPoint),user,ip);
			logger.info("=====前置通知结束=====");

		} catch (Exception e) {
			// 记录本地异常日志
			logger.error("==前置通知异常==");
			logger.error("异常信息:{}", e.getMessage());
		}
	}

	// @After("controllerAspect()")
	// public void afterExec(JoinPoint joinPoint){
	// MethodSignature ms=(MethodSignature) joinPoint.getSignature();
	// Method method=ms.getMethod();
	// logger.info("标记为"+joinPoint.getTarget().getClass().getName() + "." +
	// joinPoint.getSignature().getName() +
	// "()"+"的方法"+TimeUnit.NANOSECONDS.toSeconds((System.nanoTime()-time.get()))+"ms");
	// }

	// 声明环绕通知
	@Around("controllerAspect()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		
		Object o = pjp.proceed();
		long begin = time.get();
		long end = System.currentTimeMillis();
		// 请求的IP
		String ip = NetworkUtil.getIpAddress(request);
		logger.info("方法执行时长：" + begin + "-" + end + " = " + (end - begin) + " ms");
		Long time = (end - begin) / 1000;
		// 读取session中的用户
		// TUser user = (TUser)SerializeUtil.unserialize(this.get(this.findcookie(request).getBytes()));
		TUser user = this.getWebUserAttribute("user");

		// 读写分离判断//读写分离标示
		String name = pjp.getSignature().getName();
		String aaa = null;
		if (name.matches(SystemOption.getInstance().getRwqh()) == true) {
			aaa = "访问读写库";
		} else {
			aaa = "访问只读库";
		}
		// *========数据库日志=========*//
		if (time > 10) {
			addLogbook(user, request, pjp, ip, aaa + "-方法执行时间过长：" + time + "秒,请开发人员优化");
			logger.error(getControllerMethodDescription(pjp) + "--" + pjp.getTarget().getClass().getName() + "."
					+ pjp.getSignature().getName() + "方法执行时间过长：" + time + "秒,请开发人员优化-" + aaa);
		} else {

			logger.info(getControllerMethodDescription(pjp) + "--" + pjp.getTarget().getClass().getName() + "."
					+ pjp.getSignature().getName() + "方法执行时长：" + time + "秒-" + aaa);
			addLogbook(user, request, pjp, ip, aaa + "-方法执行时长：" + time + "秒");
		}
		return o;
	}

	/**
	 * 异常通知 用于拦截service层记录异常日志
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		// HttpSession session = request.getSession();
		// 读取session中的用户
		// TUser user = (TUser)SerializeUtil.unserialize(this.get(this.findcookie(request).getBytes()));
		TUser user = this.getWebUserAttribute("user");
		// 获取请求ip
		String ip = request.getRemoteAddr();
		// 日志信息
		TLogbook tlob = null;
		// 获取用户请求方法的参数并序列化为JSON格式字符串
		String params = "";
		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
			for (int i = 0; i < joinPoint.getArgs().length; i++) {
				params += JSON.toJSONString(joinPoint.getArgs()[i]) + ";";
			}
		}
		try {
			/* ========控制台输出========= */
			logger.info("=====异常通知开始=====");
			logger.info("异常代码:" + e.getClass().getName());
			logger.info("异常信息:" + e.getMessage());
			logger.info("异常方法:"
					+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
			logger.info("方法描述:" + getServiceMthodDescription(joinPoint));
			logger.info("请求人:" + user.getName() + " 请求IP:" + ip);
			logger.info("请求参数:" + params);
			// *========数据库日志=========*//
			if (StringUtils.isNotEmpty(user)) {
				tlob = new TLogbook(new Date(), getServiceMthodDescription(joinPoint), ip, e.getClass().getName(),
						user.getName(), request.getLocalAddr(), user.getUsername());

			} else {
				tlob = new TLogbook(new Date(), getServiceMthodDescription(joinPoint), ip, null, null,
						request.getLocalAddr(), null);
			}
			logbookserv.insert(tlob);
			logger.info("=====异常通知结束=====");
		} catch (Exception ex) {
			// 记录本地异常日志
			logger.error("==异常通知异常==");
			logger.error("异常信息:{}", ex.getMessage());
		}
		/* ==========记录本地异常日志========== */
		logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}",
				joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(),
				e.getMessage(), params);

	}

	/**
	 * 获取注解中对方法的描述信息 用于service层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getServiceMthodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemServiceLog.class).description();
					break;
				}
			}
		}
		return description;
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemControllerLog.class).description();
					break;
				}
			}
		}
		return description;
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 3;
	}
}
