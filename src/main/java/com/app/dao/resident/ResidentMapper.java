package com.app.dao.resident;

import java.util.List;

import com.app.model.Resident;
import com.app.util.PageUtil;

public interface ResidentMapper {
    int deleteByPrimaryKey(String pkResident);

    int insert(Resident record);

    int insertSelective(Resident record);

    Resident selectByPrimaryKey(String pkResident);

    int updateByPrimaryKeySelective(Resident record);

    int updateByPrimaryKey(Resident record);
    List<Resident> selectByPager(PageUtil pu);
}