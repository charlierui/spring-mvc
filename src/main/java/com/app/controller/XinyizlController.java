package com.app.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.aop.SystemControllerLog;
import com.app.model.Xinyizl;
import com.app.service.xinyzl.XinyizlServInte;

@Controller 
@RequestMapping("/user")
public class XinyizlController {
	@Autowired
  private XinyizlServInte xinyizlService;
    @RequestMapping(value="/index")
    @SystemControllerLog(description = "查询用户")
    public String index_jsp(Model model){
    	Xinyizl heal=xinyizlService.selectByPrimaryKey("5e420032119d11e6b624193925fd5d80");
        model.addAttribute("heal", heal);  
        //redis-cluster.conf
        return "index";
    }  
    @RequestMapping(value="/addxyzl")
    @SystemControllerLog(description = "用户增加")
    public String addXinyizl(){
    	Xinyizl xy=new Xinyizl();
    	xy.setPkXinyi(System.currentTimeMillis()+"");
    	xy.setXname("数据测试");
    	xy.setXsort(1); 
    	xinyizlService.insert(xy);
    	return "index";
    }
}
