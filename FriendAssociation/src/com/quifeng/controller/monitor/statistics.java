package com.quifeng.controller.monitor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.monitor.statisticsServlet;

/**
 * @desc   获取统计列表
 * @author JZH
 * @time   2021年1月29日
 */
@SuppressWarnings("serial")
@WebServlet("/api/monitor/statistics")
public class statistics extends HttpServlet{
	
	statisticsServlet servlet = new statisticsServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		servlet.getTongJi(request,response);
	}
	
}
