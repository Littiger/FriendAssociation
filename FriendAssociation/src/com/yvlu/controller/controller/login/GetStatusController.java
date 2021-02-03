/**
 * 
 */
package com.yvlu.controller.controller.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.controller.login.GetLoginStatusServlet;
import com.yvlu.tools.tools;

/**
 * @desc 获取登录控制状态
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
@SuppressWarnings("serial")
@WebServlet("/api/controller/login/getstatus")
public class GetStatusController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String token = request.getParameter("token");
		 if (tools.isnull(token)) {
			 tools.print(response, -1, "参数不全", null);
		}else{
			GetLoginStatusServlet.Info(token, response);
		}
		 
	}
	
	
}
