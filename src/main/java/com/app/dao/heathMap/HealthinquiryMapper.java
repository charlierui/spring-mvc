package com.app.dao.heathMap;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.app.model.Healthinquiry;
import com.app.util.PageUtil;

public interface HealthinquiryMapper {
    int deleteByPrimaryKey(String pkHealth);

    int insert(Healthinquiry record);

    int insertSelective(Healthinquiry record);

    Healthinquiry selectByPrimaryKey(String pkHealth);

    int updateByPrimaryKeySelective(Healthinquiry record);

    int updateByPrimaryKeyWithBLOBs(Healthinquiry record);

    int updateByPrimaryKey(Healthinquiry record);
    List<Healthinquiry> selectHealthByUserName(@Param("start") int start,@Param("end") int end);
    List<Healthinquiry> selectHealthByUserNamefilter(PageUtil pu);

}