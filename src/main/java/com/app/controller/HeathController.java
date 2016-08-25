package com.app.controller;

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
import com.app.aop.SystemControllerLog;
import com.app.model.Healthinquiry;
import com.app.model.Resident;
import com.app.service.health.HealthServInte;
import com.app.service.resdient.ResdientServInte;
import com.app.util.BaseController;
import com.app.util.PageUtil;
/**
 * 
 * @author arui
 *
 */
@Controller
@RequestMapping("/heath")
public class HeathController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(HeathController.class);

	@Autowired
	private HealthServInte heathserv;
	@Autowired
	private ResdientServInte resdiserv;
	
	
	
	@RequestMapping(value = "/heathfindbykey")
	@SystemControllerLog(description = "获取良医咨询列表")
	public String findList(Model model) {
		Healthinquiry heal = heathserv.selectByPrimaryKey("002e118418f211e69fbd2ff2395e9a47");
		model.addAttribute("heal", heal);
		return "heath/heathfindbykey";
	}
	@RequestMapping(value = "/main")
	@SystemControllerLog(description = "获取良医咨询列表")
	public String main(Model model) {
//		Healthinquiry heal = heathserv.selectByPrimaryKey("002e118418f211e69fbd2ff2395e9a47");
//		model.addAttribute("heal", heal);
		return "main";
	}
	
	/**
	 * 健康咨询查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list")
	public String listindex(HttpServletRequest request, HttpServletResponse response,
			Model model){
		
		return "heath/findlist";
	}
	@RequestMapping(value="/listresdi")
	public String listresdi(HttpServletRequest request, HttpServletResponse response,
			Model model){
		
		return "resdi/findlist";
	}
	
	@RequestMapping(value = "/findlistfilter")
	@SystemControllerLog(description = "获取良医咨询列表")
	public @ResponseBody PageUtil search(HttpServletRequest request, Healthinquiry heath, PageUtil pu) {  
		
		pu.setPageSize(Integer.parseInt(request.getParameter("rows")));
        pu.setQuery(heath);//设置查询条件
        List<Healthinquiry> data= heathserv.selectHealthByUserNamefilter(pu);
        pu.setData(data);//设置反馈结果  
        return pu;  
    }  
	/**
	 * resident
	 * @param request
	 * @param heath
	 * @param pu
	 * @return
	 */
	@RequestMapping(value = "/searchResdi")
	@SystemControllerLog(description = "获取良医咨询列表")
	public @ResponseBody PageUtil searchResdi(HttpServletRequest request, Healthinquiry heath, PageUtil pu) {  
		
		pu.setPageSize(row(request));
        pu.setQuery(heath);//设置查询条件
        List<Resident> data= resdiserv.selectByPager(pu);
        pu.setData(data);//设置反馈结果  
        return pu;  
    }   
}
