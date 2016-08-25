package com.app.service.navigation;

import java.util.List;
import java.util.Map;

import com.app.model.TNavigation;

public interface TNavigationInte {
	int deleteByPrimaryKey(Long id);

    int insert(TNavigation record);

    int insertSelective(TNavigation record);

    TNavigation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TNavigation record);

    int updateByPrimaryKey(TNavigation record);
    List selectAll(Long id);

    List<TNavigation> selectNavigation(List<Long> navids);
    List<TNavigation> selectNavigation2(Map<String, Object> params);
    List<TNavigation> selectListTNavigation(Long id);
    int selectAllByTNavigation(TNavigation record);

}
