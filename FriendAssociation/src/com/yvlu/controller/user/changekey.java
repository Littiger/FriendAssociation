package com.yvlu.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.user.changeKeyServlet;

/**
 * @desc   修改指定用户密码
 * @author JZH
 * @time   2021年2月3日
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/changekey")
public class changekey extends HttpServlet{
	
	changeKeyServlet changeKey = new changeKeyServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		changeKey.changeKey(request,response);
	}
}
