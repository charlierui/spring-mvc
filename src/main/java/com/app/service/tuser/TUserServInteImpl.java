package com.app.service.tuser;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.aop.SystemServiceLog;
import com.app.dao.tuser.TUserMapper;
import com.app.dto.TuserDto;
import com.app.model.TUser;
import com.app.util.PageUtil;
@Service("tuserserv")
public class TUserServInteImpl implements TUserServInte{
	@Autowired
	private TUserMapper tusermapper;  
	@Override
	@SystemServiceLog(description = "根据主键id删除用户")
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return tusermapper.deleteByPrimaryKey(id);
	}

	@Override
	@SystemServiceLog(description = "保存用户信息")
	public int insert(TUser record) {
		// TODO Auto-generated method stub
		return tusermapper.insert(record);
	}

	@Override
	@SystemServiceLog(description = "保存用户信息，可选择字段")
	public int insertSelective(TUser record) {
		// TODO Auto-generated method stub
        //DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
		int res=tusermapper.insertSelective(record);
		if(res==0){
			throw new RuntimeException();
		}
		return res;
	}

	@Override
	@SystemServiceLog(description = "根据主键id查询用户信息")
	public TUser selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return tusermapper.selectByPrimaryKey(id);
	}

	@Override
	@SystemServiceLog(description = "更新用户信息，可选择字段")
	public int updateByPrimaryKeySelective(TUser record) {
		// TODO Auto-generated method stub
        //DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);

		return tusermapper.updateByPrimaryKeySelective(record);
	}

	
	@Override
	@SystemServiceLog(description = "更新用户信心，全字段")
	public int updateByPrimaryKey(TUser record) {
		// TODO Auto-generated method stub
        //DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);

		return tusermapper.updateByPrimaryKey(record);
	}

	@Override
	@SystemServiceLog(description = "登录方法")
	public TUser selectByLogin(TUser tu) {
		// TODO Auto-generated method stub
        //DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);

		return tusermapper.selectByLogin(tu);
	}

	@Override
	@SystemServiceLog(description = "用户列表分页查询")
	public List<TuserDto> selectListTuser(PageUtil pu) {
		// TODO Auto-generated method stub
        //DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);

		return tusermapper.selectListTuser(pu);
	}

	@Override
	@SystemServiceLog(description = "根据username查询用户是否存在")
	public int selectCountByUsername(String username) {
		// TODO Auto-generated method stub
        //DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);

		return tusermapper.selectCountByUsername(username);
	}

	@Override
	@SystemServiceLog(description = "查询所有用户，map形式传参")
	public List<TuserDto> selectAll(Map map) {
		// TODO Auto-generated method stub
		return tusermapper.selectAll(map);
	}

}
