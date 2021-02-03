package com.yvlu.controller.posts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.posts.getInfoedPartServlet;

/**
 * @desc   搜索部分审核过的帖子信息
 * @author JZH
 * @time   2021年2月3日
 */
@SuppressWarnings("serial")
@WebServlet("/api/posts/getinfoedpart")
public class getInfoedPart extends HttpServlet{
	
	getInfoedPartServlet getInfoedPart = new getInfoedPartServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getInfoedPart.getInfoedPart(request,response);
	}
}
