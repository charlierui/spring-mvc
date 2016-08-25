package com.app.service.tdicvalue;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.aop.SystemServiceLog;
import com.app.dao.tdicvalue.TDicvalueMapper;
import com.app.model.TDicvalue;
import com.app.util.PageUtil;

@Service("tdicvalueserv")
public class TDicvalueInteImpl implements TDicvalueInte {
	@Autowired
	private TDicvalueMapper tdicvamap;

	@Override
	@SystemServiceLog(description="删除字典内容")
	public int deleteByPrimaryKey(Long vid) {
		// TODO Auto-generated method stub
		return tdicvamap.deleteByPrimaryKey(vid);
	}

	@Override
	@SystemServiceLog(description="增加字典内容，全字段")
	public int insert(TDicvalue record) {
		// TODO Auto-generated method stub
		return tdicvamap.insert(record);
	}

	@Override
	@SystemServiceLog(description="增加字典内容，字段可选择")
	public int insertSelective(TDicvalue record) {
		// TODO Auto-generated method stub
		return tdicvamap.insertSelective(record);
	}

	@Override
	@SystemServiceLog(description="根据主键vid查询字典的内容")
	public TDicvalue selectByPrimaryKey(Long vid) {
		// TODO Auto-generated method stub
		return tdicvamap.selectByPrimaryKey(vid);
	}

	@Override
	@SystemServiceLog(description="更新字典内容，字段可选择")
	public int updateByPrimaryKeySelective(TDicvalue record) {
		// TODO Auto-generated method stub
		return tdicvamap.updateByPrimaryKeySelective(record);
	}

	@Override
	@SystemServiceLog(description="更新字典内容，全字段")
	public int updateByPrimaryKey(TDicvalue record) {
		// TODO Auto-generated method stub
		return tdicvamap.updateByPrimaryKey(record);
	}

	@Override
	@SystemServiceLog(description="根据tid查询字段内容")
	public List<TDicvalue> selectByTid(PageUtil pu) {
		// TODO Auto-generated method stub
		return tdicvamap.selectByTid(pu);
	}

	@Override
	@SystemServiceLog(description="根据tid查询字段内容，map形式传参")
	public int selectByTid1(Map map) {
		// TODO Auto-generated method stub
		return tdicvamap.selectByTid1(map);
	}

	@Override
	@SystemServiceLog(description="根据id查询字段内容")
	public int selectById(TDicvalue record) {
		// TODO Auto-generated method stub
		return tdicvamap.selectById(record);
	}

	@Override
	@SystemServiceLog(description="根据Softcode查询字段内容")
	public int selectBySoftcode(TDicvalue record) {
		// TODO Auto-generated method stub
		return tdicvamap.selectBySoftcode(record);
	}

}
