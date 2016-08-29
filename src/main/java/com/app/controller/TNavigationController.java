package com.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.aop.LoginCheck;
import com.app.aop.SystemControllerLog;
import com.app.dto.Navigation2;
import com.app.model.TNavigation;
import com.app.model.TUser;
import com.app.redis.SerializeUtil;
import com.app.service.navigation.TNavigationInte;
import com.app.util.BaseController;
import com.app.util.ReturnMSG;
import com.app.util.StringUtils;
import com.app.util.Tool;

@Controller
@RequestMapping("/navigation")
public class TNavigationController extends BaseController {
	@Autowired
	private TNavigationInte navigationserv;
	
	
	
	/**
	 * 导航菜单首页
	 * 
	 * @param response
	 */
	@LoginCheck(description = true)
	@RequestMapping(value = "/navigationindex")
	@SystemControllerLog(description = "跳转到导航菜单首页")
	public String navigationindex(HttpServletRequest request, HttpServletResponse response) {
		return "navigation/navigationlist";
	}
	
	
	@LoginCheck(description = true)
	@RequestMapping(value = "/saveTNava")
	@SystemControllerLog(description = "保存和更新菜单信息")
	@ResponseBody
	public void saveTNava(TNavigation tu, HttpServletRequest request, HttpServletResponse response) {

		if (StringUtils.isEmpty(tu.getId())) {
			navigationserv.insert(tu);
			response_write(getRM(SUCCESS, "操作成功"), response);
		} else {
			navigationserv.updateByPrimaryKey(tu);
			response_write(getRM(SUCCESS, "操作成功"), response);
		}
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/navigationList")
	@SystemControllerLog(description = "导航菜单列表查询")
	@ResponseBody
	public void navigationList(HttpServletRequest request, HttpServletResponse response) {
		List navs = new ArrayList();
		List<TNavigation> myNav = navigationserv.selectListTNavigation(0l);
		for (int i = 0; i < myNav.size(); i++) {
			TNavigation tnv = (TNavigation) myNav.get(i);
			long nid = tnv.getId();
			String ntext = tnv.getText() == null ? "" : tnv.getText();
			String nurl = tnv.getUrl() == null ? "" : tnv.getUrl();
			int nsortNum = tnv.getSortnum();
			String niconCls = tnv.getIconcls() == null ? "" : tnv.getIconcls();
			long nparentId = tnv.getParentid();
			Navigation2 n2 = new Navigation2(nid, ntext, nurl, nsortNum, niconCls, nparentId);
			addChild2(n2);
			navs.add(n2);

		}
		ReturnMSG rmsg = new ReturnMSG(null, null, navs);
		response_write(navs, response);
	}
	@LoginCheck(description = true)
	@RequestMapping(value = "/initNavsq")
	@SystemControllerLog(description = "权限查询")
	@ResponseBody
	public void initNavsq(TNavigation tn,HttpServletResponse response) {
		
		List navs = new ArrayList();
		System.out.println(tn.getId());
		List<TNavigation> myNav = navigationserv.selectListTNavigation(0l);
		for (int i = 0; i < myNav.size(); i++) {
			TNavigation tnv = (TNavigation) myNav.get(i);
			long nid = tnv.getId();
			String ntext = tnv.getText() == null ? "" : tnv.getText();
			String nurl = tnv.getUrl() == null ? "" : tnv.getUrl();
			int nsortNum = tnv.getSortnum();
			String niconCls = tnv.getIconcls() == null ? "" : tnv.getIconcls();
			long nparentId = tnv.getParentid();
			Navigation2 n2 = new Navigation2(nid, ntext, nurl, nsortNum, niconCls, nparentId);
			addChild2(n2);
			navs.add(n2);

		}
		ReturnMSG rmsg = new ReturnMSG(null, null, navs);
		response_write(navs, response);
	}
	
	@LoginCheck(description = true)
	@RequestMapping(value = "/deleteTNavigation")
	@SystemControllerLog(description = "删除菜单")
	@ResponseBody
	public void deleteTNavigation(TNavigation tn, HttpServletResponse response) {
		if (StringUtils.isNotEmpty(tn.getId())) {
			List count = navigationserv.selectListTNavigation(tn.getId());
			if (count.size() > 0) {
				response_write(getRM(UNSUCCESS, "请先删除，子节点"), response);
			} else {
				navigationserv.deleteByPrimaryKey(tn.getId());
				response_write(getRM(SUCCESS, "操作成功"), response);
			}
		} else {
			response_write(getRM(UNSUCCESS, "操作失败"), response);
		}
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/navigationCombox")
	@SystemControllerLog(description = "导航菜单下拉框")
	@ResponseBody
	public void navigationCombox(HttpServletRequest request, HttpServletResponse response) {
		List navs = new ArrayList();
		List<TNavigation> myNav = navigationserv.selectListTNavigation(-1l);
		for (int i = 0; i < myNav.size(); i++) {
			TNavigation tnv = (TNavigation) myNav.get(i);
			long nid = tnv.getId();
			String ntext = tnv.getText() == null ? "" : tnv.getText();
			String nurl = tnv.getUrl() == null ? "" : tnv.getUrl();
			int nsortNum=0;
			if(StringUtils.isNotEmpty(tnv.getSortnum())){
			 nsortNum = tnv.getSortnum();
			}
			String niconCls = tnv.getIconcls() == null ? "" : tnv.getIconcls();
			long nparentId = tnv.getParentid();
			Navigation2 n2 = new Navigation2(nid, ntext, nurl, nsortNum, niconCls, nparentId);
			addChild2(n2);
			navs.add(n2);

		}
		ReturnMSG rmsg = new ReturnMSG(null, null, navs);
		response_write(navs, response);
	}

	/**
	 * 根据id加载数据
	 */
	@LoginCheck(description = true)
	@RequestMapping(value = "/loadTNav")
	@SystemControllerLog(description = "修改时加载填充数据")
	@ResponseBody
	public void loadTNav(TNavigation tu, HttpServletResponse response) {
		TNavigation td = null;
		if (StringUtils.isNotEmpty(tu.getId())) {
			td = navigationserv.selectByPrimaryKey(tu.getId());
		}
		response_write(td, response);
	}
	@LoginCheck(description=true)
	@RequestMapping(value = "initNav")
	@SystemControllerLog(description = "用户权限查询")
	@ResponseBody
	public void navigation(HttpServletRequest request,HttpServletResponse response) {
		List navs = new ArrayList();
		String navids=null;		
		TUser redisuser = (TUser) SerializeUtil.unserialize(this.get(this.findcookie(request).getBytes()));
		//TUser redisuser =this.getWebUserAttribute("user");
		if (redisuser != null) {
			 navids=redisuser.getNavids();		
		}
		List<TNavigation> myNav=navigationserv.selectNavigation(Tool.splitdh(navids));
		//ID,TEXT,URL,SORTNUM,ICONCLS,PARENTID
		for(int i=0;i<myNav.size();i++){
			TNavigation tnv=(TNavigation)myNav.get(i);
			long nid=tnv.getId();
			String ntext=tnv.getText()==null?"":tnv.getText();
			String nurl=tnv.getUrl()==null?"":tnv.getUrl();
			int nsortNum=tnv.getSortnum();
			String niconCls=tnv.getIconcls()==null?"":tnv.getIconcls();
			long nparentId=tnv.getParentid();
			Navigation2 n2=new Navigation2(nid, ntext, nurl, nsortNum,niconCls, nparentId);
			addChild(request,n2);
			navs.add(n2);
			
		}
		ReturnMSG rmsg = new ReturnMSG(null,null,navs);
		response_write(navs, response);
	}
	/**
	 * 根据父节点主键Id，查询出该父节点下的子节点
	 * @param Navigation2实体类
	 * return null
	 * */
	private void  addChild(HttpServletRequest request,Navigation2 n){
		String navids=null;		
		TUser redisuser = (TUser) SerializeUtil.unserialize(this.get(this.findcookie(request).getBytes()));

		//TUser redisuser = this.getWebUserAttribute("user");
		if (redisuser != null) {
			 navids=redisuser.getNavids();		
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("navIdsList", Tool.splitdh(navids));
		params.put("pantent", n.getId());
		List list=navigationserv.selectNavigation2(params);
		if(list.size()==0){
			return;
		}else{
			for(int i=0;i<list.size();i++){
				TNavigation tnv=(TNavigation)list.get(i);
				long nid=tnv.getId();
				String ntext=tnv.getText()==null?"":tnv.getText();
				String nurl=tnv.getUrl()==null?"":tnv.getUrl();
				int nsortNum=tnv.getSortnum();
				String niconCls=tnv.getIconcls()==null?"":tnv.getIconcls();
				long nparentId=tnv.getParentid();			
				Navigation2 n2=new Navigation2(nid, ntext, nurl, nsortNum,niconCls, nparentId);
				addChild(request,n2);
				n.getChildren().add(n2);
			}
		}
	}
	/**
	 * 根据父节点主键Id，查询出该父节点下的子节点
	 * 
	 * @param Navigation2实体类
	 *            return null
	 */
	private void addChild2(Navigation2 n) {
		
		// Map<String, Object> params = new HashMap<String, Object>();
		// params.put("navIdsList", Tool.splitdh(navIds));
		// params.put("pantent", n.getId());
		List list = navigationserv.selectListTNavigation(n.getId());
		if (list.size() == 0) {
			return;
		} else {
			for (int i = 0; i < list.size(); i++) {
				TNavigation tnv = (TNavigation) list.get(i);
				long nid = tnv.getId();
				String ntext = tnv.getText() == null ? "" : tnv.getText();
				String nurl = tnv.getUrl() == null ? "" : tnv.getUrl();
				int nsortNum = tnv.getSortnum();
				String niconCls = tnv.getIconcls() == null ? "" : tnv.getIconcls();
				long nparentId = tnv.getParentid();
				Navigation2 n2 = new Navigation2(nid, ntext, nurl, nsortNum, niconCls, nparentId);
				addChild2(n2);
				n.getChildren().add(n2);
			}
		}
	}
}
