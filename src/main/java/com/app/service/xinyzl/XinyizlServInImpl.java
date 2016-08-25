package com.app.service.xinyzl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.aop.SystemServiceLog;
import com.app.dao.xinzlMap.XinyizlMapper;
import com.app.model.Xinyizl;
@Service("xinyizlService")
public class XinyizlServInImpl implements XinyizlServInte {
	@Autowired
	private XinyizlMapper xinyizlMapp;
	public int deleteByPrimaryKey(String pkXinyi) {
		// TODO Auto-generated method stub
		return xinyizlMapp.deleteByPrimaryKey(pkXinyi);
	}
	@SystemServiceLog(description = "增加用户Service")
	public int insert(Xinyizl record) {
		// TODO Auto-generated method stub
		int a=xinyizlMapp.insert(record);
		//事务测试
		if(a<=0){ 
			throw new RuntimeException();
		}
		return a;
	}

	public int insertSelective(Xinyizl record) {
		// TODO Auto-generated method stub
		return xinyizlMapp.insertSelective(record);
	}
	@SystemServiceLog(description = "查询用户Service")
	public Xinyizl selectByPrimaryKey(String pkXinyi){		
		return xinyizlMapp.selectByPrimaryKey(pkXinyi);
	}

	public int updateByPrimaryKeySelective(Xinyizl record) {
		// TODO Auto-generated method stub
		return xinyizlMapp.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(Xinyizl record) {
		// TODO Auto-generated method stub
		return xinyizlMapp.updateByPrimaryKey(record);
	}

}
