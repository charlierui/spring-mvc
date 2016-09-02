package com.app.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.app.aop.LoginCheck;
import com.app.aop.SystemControllerLog;
import com.app.dto.SystemOption;
import com.app.dto.TuserDto;
import com.app.model.Healthinquiry;
import com.app.model.TDepartment;
import com.app.model.TStations;
import com.app.model.TUser;
import com.app.redis.SerializeUtil;
import com.app.service.department.TdepartmentInte;
import com.app.service.tdictype.TDictypeInte;
import com.app.service.tstations.TStationsInte;
import com.app.service.tuser.TUserServInte;
import com.app.util.BaseController;
import com.app.util.MD5;
import com.app.util.PageUtil;
import com.app.util.StringUtils;
import com.app.util.Tool;

@Controller
@RequestMapping("tusercon")
public class TUserController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(TUserController.class);

	@Autowired
	private TUserServInte tuserserv;
	@Autowired
	private TdepartmentInte deptserv;
	@Autowired
	private TStationsInte tstationsserv;

	@LoginCheck(description = true)
	@RequestMapping("userindex")
	@SystemControllerLog(description = "后台用户信息查询")
	public String userindex(Model model) {
		return "tuser/tuserlist";
	}

	@RequestMapping("uploadFile")
	@SystemControllerLog(description = "文件上传")
	public void uploadFile(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		MultipartFile multipartFile = null;
		String fileName = null;
		List list=new ArrayList<>();
		for (Map.Entry<String, MultipartFile> set : fileMap.entrySet()) {
			String filekey = set.getKey();// Filedata
			multipartFile = set.getValue();// 文件名
		}
		fileName = this.saveFile(multipartRequest, multipartFile,"up");
		list.add(fileName);
		logger.info(fileName);
		response_write(list, response);
	}


	@LoginCheck(description = true)
	@RequestMapping(value = "userlist")
	@SystemControllerLog(description = "后台用户信息列表查询")
	@ResponseBody
	public PageUtil userList(TuserDto tus, PageUtil pu, HttpServletRequest request, HttpServletResponse response) {

		pu.setPageSize(row(request));
		pu.setQuery(tus);// 设置查询条件
		List<TuserDto> data = tuserserv.selectListTuser(pu);
		pu.setData(data);// 设置反馈结果
		return pu;
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/userSave")
	@SystemControllerLog(description = "后台用户保存和更新")
	@ResponseBody
	public void saveUser(TUser tu, HttpServletResponse response, HttpServletRequest request) {
		try {
			if (tu.getId() == null || tu.getId() == 0) {
				// System.out.println(MD5.MD5(SystemOption.getInstance().getDefaultPsw()));
				// System.out.println(Tool.encoderByMd5(SystemOption.getInstance().getDefaultPsw()));
				tu.setPsw(MD5.MD5(SystemOption.getInstance().getDefaultPsw()));
				tu.setStatus(TUser.USER_STATUS_WORK);
				tuserserv.insertSelective(tu);
				response_write(getRM(SUCCESS, "操作成功"), response);
			} else {
				// tu.setStatus(this.getWebUserAttribute("user").getStatus());
				tuserserv.updateByPrimaryKeySelective(tu);
				 this.del(findcookie(request).getBytes());
				response_write(getRM(SUCCESS, "操作成功"), response);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			response_write(getRM(UNSUCCESS, "操作失败"), response);
		}

	}

	/**
	 * 锁定用户
	 * 
	 * @param tu
	 * @param response
	 * @throws IOException
	 */
	@LoginCheck(description = true)
	@RequestMapping(value = "/lockUser")
	@SystemControllerLog(description = "后台锁定用户")
	@ResponseBody
	public void lockUser(TUser tu, HttpServletResponse response) {
		try {
			TUser tus = tuserserv.selectByPrimaryKey(tu.getId());
			tus.setStatus(2);
			tuserserv.updateByPrimaryKey(tus);
			response_write(getRM(SUCCESS, "锁定成功"), response);
		} catch (Exception e) {
			logger.info(e.getMessage());
			response_write(getRM(UNSUCCESS, "锁定失败"), response);
		}
	}

	/**
	 * 解锁用户
	 * 
	 * @param tu
	 * @param response
	 * @throws IOException
	 */
	@LoginCheck(description = true)
	@RequestMapping(value = "/unlockUser")
	@SystemControllerLog(description = "后台解锁用户")
	@ResponseBody
	public void unlockUser(TUser tu, HttpServletResponse response) {
		try {
			TUser tus = tuserserv.selectByPrimaryKey(tu.getId());
			tus.setStatus(1);
			tuserserv.updateByPrimaryKey(tus);
			response_write(getRM(SUCCESS, "解锁成功"), response);
		} catch (Exception e) {
			logger.info(e.getMessage());
			response_write(getRM(UNSUCCESS, "解锁失败"), response);
		}

	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/resetPwdUser")
	@SystemControllerLog(description = "后台用户密码重置")
	@ResponseBody
	public void resetPwdUser(TUser tu, HttpServletResponse response,HttpServletRequest request) {
		try {
			TUser tus = tuserserv.selectByPrimaryKey(tu.getId());
			tus.setPsw(MD5.MD5(SystemOption.getInstance().getDefaultPsw()));
			tuserserv.updateByPrimaryKey(tus);
			 this.del(findcookie(request).getBytes());

			response_write(getRM(SUCCESS, "密码重置成功，密码：" + SystemOption.getInstance().getDefaultPsw()), response);
		} catch (Exception e) {
			logger.info(e.getMessage());
			response_write(getRM(UNSUCCESS, "重置失败"), response);
		}
	}

	/**
	 * 用户名检测
	 * 
	 * @param tu
	 * @param response
	 * @throws IOException
	 */
	@LoginCheck(description = true)
	@RequestMapping(value = "/checkUserName")
	@SystemControllerLog(description = "检查用户名是否存在")
	@ResponseBody
	public void checkUserName(TUser tu, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		long count = tuserserv.selectCountByUsername(tu.getUsername());
		if (count > 0) {
			out.print(false);
		} else {
			out.print(true);
		}
	}

	/**
	 * 密码修改验证
	 * 
	 * @param tu
	 * @param response
	 * @throws IOException
	 */
	@LoginCheck(description = true)
	@RequestMapping(value = "/checkPwd")
	@SystemControllerLog(description = "检查密码是否正确")
	@ResponseBody
	public void checkPwd(TUser tu, String oldPwd, HttpServletResponse response, HttpServletRequest request) throws IOException {
		PrintWriter out = response.getWriter();
		//TUser tus = this.getWebUserAttribute("user");
		TUser tus = (TUser) SerializeUtil.unserialize(this.get(this.findcookie(request).getBytes()));

		if (tus.getPsw().equals(MD5.MD5(oldPwd))) {
			out.print(true);
		} else {
			out.print(false);
		}
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/updatePwd")
	@SystemControllerLog(description = "修改密码")
	@ResponseBody
	public void updatePwd(TUser tu, String newPwd, HttpServletResponse response, HttpServletRequest request) {
		try {
			TUser currentUser = (TUser) SerializeUtil.unserialize(this.get(this.findcookie(request).getBytes()));
			currentUser.setPsw(MD5.MD5(newPwd));
			//this.addSessionWebUser("user", currentUser);
			tuserserv.updateByPrimaryKey(currentUser);
			this.del(findcookie(request).getBytes());
			response_write(getRM(SUCCESS, "密码修改成功，修改后密码为：【" + newPwd + "】请牢记"), response);
		} catch (Exception e) {
			logger.info("密码修改失败:" + e.getMessage());
			response_write(getRM(SUCCESS, "密码修改失败"), response);
		}
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/loadUser")
	@SystemControllerLog(description = "加载用户信息")
	@ResponseBody
	public void loadUser(TUser tu, HttpServletResponse response) {
		TUser tus = null;
		if (StringUtils.isNotEmpty(tu.getId())) {
			tus = tuserserv.selectByPrimaryKey(tu.getId());
			TDepartment tdp = deptserv.selectByPrimaryKey(tus.getDeptid());
			TStations tst = tstationsserv.selectByPrimaryKey(tus.getStationid());
			if (tdp != null || tst != null) {
				tus.setDeptname(tdp.getDname());
				tus.setStationname(tst.getStationname());
			} else {
				tus.setDeptname("无");
			}
		}
		response_write(tus, response);
	}

	@LoginCheck(description = true)
	@RequestMapping(value = "/deleteUser")
	@SystemControllerLog(description = "用户删除")
	@ResponseBody
	public void deleteUser(TUser tu, HttpServletResponse response,HttpServletRequest request) {
		if (tu.getId() != null) {
			tuserserv.deleteByPrimaryKey(tu.getId());
			this.del(findcookie(request).getBytes());
			response_write(getRM(SUCCESS, "删除成功"), response);
		} else {
			response_write(getRM(UNSUCCESS, "删除失败"), response);
		}
	}
}
