package com.app.controller;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.model.Product;
import com.app.service.product.ProductInte;
import com.app.util.BaseController;

@Controller
@RequestMapping(value = "miaosha")
public class ProductController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	// 使用ThreadLocal类管理共享的ThreadLocalBankAccount变量
	// 余额
	private static ThreadLocal<Integer> balance = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 100;
		};
	};
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	@Autowired
	private ProductInte productimpl;

	Integer count = 100;

	@RequestMapping(value = "chuku")
	public void chuku(HttpServletRequest request, HttpServletResponse response, Product pro) {
		// makeWithdraw(1);
		if (count > 0) {
			pro = new Product();
			pro.setId(request.getSession().getId());
			pro.setProname("iphone7");
			pro.setUname(request.getSession().getId());
			pro.setBuycount(count--);
			productimpl.insert(pro);
		}
		response_write(getRM(this.get("count"), "登录成功"), response);
	}

	/**
	 * makeWithdraw 账户取款
	 * 
	 * @param amount
	 *            取款金额<br />
	 *            打印log记录取款过程
	 */
	private void makeWithdraw(int amount) {
		if (getBalance() >= amount) { // 如果余额足够则取款
			// System.out.println("☆"+Thread.currentThread().getName()+"
			// 准备取款!");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + "   准备取款,等待0.5s线程中断!" + e.getMessage());
			}
			withdraw(amount);
			System.out.println("☆" + Thread.currentThread().getName() + "   完成" + amount + "购买!剩余" + getBalance());
		} else { // 余额不足则提示
			System.out.println("数量不足" + getBalance());
		}
	}

	// 查询
	public int getBalance() {
		return balance.get();
	}

	// 取款
	public void withdraw(int amount) {
		balance.set(balance.get() - amount);
	}

	// 存款
	public void deposit(int amount) {
		balance.set(balance.get() + amount);
	}
}
