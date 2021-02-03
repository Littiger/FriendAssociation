/**
 * 
 */
package com.yvlu.controller.controller.update;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.controller.announcement.GetannouncementStatusServlet;
import com.yvlu.servlet.controller.update.GetUpdateServlet;
import com.yvlu.tools.tools;

/**
 * @desc 获取软件更新地址
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
@SuppressWarnings("serial")
@WebServlet("/api/controller/update/getinfo")
public class GetUpdateController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetUpdateServlet.Info(response);
	}
	
	
}
