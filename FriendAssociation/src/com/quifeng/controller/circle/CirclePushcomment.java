package com.quifeng.controller.circle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.circle.pushcomment.CirclePushcommentServlet;
/**
 * @desc   发布评论
 * @author JZH
 * @time   2020-12-27
 */
@SuppressWarnings("serial")
@WebServlet("/api/circle/pushcomment")
public class CirclePushcomment extends HttpServlet{
	
	CirclePushcommentServlet pushcommentServlet = new CirclePushcommentServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pushcommentServlet.pushComment(request,response);
	}
}
