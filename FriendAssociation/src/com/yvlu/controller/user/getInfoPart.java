package com.yvlu.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.user.getInfoPartServlet;

/**
 * @desc   获取部分用户
 * @author JZH
 * @time   2021年2月3日
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/getinfopart")
public class getInfoPart extends HttpServlet{
	
	getInfoPartServlet getInfoPart = new getInfoPartServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getInfoPart.getInfoPart(request,response);
	}
	
	
}
