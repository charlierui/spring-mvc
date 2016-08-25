package com.app.service.tdictype;

import java.util.List;

import com.app.dto.TDictypeDto;
import com.app.model.TDictype;

public interface TDictypeInte {
	int deleteByPrimaryKey(Long id);

    int insert(TDictype record);

    int insertSelective(TDictype record);

    TDictype selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TDictype record);

    int updateByPrimaryKey(TDictype record);
    List<TDictype> selectAll();
    int selectCountByValue(String value);
    List<TDictypeDto> ditcombox(String typeValue);


}
