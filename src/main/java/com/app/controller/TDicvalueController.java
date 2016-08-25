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
import com.app.model.TDictype;
import com.app.model.TDicvalue;
import com.app.service.tdicvalue.TDicvalueInte;
import com.app.util.BaseController;
import com.app.util.PageUtil;
import com.app.util.StringUtils;

@Controller
@RequestMapping(value = "/tdicvaluecon")
public class TDicvalueController extends BaseController {
	@Autowired
	private TDicvalueInte tdicvalueserv;

	@LoginCheck(description = true)
	@RequestMapping(value = "/listDicvalue")
	@SystemControllerLog(description = "加载字典类别")
	@ResponseBody
	public PageUtil listDicvalue(PageUtil pu, TDicvalue td, HttpServletRequest request, HttpServletResponse response) {
		pu.setPageSize(row(request));
		pu.setQuery(td);// 设置查询条件
		List<TDicvalue> data = tdicvalueserv.selectByTid(pu);
		pu.setData(data);// 设置反馈结果
		return pu;
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/saveDicvalue")
	@SystemControllerLog(description = "字典类别保存和更新")
	@ResponseBody
	public void saveDicvalue(TDicvalue td, HttpServletRequest request, HttpServletResponse response) {
			if (StringUtils.isEmpty(td.getVid())) {
				long vid = tdicvalueserv.selectBySoftcode(td);
				long tid = tdicvalueserv.selectById(td);
				
				if (vid > 0) {
					response_write(getRM(UNSUCCESS, "类型编码存在"), response);
				} else if (tid > 0) {
					response_write(getRM(UNSUCCESS, "类型值存在"), response);
				} else {
					tdicvalueserv.insert(td);
					response_write(getRM(SUCCESS, "操作成功"), response);
				}
			} else {
				try {
					tdicvalueserv.updateByPrimaryKeySelective(td);
					response_write(getRM(SUCCESS, "操作成功"), response);
				} catch (Exception e) {
					response_write(getRM(UNSUCCESS, "操作失败"), response);
				}
			}
		
	}
	@LoginCheck(description = true)
	@RequestMapping(value = "/removeDicvalue")
	@SystemControllerLog(description = "字典类别保存和更新")
	@ResponseBody
	public void removeDicvalue(TDicvalue td, HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isNotEmpty(td.getVid())){
			tdicvalueserv.deleteByPrimaryKey(td.getVid());
			response_write(getRM(SUCCESS, "操作成功"), response);
		}else{
			response_write(getRM(UNSUCCESS, "操作失败"), response);

		}
	}
}
