package com.app.service.tdictype;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.aop.SystemServiceLog;
import com.app.dao.tdictype.TDictypeMapper;
import com.app.dto.TDictypeDto;
import com.app.model.TDictype;

@Service("tdictypeserv")
public class TDictypeInteImpl implements TDictypeInte {
	@Autowired
	private TDictypeMapper tdictyoemap;

	@Override
	@SystemServiceLog(description="删除字段类别")
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return tdictyoemap.deleteByPrimaryKey(id);
	}

	@Override
	@SystemServiceLog(description="增加字典类别")
	public int insert(TDictype record) {
		// TODO Auto-generated method stub
		return tdictyoemap.insert(record);
	}

	@Override
	@SystemServiceLog(description="增加字段类别，可选择字段")
	public int insertSelective(TDictype record) {
		// TODO Auto-generated method stub
		return tdictyoemap.insertSelective(record);
	}

	@Override
	@SystemServiceLog(description="根据主键id查询字段类别")
	public TDictype selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return tdictyoemap.selectByPrimaryKey(id);
	}

	@Override
	@SystemServiceLog(description="更新字典类别信息，更新字段可选择")
	public int updateByPrimaryKeySelective(TDictype record) {
		// TODO Auto-generated method stub
		return tdictyoemap.updateByPrimaryKeySelective(record);
	}

	@Override
	@SystemServiceLog(description="更新字典类别信息，全部字段")
	public int updateByPrimaryKey(TDictype record) {
		// TODO Auto-generated method stub
		return tdictyoemap.updateByPrimaryKey(record);
	}

	@Override
	@SystemServiceLog(description="查询所有字段类别")
	public List<TDictype> selectAll() {
		// TODO Auto-generated method stub
		return tdictyoemap.selectAll();
	}

	@Override
	@SystemServiceLog(description="字典类别验证")
	public int selectCountByValue(String value) {
		// TODO Auto-generated method stub
		return tdictyoemap.selectCountByValue(value);
	}

	@Override
	@SystemServiceLog(description="字典下拉列表查询")
	public List<TDictypeDto> ditcombox(String typeValue) {
		// TODO Auto-generated method stub
		return tdictyoemap.ditcombox(typeValue);
	}

}
