/**
 * 
 */
package com.yvlu.controller.controller.announcement;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.controller.announcement.SetannouncementStatusServlet;
import com.yvlu.tools.tools;

/**
 * @desc 发布公告
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
@SuppressWarnings("serial")
@WebServlet("/api/controller/announcement/postinfo")
public class SetStatusController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		String info = request.getParameter("info");
		if(tools.isnull(token,info)){
			tools.print(response, -1, "参数不全", null);
		}else{
			SetannouncementStatusServlet.Info(token, info, response);
			
		}
	
	
	
	
	}
}
