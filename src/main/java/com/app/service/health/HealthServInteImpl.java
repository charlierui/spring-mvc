package com.app.service.health;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.heathMap.HealthinquiryMapper;
import com.app.model.Healthinquiry;
import com.app.util.PageUtil;
@Service("heathService")
public class HealthServInteImpl implements HealthServInte {
	@Autowired
	private HealthinquiryMapper healthmapper;
	public int deleteByPrimaryKey(String pkHealth) {
		// TODO Auto-generated method stub
		return healthmapper.deleteByPrimaryKey(pkHealth);
	}

	public int insert(Healthinquiry record) {
		// TODO Auto-generated method stub
		return healthmapper.insert(record);
	}

	public int insertSelective(Healthinquiry record) {
		// TODO Auto-generated method stub
		return healthmapper.insertSelective(record);
	}

	public Healthinquiry selectByPrimaryKey(String pkHealth) {
		// TODO Auto-generated method stub
		return healthmapper.selectByPrimaryKey(pkHealth);
	}

	public int updateByPrimaryKeySelective(Healthinquiry record) {
		// TODO Auto-generated method stub
		return healthmapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKeyWithBLOBs(Healthinquiry record) {
		// TODO Auto-generated method stub
		return healthmapper.updateByPrimaryKeyWithBLOBs(record);
	}

	public int updateByPrimaryKey(Healthinquiry record) {
		// TODO Auto-generated method stub
		return healthmapper.updateByPrimaryKey(record);
	}

	public List<Healthinquiry> selectHealthByUserName(int start,int end) {
		// TODO Auto-generated method stub
		return healthmapper.selectHealthByUserName(start,end);
	}

	public List<Healthinquiry> selectHealthByUserNamefilter(PageUtil pu) {
		// TODO Auto-generated method stub
		return healthmapper.selectHealthByUserNamefilter(pu);
	}

	
}
