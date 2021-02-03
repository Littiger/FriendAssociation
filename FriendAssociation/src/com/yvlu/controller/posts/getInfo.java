package com.yvlu.controller.posts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.posts.getInfoServlet;

/**
 * @desc   获取所有未审核的帖子信息
 * @author JZH
 * @time   2021年2月3日
 */
@SuppressWarnings("serial")
@WebServlet("/api/posts/getinfo")
public class getInfo extends HttpServlet{
	
	getInfoServlet getInfo = new getInfoServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getInfo.getInfo(request,response);
	}
}
