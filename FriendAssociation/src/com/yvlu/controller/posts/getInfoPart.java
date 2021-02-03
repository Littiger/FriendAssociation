package com.yvlu.controller.posts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.posts.getInfoPartServlet;

/**
 * @desc   搜索部分未审核的帖子信息
 * @author JZH
 * @time   2021年2月3日
 */
@SuppressWarnings("serial")
@WebServlet("/api/posts/getinfopart")
public class getInfoPart extends HttpServlet{
	
	getInfoPartServlet getInfoPart = new getInfoPartServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getInfoPart.getInfoPart(request,response);
	}
}
