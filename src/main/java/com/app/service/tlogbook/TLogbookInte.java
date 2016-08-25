package com.app.service.tlogbook;

import java.util.List;

import com.app.model.TLogbook;
import com.app.util.PageUtil;

public interface TLogbookInte {
	int deleteByPrimaryKey(Long id);

    int insert(TLogbook record);

    int insertSelective(TLogbook record);

    TLogbook selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TLogbook record);

    int updateByPrimaryKey(TLogbook record);
    public List<TLogbook> selectListTlog(PageUtil pu);
    
    int deletelogbook();
}
