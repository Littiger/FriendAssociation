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

import com.yvlu.servlet.controller.announcement.GetannouncementStatusServlet;
import com.yvlu.tools.tools;

/**
 * @desc 获取公告
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
@SuppressWarnings("serial")
@WebServlet("/api/controller/announcement/getinfo")
public class GetStatusController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetannouncementStatusServlet.Info(response);
	}
	
	
}
