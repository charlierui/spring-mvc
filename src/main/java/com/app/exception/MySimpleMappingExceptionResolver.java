package com.app.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.druid.support.json.JSONUtils;
import com.app.util.ReturnMSG;

public class MySimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
	@Override

	protected ModelAndView doResolveException(HttpServletRequest request,

			HttpServletResponse response, Object arg2, Exception ex) {

		System.out.println("resolver ...");

		String viewName = determineViewName(ex, request);

		if (viewName != null) {// JSP格式返回

			if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null
					&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);

			} else {// JSON格式返回

				try {
					PrintWriter writer = response.getWriter();
					ReturnMSG rm = new ReturnMSG();
					rm.setStatuscode("-1");
					rm.setMsg(ex.getMessage());
//					Map map = new HashMap();
//
//					map.put("success", false);
//
//					map.put("msg", ex.getMessage());

					writer.write(JSONUtils.toJSONString(rm));

					writer.flush();
				} catch (IOException e) {

					e.printStackTrace();

				}

				return null;

			}

		} else {

			return null;

		}

	}

}
