package com.app.service.resdient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.aop.SystemServiceLog;
import com.app.dao.resident.ResidentMapper;
import com.app.model.Resident;
import com.app.util.PageUtil;
@Service("resdiserv")
public class ResdientServInteImpl implements ResdientServInte {
	@Autowired
	private ResidentMapper resdimapper;
	@Override
	public int deleteByPrimaryKey(String pkResident) {
		// TODO Auto-generated method stub
		return resdimapper.deleteByPrimaryKey(pkResident);
	}

	@Override
	public int insert(Resident record) {
		// TODO Auto-generated method stub
		return resdimapper.insert(record);
	}

	@Override
	public int insertSelective(Resident record) {
		// TODO Auto-generated method stub
		return resdimapper.insertSelective(record);
	}

	@Override
	public Resident selectByPrimaryKey(String pkResident) {
		// TODO Auto-generated method stub
		return resdimapper.selectByPrimaryKey(pkResident);
	}

	@Override
	public int updateByPrimaryKeySelective(Resident record) {
		// TODO Auto-generated method stub
		return resdimapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Resident record) {
		// TODO Auto-generated method stub
		return resdimapper.updateByPrimaryKey(record);
	}

	@SystemServiceLog(description = "selectByPagerIMpl方法")
	public List<Resident> selectByPager(PageUtil pu) {
		// TODO Auto-generated method stub
		return resdimapper.selectByPager(pu);
	}

}
