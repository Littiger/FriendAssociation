package com.yvlu.controller.circle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.circle.getinfoServlet;

/**
 * @desc   获取所有版块信息
 * @author JZH
 * @time   2021年2月3日
 */
@SuppressWarnings("serial")
@WebServlet("/api/circle/getinfo")
public class getinfo extends HttpServlet{
	getinfoServlet getinfoServlet = new getinfoServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getinfoServlet.getCircleInfo(request,response);
	}
	
	
}
