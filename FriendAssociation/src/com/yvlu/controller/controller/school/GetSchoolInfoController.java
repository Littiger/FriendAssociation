/**
 * 
 */
package com.yvlu.controller.controller.school;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.controller.announcement.GetannouncementStatusServlet;
import com.yvlu.servlet.controller.login.GetLoginStatusServlet;
import com.yvlu.servlet.controller.school.GetSchoolInfoServlet;
import com.yvlu.tools.tools;

/**
 * @desc 获取学校信息
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
@SuppressWarnings("serial")
@WebServlet("/api/controller/school/getinfo")
public class GetSchoolInfoController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		if (tools.isnull(token)) {
			 tools.print(response, -1, "参数不全", null);
		}else{
			GetSchoolInfoServlet.Info(token, response);
		}
		
		
		
	}
	
	
}
