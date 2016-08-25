package com.app.service.health;

import java.util.List;

import com.app.model.Healthinquiry;
import com.app.util.PageUtil;

public interface HealthServInte {
	int deleteByPrimaryKey(String pkHealth);

    int insert(Healthinquiry record);

    int insertSelective(Healthinquiry record);

    Healthinquiry selectByPrimaryKey(String pkHealth);

    int updateByPrimaryKeySelective(Healthinquiry record);

    int updateByPrimaryKeyWithBLOBs(Healthinquiry record);

    int updateByPrimaryKey(Healthinquiry record);
    List<Healthinquiry> selectHealthByUserName(int start,int end);
    List<Healthinquiry> selectHealthByUserNamefilter(PageUtil pu);

}
