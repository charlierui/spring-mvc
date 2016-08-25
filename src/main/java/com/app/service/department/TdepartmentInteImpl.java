package com.app.service.department;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.aop.SystemServiceLog;
import com.app.dao.tdepartment.TDepartmentMapper;
import com.app.model.TDepartment;
import com.app.util.PageUtil;
@Service("deptserv")
public class TdepartmentInteImpl implements TdepartmentInte {
	@Autowired
	private TDepartmentMapper deptmap;
	
	@Override
	@SystemServiceLog(description = "删除部门Service")
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return deptmap.deleteByPrimaryKey(id);
	}

	@Override
	@SystemServiceLog(description = "增加部门Service")

	public int insert(TDepartment record) {
		// TODO Auto-generated method stub
		return deptmap.insert(record);
	}

	@Override
	@SystemServiceLog(description = "增加部门Service")
	public int insertSelective(TDepartment record) {
		// TODO Auto-generated method stub
		return deptmap.insertSelective(record);
	}

	@Override
	@SystemServiceLog(description="根据主键查询部门信息")
	public TDepartment selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return deptmap.selectByPrimaryKey(id);
	}

	@Override
	@SystemServiceLog(description="更新部门信息，字段可为空")
	public int updateByPrimaryKeySelective(TDepartment record) {
		// TODO Auto-generated method stub
		return deptmap.updateByPrimaryKeySelective(record);
	}

	@Override
	@SystemServiceLog(description="更新部门信息全部字段")
	public int updateByPrimaryKey(TDepartment record) {
		// TODO Auto-generated method stub
		return deptmap.updateByPrimaryKey(record);
	}

	@Override
	@SystemServiceLog(description="根据ID查询部门集合")
	public List<TDepartment> selectListTdept(Long id) {
		// TODO Auto-generated method stub
		return deptmap.selectListTdept(id);
	}

	@Override	
	@SystemServiceLog(description="根据父节点ID查询集合")

	public List selectListTdeptByparentId(Long id) {
		// TODO Auto-generated method stub
		return deptmap.selectListTdeptByparentId(id);
	}

	@Override
	@SystemServiceLog(description="查询所有部门信息")
	public int selectAll() {
		// TODO Auto-generated method stub
		return deptmap.selectAll();
	}

}
