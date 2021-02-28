package com.quifeng.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.user.ChangePwdServlet;

/**
 * @desc   修改密码
 * @author JZH
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/changepwd")
public class ChangePwdController extends HttpServlet{
	
	ChangePwdServlet changePwd = new ChangePwdServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		changePwd.changePwd(request,response);
	}
}
