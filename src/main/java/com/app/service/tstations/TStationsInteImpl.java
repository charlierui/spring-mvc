package com.app.service.tstations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.aop.SystemServiceLog;
import com.app.dao.tstations.TStationsMapper;
import com.app.model.TStations;
import com.app.util.PageUtil;

@Service("tstationsserv")
public class TStationsInteImpl implements TStationsInte {
	@Autowired
	private TStationsMapper tstationsmap;

	@Override
	@SystemServiceLog(description = "删除岗位信息")
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return tstationsmap.deleteByPrimaryKey(id);
	}

	@Override
	@SystemServiceLog(description = "增加岗位信息，全部字段")
	public int insert(TStations record) {
		// TODO Auto-generated method stub
		return tstationsmap.insert(record);
	}

	@Override
	@SystemServiceLog(description = "增加岗位信息，可选择字段")
	public int insertSelective(TStations record) {
		// TODO Auto-generated method stub
		return tstationsmap.insertSelective(record);
	}

	@Override
	@SystemServiceLog(description = "根据主键id查询岗位信息")
	public TStations selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return tstationsmap.selectByPrimaryKey(id);
	}

	@Override
	@SystemServiceLog(description = "更新岗位信息，字段可选择")
	public int updateByPrimaryKeySelective(TStations record) {
		// TODO Auto-generated method stub
		return tstationsmap.updateByPrimaryKeySelective(record);
	}

	@Override
	@SystemServiceLog(description = "更新岗位信息，全字段")
	public int updateByPrimaryKey(TStations record) {
		// TODO Auto-generated method stub
		return tstationsmap.updateByPrimaryKey(record);
	}

	@Override
	@SystemServiceLog(description = "岗位信息分页查询")
	public List<TStations> selectListTstations(PageUtil pu) {
		// TODO Auto-generated method stub
		return tstationsmap.selectListTstations(pu);
	}

	@Override
	@SystemServiceLog(description = "根据stationcode查询岗位是否存在")
	public int selectCountByStationcode(String stationcode) {
		// TODO Auto-generated method stub
		return tstationsmap.selectCountByStationcode(stationcode);
	}

	@Override
	@SystemServiceLog(description = "查询所有岗位信息，用于下拉列表")
	public List<TStations> selectall() {
		// TODO Auto-generated method stub
		return tstationsmap.selectall();
	}

}
