package com.app.service.navigation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.aop.SystemServiceLog;
import com.app.dao.tnavigation.TNavigationMapper;
import com.app.model.TNavigation;
@Service("navigationserv")
public class TNavigationInteImpl implements TNavigationInte {
@Autowired
private TNavigationMapper tnavigation;
	@Override
	@SystemServiceLog(description="根据id删除菜单信息")
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return tnavigation.deleteByPrimaryKey(id);
	}

	@Override
	@SystemServiceLog(description="保存菜单信息全字段")
	public int insert(TNavigation record) {
		// TODO Auto-generated method stub
		return tnavigation.insert(record);
	}

	@Override
	@SystemServiceLog(description="保存菜单信息拼接字段")
	public int insertSelective(TNavigation record) {
		// TODO Auto-generated method stub
		return tnavigation.insertSelective(record);
	}

	@Override
	@SystemServiceLog(description="根据主键id查询菜单信息")
	public TNavigation selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return tnavigation.selectByPrimaryKey(id);
	}

	@Override
	@SystemServiceLog(description="更新菜单信息，拼接字段")
	public int updateByPrimaryKeySelective(TNavigation record) {
		// TODO Auto-generated method stub
		return tnavigation.updateByPrimaryKeySelective(record);
	}

	@Override
	@SystemServiceLog(description="更新菜单信息全部字段更新")
	public int updateByPrimaryKey(TNavigation record) {
		// TODO Auto-generated method stub
		return tnavigation.updateByPrimaryKey(record);
	}

	@Override
	@SystemServiceLog(description="权限菜单查询")
	public List<TNavigation> selectNavigation(List<Long> navids) {
		// TODO Auto-generated method stub
		return tnavigation.selectNavigation(navids);
	}
	


	@Override
	@SystemServiceLog(description="根据id查询所有菜单信息")
	public List selectAll(Long id) {
		// TODO Auto-generated method stub
		return tnavigation.selectAll(id);
	}

	@Override
	@SystemServiceLog(description="查询菜单信息，map形式传参数")
	public List<TNavigation> selectNavigation2(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tnavigation.selectNavigation2(params);
	}

	@Override
	@SystemServiceLog(description="查询菜单信息，根据父节点")
	public List<TNavigation> selectListTNavigation(Long id) {
		// TODO Auto-generated method stub
		return tnavigation.selectListTNavigation(id);
	}

	@Override
	@SystemServiceLog(description="查询总数")
	public int selectAllByTNavigation(TNavigation record) {
		return tnavigation.selectAllByTNavigation(record);
	}

}
