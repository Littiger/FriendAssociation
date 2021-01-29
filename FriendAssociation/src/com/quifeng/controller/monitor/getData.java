package com.quifeng.controller.monitor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.monitor.getdataServlet;

/**
 * @desc   获取实时温湿度
 * @author JZH
 * @time  2021年1月29日
 */
@SuppressWarnings("serial")
@WebServlet("/api/monitor/getdata")
public class getData extends HttpServlet{
	
	getdataServlet getDataServlet = new getdataServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getDataServlet.getData(request,response);
	}
}
