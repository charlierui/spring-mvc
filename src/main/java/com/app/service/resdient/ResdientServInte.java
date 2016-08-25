package com.app.service.resdient;

import java.util.List;

import com.app.model.Resident;
import com.app.util.PageUtil;

public interface ResdientServInte {
	int deleteByPrimaryKey(String pkResident);

    int insert(Resident record);

    int insertSelective(Resident record);

    Resident selectByPrimaryKey(String pkResident);

    int updateByPrimaryKeySelective(Resident record);

    int updateByPrimaryKey(Resident record);
    List<Resident> selectByPager(PageUtil pu);

}
