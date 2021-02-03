package com.yvlu.controller.controller.register;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.controller.register.GetStatusServlet;
import com.yvlu.tools.tools;

/**
 * @desc 获取 用户端注册控制
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月2日 下午4:04:48
 * @code 
 * 		-1  未登录
 * 		-2 获取应用信息失败
 */
@SuppressWarnings("serial")
@WebServlet("/api/controller/register/getstatus")
public class GetStatusController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token =request.getParameter("token");
		if ( token ==null || "".equals(token)) {
			tools.print(response, -1, "未登录", null);
		}else{
			GetStatusServlet.Info(token, response);
		}
	}
}
