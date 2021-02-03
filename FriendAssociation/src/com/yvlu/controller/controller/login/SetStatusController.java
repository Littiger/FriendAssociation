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

import com.yvlu.servlet.controller.login.SetLoginStatusServlet;
import com.yvlu.tools.tools;

/**
 * @desc 写入登录控制状态
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
@SuppressWarnings("serial")
@WebServlet("/api/controller/login/poststatus")
public class SetStatusController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		String status = request.getParameter("status");
		if(tools.isnull(token,status)){
			tools.print(response, -1, "参数不全", null);
			
		}else{
			if(status.equals("1") || status.equals("0")){
				SetLoginStatusServlet.Info(token, status, response);
			}else{
				tools.print(response, -2, "参数值异常", null);
			}
		}
	
	
	
	
	}
}
