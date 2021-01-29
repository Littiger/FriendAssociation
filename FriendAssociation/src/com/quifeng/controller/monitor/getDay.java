package com.quifeng.controller.monitor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.monitor.getdayServlet;

/**
 * @desc   获取有数据DAY序号
 * @author JZH
 * @time   2021年1月29日
 */
@SuppressWarnings("serial")
@WebServlet("/api/monitor/getday")
public class getDay extends HttpServlet{
	
	getdayServlet getdayServlet = new getdayServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getdayServlet.getDay(request,response);
	}
	
}
