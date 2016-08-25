package com.app.service.xinyzl;

import com.app.model.Xinyizl;

public interface XinyizlServInte {
	int deleteByPrimaryKey(String pkXinyi);

    int insert(Xinyizl record);

    int insertSelective(Xinyizl record);

    Xinyizl selectByPrimaryKey(String pkXinyi);

    int updateByPrimaryKeySelective(Xinyizl record);

    int updateByPrimaryKey(Xinyizl record);
}
