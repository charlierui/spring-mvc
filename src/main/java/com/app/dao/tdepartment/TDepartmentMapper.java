package com.app.dao.tdepartment;

import java.util.List;

import com.app.model.TDepartment;
import com.app.util.PageUtil;

public interface TDepartmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TDepartment record);

    int insertSelective(TDepartment record);

    TDepartment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TDepartment record);

    int updateByPrimaryKey(TDepartment record);
    List<TDepartment> selectListTdept(Long id);
    List selectListTdeptByparentId(Long id);
    int selectAll();

}