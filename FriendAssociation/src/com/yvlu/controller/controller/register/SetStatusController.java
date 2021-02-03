package com.yvlu.controller.controller.register;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.controller.register.GetStatusServlet;
import com.yvlu.servlet.controller.register.SetStatusServlet;
import com.yvlu.tools.tools;

/**
 * 
 * @desc 写入注册信息
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:15
 */
@SuppressWarnings("serial")
@WebServlet("/api/controller/register/poststatus")
public class SetStatusController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		String status = request.getParameter("status");
		
		
		if (token ==null || status ==null || "".equals(token)  || "".equals(status)) {
			//参数不全的
			tools.print(response, -1, "参数异常", null);
			return;			
		}else{
			if(status.equals("1") || status.equals("0")){
				SetStatusServlet.Info(token, status, response);
			}else{
				//status 不等于 1 或者2 的
				tools.print(response, -2, "参数值异常", null);
			}
		}
		
		
		
		
	}
}
