package com.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.aop.LoginCheck;
import com.app.aop.SystemControllerLog;
import com.app.model.TLogbook;
import com.app.service.tlogbook.TLogbookInte;
import com.app.util.BaseController;
import com.app.util.PageUtil;
@Controller
@RequestMapping(value="/tlogbookcon")
public class LogbookController extends BaseController{
	@Autowired
	private TLogbookInte tlogbookserv;
	
	
	@LoginCheck(description = true)
	@RequestMapping(value = "/logbookindex")
	@SystemControllerLog(description = "日志查询页面")
	public String logbook(){
		return "logbook/loglist";
	}
	
	
	@LoginCheck(description = true)
	@RequestMapping(value = "/logbooklist")
	@SystemControllerLog(description = "日志列表查询")
	@ResponseBody
	public PageUtil logbooklist(TLogbook tlog,PageUtil pu, HttpServletRequest request, HttpServletResponse response){
		pu.setPageSize(row(request));
		pu.setQuery(tlog);// 设置查询条件
		List<TLogbook> data = tlogbookserv.selectListTlog(pu);
		pu.setData(data);// 设置反馈结果
		return pu;
	}
	
	@LoginCheck(description = true)
	@RequestMapping(value = "/deletelogbook")
	@SystemControllerLog(description = "日志列表查询")
	@ResponseBody
	public void deletelogbook(HttpServletRequest request, HttpServletResponse response){
		tlogbookserv.deletelogbook();
		
		response_write(getRM(SUCCESS, "清表成功"), response);
	}
	
}
