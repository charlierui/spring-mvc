package com.app.service.log;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.log.LogMapper;
import com.app.model.Log;
@Service("loginteimpl")
public class LogInteImpl implements LogInte {
	@Autowired
	private LogMapper logmap;
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return logmap.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Log record) {
		// TODO Auto-generated method stub
		return logmap.insert(record);
	}

	@Override
	public int insertSelective(Log record) {
		// TODO Auto-generated method stub
		return logmap.insertSelective(record);
	}

	@Override
	public Log selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return logmap.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Log record) {
		// TODO Auto-generated method stub
		return logmap.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Log record) {
		// TODO Auto-generated method stub
		return logmap.updateByPrimaryKey(record);
	}

	@Override
	public Log getLatestLog(String sessionId, String ip) {
		// TODO Auto-generated method stub
		return logmap.getLatestLog(sessionId, ip);
	}

	@Override
	public int getPV(Timestamp startTime, Timestamp endTime) {
		// TODO Auto-generated method stub
		return logmap.getPV(startTime, endTime);
	}

	@Override
	public int getUV(Timestamp startTime, Timestamp endTime) {
		// TODO Auto-generated method stub
		return logmap.getUV(startTime, endTime);
	}

}
