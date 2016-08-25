package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.aop.LoginCheck;
import com.app.aop.SystemControllerLog;
import com.app.dto.TDepartment2;
import com.app.model.TDepartment;
import com.app.model.TStations;
import com.app.redis.SerializeUtil;
import com.app.service.department.TdepartmentInte;
import com.app.util.BaseController;
import com.app.util.PageUtil;
import com.app.util.StringUtils;

@Controller
@RequestMapping(value = "/deptcon")
public class TdepartmentController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(TdepartmentController.class);

	@Autowired
	private TdepartmentInte deptserv;

	@LoginCheck(description = true)
	@RequestMapping(value = "/deptindex")
	@SystemControllerLog(description = "后台部门信息查询")
	public String deptlist() {
		return "department/deptlist";
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/deptList")
	@SystemControllerLog(description = "后台部门信息列表查询")
	@ResponseBody
	public void deptList(TDepartment tu, PageUtil pu, HttpServletRequest request, HttpServletResponse response) {
		List navs = new ArrayList();

		// 查询父节点
		List<TDepartment> data = deptserv.selectListTdept(0l);
		for (int i = 0; i < data.size(); i++) {
			TDepartment td = data.get(i);
			long id = td.getId();
			String back = td.getBak() == null ? "" : td.getBak();
			String dname = td.getDname() == null ? "" : td.getDname();
			String dnsortNum = td.getDsortnum();
			long parentid = td.getParentid();
			long zcgkid = td.getZcgkid() == null ? 0 : td.getZcgkid();
			String text = td.getDname();
			TDepartment2 dept = new TDepartment2(id, back, dname, dnsortNum, parentid, zcgkid, text);
			addChild(dept);
			navs.add(dept);
		}
		this.response_write(navs, response);

	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/saveTDept")
	@SystemControllerLog(description = "保存和更新部门信息")
	@ResponseBody
	public void saveTDept(TDepartment tu, HttpServletRequest request, HttpServletResponse response) {
		TDepartment td1 = new TDepartment();
		td1.copy(tu);
		long id = -2;
		long count = deptserv.selectAll();
		if (StringUtils.isEmpty(tu.getId())) {
			if (tu.getParentid() == 0) {
				deptserv.insert(td1);
				td1.setZcgkid(td1.getId());
				deptserv.updateByPrimaryKey(td1);
			} else {
				for (int i = 0; i < count; i++) {
					tu = deptserv.selectByPrimaryKey(tu.getParentid());
					if (tu.getParentid() == 0) {
						id = tu.getId();
						break;
					}
				}
				td1.setZcgkid(id);
				deptserv.insert(td1);
			}

			response_write(getRM(SUCCESS, "操作成功"), response);
		} else {
			if (tu.getParentid() == 0) {
				id = td1.getId();
			} else {
				for (int i = 0; i < count; i++) {
					tu = deptserv.selectByPrimaryKey(tu.getParentid());
					if (tu.getParentid() == 0) {
						id = tu.getId();
						break;
					}
				}
			}
			TDepartment tmp = deptserv.selectByPrimaryKey(td1.getId());
			// 查询要修改的部门下，是否存在子信息
			List<TDepartment> list = deptserv.selectListTdeptByparentId(tmp.getId());
			if (list.size() > 0) {
				updateZCGK(list, id);
			}
			tmp.setZcgkid(id);
			tmp.copy(td1);
			deptserv.updateByPrimaryKey(tmp);

			response_write(getRM(SUCCESS, "操作成功"), response);
		}
	}

	/**
	 * 删除方法
	 */
	@LoginCheck(description = true)
	@RequestMapping(value = "/deleteTDept")
	@SystemControllerLog(description = "删除部门信息")
	@ResponseBody
	public void deleteTDept(TDepartment tu, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotEmpty(tu.getId())) {
			List list = deptserv.selectListTdeptByparentId(tu.getId());
			if (list.size() > 0) {
				response_write(getRM(UNSUCCESS, "请先删除，子节点"), response);
			} else {
				deptserv.deleteByPrimaryKey(tu.getId());
				response_write(getRM(SUCCESS, "操作成功"), response);
			}
		}
	}

	/**
	 * 根据id加载数据
	 */
	@LoginCheck(description = true)
	@RequestMapping(value = "/loadDept")
	@SystemControllerLog(description = "修改部门信息时加载填充数据")
	@ResponseBody
	public void loadDept(TDepartment tu, HttpServletResponse response) {
		TDepartment td = null;
		if (StringUtils.isNotEmpty(tu.getId())) {
			td = deptserv.selectByPrimaryKey(tu.getId());
		}
		response_write(td, response);
	}

	/**
	 * 树形下拉列表
	 * 
	 * @param tu
	 * @param request
	 * @param response
	 */
	@LoginCheck(description = true)
	@RequestMapping(value = "/findBypartent")
	@SystemControllerLog(description = "后台下拉列表使用")
	@ResponseBody
	public void findBypartent(TDepartment tu, HttpServletRequest request, HttpServletResponse response) {
		List navs = new ArrayList();
//		List list1 = (List) SerializeUtil.unserialize(this.get("findBypartent".getBytes()));
//		if (list1 != null && list1.size() > 0) {
//			//logger.info("命中缓存findBypartent");
//			response_write(list1, response);
//		} else {
			// 查询父节点
			List<TDepartment> data = deptserv.selectListTdept(-1l);
			for (int i = 0; i < data.size(); i++) {
				TDepartment td = data.get(i);
				long id = td.getId();
				String back = td.getBak() == null ? "" : td.getBak();
				String dname = td.getDname() == null ? "" : td.getDname();
				String dnsortNum = td.getDsortnum();
				long parentid = td.getParentid();
				long zcgkid = td.getZcgkid() == null ? 0 : td.getZcgkid();
				String text = td.getDname();
				TDepartment2 dept = new TDepartment2(id, back, dname, dnsortNum, parentid, zcgkid, text);
				addChild(dept);
				navs.add(dept);
			}
			//logger.info("添加缓存findBypartent");
			//this.set("findBypartent".getBytes(), SerializeUtil.serialize(navs));
			this.response_write(navs, response);
		//}
	}

	/**
	 * 循环获取子节点
	 * 
	 * @param n
	 */
	private void addChild(TDepartment2 n) {

		List list = deptserv.selectListTdeptByparentId(n.getId());
		if (list.size() == 0) {
			return;
		} else {
			for (int i = 0; i < list.size(); i++) {
				TDepartment td = (TDepartment) list.get(i);
				long id = td.getId();
				String back = td.getBak() == null ? "" : td.getBak();
				String dname = td.getDname() == null ? "" : td.getDname();
				String dnsortNum = td.getDsortnum();
				long parentid = td.getParentid();
				long zcgkid = td.getZcgkid() == null ? 0 : td.getZcgkid();
				String text = td.getDname();
				TDepartment2 dept = new TDepartment2(id, back, dname, dnsortNum, parentid, zcgkid, text);
				addChild(dept);
				n.getChildren().add(dept);
			}
		}
	}

	/**
	 * 根据id修改zcgkid
	 * 
	 * @return
	 */
	public void updateZCGK(List<TDepartment> list, long id) {
		/**
		 * 
		 * 根据id获取子信息,然后根据子id在获取子信息的子信息,以此循环,直到没有子信息
		 */
		for (int i = 0; i < list.size(); i++) {
			try {
				TDepartment dept = list.get(i);
				List<TDepartment> list2 = deptserv.selectListTdeptByparentId(dept.getId());
				if (list2.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						dept = list.get(j);
						dept.setZcgkid(id);
						deptserv.updateByPrimaryKey(dept);
						updateZCGK(list2, id);
					}
				}
				dept.setZcgkid(id);
				deptserv.updateByPrimaryKey(dept);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
