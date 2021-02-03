/**
 * 
 */
package com.yvlu.controller.controller.version;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.controller.version.SetversionServlet;
import com.yvlu.tools.tools;

/**
 * @desc 写入软件版本
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
@SuppressWarnings("serial")
@WebServlet("/api/controller/version/postinfo")
public class SetStatusController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		String info = request.getParameter("info");
		if(tools.isnull(token,info)){
			tools.print(response, -1, "参数不全", null);
		}else{
			SetversionServlet.Info(token, info, response);
		}
	
	
	
	
	}
}
