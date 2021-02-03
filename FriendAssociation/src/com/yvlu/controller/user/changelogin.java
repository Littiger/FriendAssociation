package com.yvlu.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.user.changeLoginServlet;

/**
 * @desc   修改指定用户登录权限
 * @author JZH
 * @time   2021年2月3日
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/changelogin")
public class changelogin extends HttpServlet{
	
	changeLoginServlet changeLogin = new changeLoginServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		changeLogin.changeLogin(request,response);
	}
}
