package com.app.service.tlogbook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.aop.SystemServiceLog;
import com.app.dao.tlogbook.TLogbookMapper;
import com.app.model.TLogbook;
import com.app.util.PageUtil;

@Service("tlogbookserv")
public class TLogbookInteImpl implements TLogbookInte {
	@Autowired
	private TLogbookMapper logbookmap;

	@Override
	@SystemServiceLog(description = "删除日志信息")
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return logbookmap.deleteByPrimaryKey(id);
	}

	@Override
	@SystemServiceLog(description = "增加日志信息，全字段")
	public int insert(TLogbook record) {
		// TODO Auto-generated method stub
		return logbookmap.insert(record);
	}

	@Override
	@SystemServiceLog(description = "增加日志信息，字段可选择")
	public int insertSelective(TLogbook record) {
		// TODO Auto-generated method stub
		return logbookmap.insertSelective(record);
	}

	@Override
	@SystemServiceLog(description = "根据主键id查询日志信息")
	public TLogbook selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return logbookmap.selectByPrimaryKey(id);
	}

	@Override
	@SystemServiceLog(description = "更新日志信息，字段可选择")
	public int updateByPrimaryKeySelective(TLogbook record) {
		// TODO Auto-generated method stub
		return logbookmap.updateByPrimaryKeySelective(record);
	}

	@Override
	@SystemServiceLog(description = "更新日志信息，全部字段")
	public int updateByPrimaryKey(TLogbook record) {
		// TODO Auto-generated method stub
		return logbookmap.updateByPrimaryKey(record);
	}

	@Override
	@SystemServiceLog(description = "日志列表查询")
	public List<TLogbook> selectListTlog(PageUtil pu) {
		// TODO Auto-generated method stub
		return logbookmap.selectListTlog(pu);
	}

	@Override
	@SystemServiceLog(description = "日志列表清表")
	public int deletelogbook() {
		// TODO Auto-generated method stub
		return logbookmap.deletelogbook();
	}

}
