package com.yvlu.controller.controller.school;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.controller.school.PostShenHeServlet;

/**
 * @desc   控制学校帖子是否需要审核
 * @author JZH
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/controller/school/postshenhe")
public class PostShenHeController extends HttpServlet{
	
	PostShenHeServlet postShenHe = new PostShenHeServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		postShenHe.postShenHe(request,response);
	}
}
