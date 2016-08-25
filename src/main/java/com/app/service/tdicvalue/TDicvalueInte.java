package com.app.service.tdicvalue;

import java.util.List;
import java.util.Map;

import com.app.model.TDicvalue;
import com.app.util.PageUtil;

public interface TDicvalueInte {
	int deleteByPrimaryKey(Long vid);

    int insert(TDicvalue record);

    int insertSelective(TDicvalue record);

    TDicvalue selectByPrimaryKey(Long vid);

    int updateByPrimaryKeySelective(TDicvalue record);

    int updateByPrimaryKey(TDicvalue record);
    List<TDicvalue> selectByTid(PageUtil pu);
    int selectByTid1(Map map);
    int selectById(TDicvalue record);
    int selectBySoftcode(TDicvalue record);

}
