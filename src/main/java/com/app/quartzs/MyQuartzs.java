package com.app.quartzs;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.app.service.log.LogInte;
import com.app.util.BaseController;

@Component
@Lazy(value = false)
public class MyQuartzs extends BaseController{
	private Logger logger = Logger.getLogger(MyQuartzs.class);
	@Autowired 
	LogInte loginteimpl;
	@Scheduled(cron = "0 */60 * * * ?") // 每隔5秒执行一次
	public void test() throws Exception {
		logger.info("统计访问量定时任务执行");
		//统计网站的PV（页面浏览量），UV（独立访客数）
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String format = df.format(new Date());
				Date parse = null;
				try {
					parse = df.parse(format);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				Timestamp startTime = new Timestamp(parse.getTime());
				Timestamp endTime = new Timestamp(parse.getTime() + 24*3600*1000);
				try {
					int pv = loginteimpl.getPV(startTime, endTime);
					int uv = loginteimpl.getUV(startTime, endTime);
					this.set("pv", pv+"");
					this.set("uv", uv+"");
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
		
	}

	// @Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点整
	// @Scheduled(cron = "0 30 0 * * ?")//每天凌晨0点30分
	// @Scheduled(cron = "0 */60 * * * ?")//1小时处理一次
	//	@Scheduled(cron = "0/5 * *  * * ?") // 每隔5秒执行一次

}