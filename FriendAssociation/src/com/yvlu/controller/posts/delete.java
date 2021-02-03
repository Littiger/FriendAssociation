package com.yvlu.controller.posts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.posts.deleteServlet;

/**
 * @desc   删除指定帖子(所有帖子,包括未审核)
 * @author JZH
 * @time   2021年2月3日
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/delete")
public class delete extends HttpServlet{
	
	deleteServlet delete = new deleteServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		delete.deletePost(request,response);
	}
}
