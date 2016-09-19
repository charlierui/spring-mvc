package com.app.quartzs;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.model.Product;
import com.app.redis.SerializeUtil;
import com.app.service.product.ProductInte;
import com.app.util.BaseController;

@Component
@Lazy(value = false)
public class mias extends BaseController {
	private Logger logger = Logger.getLogger(MyQuartzs.class);
	@Autowired
	private ProductInte productimpl;

	@Scheduled(cron = "0 */5 * * * ?") // 每小时执行一次
	public void test() throws Exception {
		// 得到缓存
		List<byte[]> pro1 = lrange("chuku".getBytes(), 0, -1);
		Product pro = null;
		for (int i = 0; i < pro1.size(); i++) {
			pro = (Product) SerializeUtil.unserialize(pro1.get(i));
			Product checkp = productimpl.selectByPrimaryKey(pro.getId());
			if (checkp != null) {
				logger.info("已存在");
			}else{
				productimpl.insert(pro);
			}
			
		}
		this.del("chuku".getBytes());
	}

}
