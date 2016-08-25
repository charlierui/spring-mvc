package com.app.dao.tstations;

import java.util.List;

import com.app.model.TStations;
import com.app.util.PageUtil;

public interface TStationsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TStations record);

    int insertSelective(TStations record);

    TStations selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TStations record);

    int updateByPrimaryKey(TStations record);
    List<TStations> selectListTstations(PageUtil pu);
    int selectCountByStationcode(String stationcode);
    List<TStations> selectall();
}