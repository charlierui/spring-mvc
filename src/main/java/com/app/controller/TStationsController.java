package com.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.aop.LoginCheck;
import com.app.aop.SystemControllerLog;
import com.app.dto.TDictypeDto;
import com.app.model.TDepartment;
import com.app.model.TStations;
import com.app.model.TUser;
import com.app.redis.SerializeUtil;
import com.app.service.tstations.TStationsInte;
import com.app.util.BaseController;
import com.app.util.PageUtil;
import com.app.util.StringUtils;

@Controller
@RequestMapping(value = "/stationscon")
public class TStationsController extends BaseController {
	@Autowired
	private TStationsInte tstationsserv;
	private static Logger logger = LoggerFactory.getLogger(TStationsController.class);

	@LoginCheck(description = true)
	@RequestMapping(value = "/stationindex")
	@SystemControllerLog(description = "后台岗位页面跳转")
	public String stationindex(Model model) {
		return "tstations/tstationslist";
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/stationlist")
	@SystemControllerLog(description = "后台岗位信息列表查询")
	@ResponseBody
	public PageUtil stationlist(TStations tstat, PageUtil pu, HttpServletRequest request,
			HttpServletResponse response) {

		pu.setPageSize(row(request));
		pu.setQuery(tstat);// 设置查询条件
		List<TStations> data = tstationsserv.selectListTstations(pu);
		pu.setData(data);// 设置反馈结果
		return pu;
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/saveStation")
	@SystemControllerLog(description = "后台岗位信息列表查询")
	@ResponseBody
	public void saveStation(TStations tstat, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(tstat.getId())) {
				tstationsserv.insert(tstat);
				response_write(getRM(SUCCESS, "操作成功"), response);
			} else {
				tstationsserv.updateByPrimaryKey(tstat);
				response_write(getRM(SUCCESS, "操作成功"), response);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			response_write(getRM(UNSUCCESS, "操作失败"), response);
		}
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/checkStationCode")
	@SystemControllerLog(description = "检查岗位编码是否存在")
	@ResponseBody
	public void checkStationcode(TStations tu, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		if (StringUtils.isNotEmpty(tu.getId())) {
			out.print(true);
		} else {
			long count = tstationsserv.selectCountByStationcode(tu.getStationcode());
			if (count > 0) {
				out.print(false);
			} else {
				out.print(true);
			}
		}
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/loadStation")
	@SystemControllerLog(description = "修改加载岗位数据")
	@ResponseBody
	public void loadStation(TStations tu, HttpServletResponse response) {
		TStations tus = null;
		if (StringUtils.isNotEmpty(tu.getId())) {
			tus = tstationsserv.selectByPrimaryKey(tu.getId());

		}
		response_write(tus, response);
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/deleteStation")
	@SystemControllerLog(description = "修改--加载岗位数据")
	@ResponseBody
	public void delStation(TStations tu, HttpServletResponse response) {
		if (StringUtils.isNotEmpty(tu.getId())) {
			tstationsserv.deleteByPrimaryKey(tu.getId());
			response_write(getRM(SUCCESS, "操作成功"), response);
		} else {
			response_write(getRM(UNSUCCESS, "操作失败"), response);
		}
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/selectall")
	@SystemControllerLog(description = "下拉列表加载岗位数据")
	@ResponseBody
	public void selectall(HttpServletResponse response) {
		List<TStations> tsta = new ArrayList<TStations>();
//		List<TStations> list1 = (List<TStations>) SerializeUtil.unserialize(this.get("selectall".getBytes()));
//		if (list1!=null&& list1.size() > 0) {
//			//logger.info("命中缓存selectall");
//			response_write(list1, response);
//		} else {
			TStations tsone = new TStations();
			tsone.setId(0l);
			tsone.setStationname("--请选择--");
			tsta.add(tsone);
			List<TStations> list = tstationsserv.selectall();
			for (int i = 0; i < list.size(); i++) {
				TStations tstwo = list.get(i);
				tsta.add(tstwo);

			}
			//this.set("selectall".getBytes(), SerializeUtil.serialize(tsta));
			response_write(tsta, response);
		//}
	}
}
