package com.quifeng.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.user.ChangePwdCodeServlet;

/**
 * @desc   修改密码，发送验证码
 * @author JZH
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/changepwdcode")
public class ChangePwdCodeController extends HttpServlet{
	
	ChangePwdCodeServlet changePwd = new ChangePwdCodeServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		changePwd.changePwdCode(request,response);
	}
}
