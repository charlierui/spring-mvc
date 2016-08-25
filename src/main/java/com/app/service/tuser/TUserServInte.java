package com.app.service.tuser;

import java.util.List;
import java.util.Map;

import com.app.dto.TuserDto;
import com.app.model.TUser;
import com.app.util.PageUtil;

public interface TUserServInte {
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);
	/**
	 * 添加方法
	 * @param record
	 * @return
	 */
    int insert(TUser record);
    /**
     * 添加方法，会判断字段空值，如果为字段为空 则该字段不会拼接到inser语句中
     * @param record
     * @return
     */
    int insertSelective(TUser record);
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    TUser selectByPrimaryKey(Long id);
    /**
     * 添加方法，会判断字段空值，如果为字段为空 则该字段不会拼接到update语句中
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(TUser record);
    /**
     * 更新方法
     * @param record
     * @return
     */
    int updateByPrimaryKey(TUser record);
    /**
     * 登录方法
     * @param tu
     * @return
     */
    TUser selectByLogin(TUser tu);
    /**
     * 查询用户列表
     */
    List<TuserDto> selectListTuser(PageUtil pu);
    int selectCountByUsername(String username);
    List<TuserDto> selectAll(Map map);
}
