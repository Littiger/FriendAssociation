package com.yvlu.controller.posts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.posts.auditServlet;

/**
 * @desc   审核指定帖子
 * @author JZH
 * @time   2021年2月3日
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/audit")
public class audit extends HttpServlet{
	
	auditServlet audit = new auditServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		audit.auditPost(request,response);
	}
}
