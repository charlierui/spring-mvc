package com.app.dao.xinzlMap;

import com.app.model.Xinyizl;

public interface XinyizlMapper {
    int deleteByPrimaryKey(String pkXinyi);

    int insert(Xinyizl record);

    int insertSelective(Xinyizl record);

    Xinyizl selectByPrimaryKey(String pkXinyi);

    int updateByPrimaryKeySelective(Xinyizl record);

    int updateByPrimaryKey(Xinyizl record);
}