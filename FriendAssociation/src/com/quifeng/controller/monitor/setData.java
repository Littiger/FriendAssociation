package com.quifeng.controller.monitor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.monitor.setdataServlet;

/**
 * @desc   设置实时温湿度与平均数据
 * @author JZH
 * @time   2021年1月29日
 */
@SuppressWarnings("serial")
@WebServlet("/api/monitor/setdata")
public class setData extends HttpServlet{
	
	setdataServlet setDataServlet = new setdataServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setDataServlet.setData(request,response);
	}
}
