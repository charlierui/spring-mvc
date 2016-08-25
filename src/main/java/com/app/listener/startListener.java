package com.app.listener;



import java.util.Timer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;


public class startListener implements ServletContextListener {
    private ServletContext application;
    private Logger logger = Logger.getLogger(startListener.class);
    Timer timer = null;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		logger.info("应用程序注销……");
        timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		 logger.info("应用程序初始化开始……");
	    timer = new Timer(true);

	    application = arg0.getServletContext();
	   	application.setAttribute("ctx", application.getContextPath());
        logger.debug("应用程序初始化完成。");

	}

}
