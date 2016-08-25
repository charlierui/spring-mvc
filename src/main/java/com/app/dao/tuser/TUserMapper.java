package com.app.dao.tuser;

import java.util.List;
import java.util.Map;

import com.app.dto.TuserDto;
import com.app.model.TUser;
import com.app.util.PageUtil;

public interface TUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
    TUser selectByLogin(TUser tu);
    List<TuserDto> selectListTuser(PageUtil pu);
    int selectCountByUsername(String username);
    List<TuserDto> selectAll(Map map);
}