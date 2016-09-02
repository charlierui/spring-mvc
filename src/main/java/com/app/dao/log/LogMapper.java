package com.app.dao.log;

import java.sql.Timestamp;

import com.app.model.Log;

public interface LogMapper {
    int deleteByPrimaryKey(String id);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
    /**
	 * 查出最近的那条访问日志记录
	 * @param sessionId
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public Log getLatestLog(String sessionId, String ip);
	/**
	 * 统计某段时间内的PV数（页面访问数），每刷新一次页面就算一次访问
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	public int getPV(Timestamp startTime, Timestamp endTime);
	
	/**
	 * 统计某段时间内的UV数（独立访客数），同一段时间内，一个IP多次访问只算一个。
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	public int getUV(Timestamp startTime, Timestamp endTime);

}