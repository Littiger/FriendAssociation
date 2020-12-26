package com.turing.manage.listeners;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
/**
 * @desc  利用监听器实现预先加载一些数据
 * @author WYH
 * @time   2020-12-14
 */
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyLicenesListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	     System.out.println("===============servlet服务初始化==================");
	     
	}
    
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("===============servlet服务销毁==================");
	}

}
