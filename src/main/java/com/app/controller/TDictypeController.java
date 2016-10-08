package com.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.aop.LoginCheck;
import com.app.aop.SystemControllerLog;
import com.app.dto.TDictypeDto;
import com.app.model.TDictype;
import com.app.model.TDicvalue;
import com.app.redis.SerializeUtil;
import com.app.service.tdictype.TDictypeInte;
import com.app.service.tdicvalue.TDicvalueInte;
import com.app.util.BaseController;
import com.app.util.StringUtils;

@Controller
@RequestMapping(value = "/tdictypecon")
public class TDictypeController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(TDictypeController.class);

	@Autowired
	private TDictypeInte tdictypeserv;
	@Autowired
	private TDicvalueInte tdicvalueserv;

	/**
	 * 联动测试
	 */
	
	@LoginCheck(description = true)
	@RequestMapping(value = "/ldindex")
	@SystemControllerLog(description = "跳转到连动首页")
	public String ldindex(HttpServletRequest request, HttpServletResponse response) {
		return "dictype/ldindex";
	}
	
	@LoginCheck(description = true)
	@RequestMapping(value = "/listDictypeAllld")
	@SystemControllerLog(description = "加载字典类别")
	@ResponseBody
	public void listDictypeAllld(TDicvalue td,HttpServletRequest request, HttpServletResponse response) {
		Map map=new HashMap();
		map.put("tid", td.getTid());
		List<TDicvalue> data = tdicvalueserv.selectByTidld(map);
		response_write(data, response);
	}
	
	
	/**
	 * 数据字典首页
	 * 
	 * @param response
	 */
	@LoginCheck(description = true)
	@RequestMapping(value = "/dictypeindex")
	@SystemControllerLog(description = "跳转到数据字典首页")
	public String navigationindex(HttpServletRequest request, HttpServletResponse response) {
		return "dictype/dictypelist";
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/listDictype")
	@SystemControllerLog(description = "加载字典类别")
	@ResponseBody
	public void listDictype(HttpServletRequest request, HttpServletResponse response) {

		response_write(tdictypeserv.selectAll(), response);
	}
	@LoginCheck(description = true)
	@RequestMapping(value = "/saveDictype")
	@SystemControllerLog(description = "字典类别保存和更新")
	@ResponseBody
	public void saveDictype(TDictype td, HttpServletRequest request, HttpServletResponse response) {

		if (StringUtils.isEmpty(td.getId())) {
			tdictypeserv.insert(td);
			response_write(getRM(SUCCESS, "操作成功"), response);
		} else {
			tdictypeserv.updateByPrimaryKeySelective(td);
			response_write(getRM(SUCCESS, "操作成功"), response);
		}

	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/checkDictypeValue")
	@SystemControllerLog(description = "字典类别保存和更新")
	@ResponseBody
	public void checkDictypeValue(TDictype td, HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			long count = tdictypeserv.selectCountByValue(td.getValue());
			if (count > 0) {
				out.print(false);
			} else {
				out.print(true);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/loadDictype")
	@SystemControllerLog(description = "字典类别修改加载信息")
	@ResponseBody
	public void loadDictype(TDictype td, HttpServletResponse response) {
		TDictype tdi = null;

		if (StringUtils.isNotEmpty(td.getId())) {
			tdi = tdictypeserv.selectByPrimaryKey(td.getId());

		}
		response_write(tdi, response);
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/removeDictype")
	@SystemControllerLog(description = "字典类别删除")
	@ResponseBody
	public void removeDictype(TDictype td, HttpServletResponse response) {
		if (StringUtils.isNotEmpty(td.getId())) {
			Map map = new HashedMap();
			map.put("tid", td.getId());
			int count = tdicvalueserv.selectByTid1(map);
			if (count > 0) {
				response_write(getRM(UNSUCCESS, "请先删除该类型下的所有内容"), response);
			} else {
				tdictypeserv.deleteByPrimaryKey(td.getId());
				response_write(getRM(SUCCESS, "操作成功"), response);

			}
		} else {
			response_write(getRM(UNSUCCESS, "操作失败"), response);
		}
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/ditcomboxs")
	@SystemControllerLog(description = "字典类别下拉列表")
	@ResponseBody
	public void ditcombox(HttpServletRequest request, HttpServletResponse response) {
		List<TDictypeDto> list1 = new ArrayList<TDictypeDto>();

		TDictypeDto td = new TDictypeDto();
		td.setId("0");
		td.setText("--请选择--");
		list1.add(td);
		// System.out.println(request.getParameter("typeValue"));
		List<TDictypeDto> list2 = tdictypeserv.ditcombox(request.getParameter("typeValue"));
		for (int i = 0; i < list2.size(); i++) {
			TDictypeDto td2 = list2.get(i);
			list1.add(td2);
		}
		//this.set("ditcombox".getBytes(), SerializeUtil.serialize(list1));
		response_write(list1, response);

	}
}
