package com.app.controller;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.model.Product;
import com.app.redis.RedisCache;
import com.app.redis.SerializeUtil;
import com.app.service.product.ProductInte;
import com.app.util.BaseController;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping(value = "miaosha")
public class ProductController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	// 使用ThreadLocal类管理共享的ThreadLocalBankAccount变量
	// 余额
	private static ThreadLocal<Integer> balance = new ThreadLocal<Integer>();
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	private Jedis jedis;
	@Autowired
	private ProductInte productimpl;

	// Integer count = 0;

	@RequestMapping(value = "chuku")
	public void chuku(final HttpServletRequest request, final HttpServletResponse response) {
		
		try {
			
			String balcount = null;
			synchronized (this) {
				balcount = get("balcount");
				
				int r = 1;// new Random().nextInt(2);
				
				int lastAccount = 0;
				if (checkexists(request.getSession().getId()) == true) {
					System.out.println("重复抢购");
					response_write(getRM(get("count"), "重复抢购"), response);
				} else if (Integer.valueOf(balcount) > 0) {
					String checkbal = get("balcount");
					lastAccount = Integer.valueOf(balcount) - r;
					set("balcount", lastAccount + "");

					Product pro = new Product();
					pro.setId(request.getSession().getId());
					pro.setProname("恭喜" + Thread.currentThread().getName() + "成功抢购iphone7,剩余" + lastAccount);
					pro.setUname(Thread.currentThread().getName());
					pro.setBuycount(lastAccount);
					this.lpush("chuku".getBytes(),SerializeUtil.serialize(pro));
					setex(request.getSession().getId(), 3600,request.getSession().getId());
					response_write(
							getRM(get("count"), "恭喜" + Thread.currentThread().getName() + "抢购成功,剩余" + lastAccount),
							response);

				} else {
					response_write(getRM(get("count"), "库存不足"), response);

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		} 

	}

}
