package com.yvlu.controller.posts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.posts.getInfoedServlet;

/**
 * @desc   获取所有审核过了的帖子信息
 * @author JZH
 * @time   2021年2月3日
 */
@SuppressWarnings("serial")
@WebServlet("/api/posts/getinfoed")
public class getInfoed extends HttpServlet{
	
	getInfoedServlet getinfoed = new getInfoedServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getinfoed.getInfoed(request,response);
	}
}
