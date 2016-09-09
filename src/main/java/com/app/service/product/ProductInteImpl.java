package com.app.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.aop.SystemServiceLog;
import com.app.dao.product.ProductMapper;
import com.app.model.Product;
@Service("productimpl")
public class ProductInteImpl implements ProductInte {
	@Autowired
	private ProductMapper product;
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return product.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Product record) {
		// TODO Auto-generated method stub
		return product.insert(record);
	}

	@Override
	public int insertSelective(Product record) {
		// TODO Auto-generated method stub
		return product.insertSelective(record);
	}

	@Override
	public Product selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return product.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Product record) {
		// TODO Auto-generated method stub
		return product.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Product record) {
		return product.updateByPrimaryKey(record);
	}

}
