package com.quifeng.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.user.FeedBackServlet;

/**
 * @desc   发送反馈意见
 * @author JZH
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/feedback")
public class FeedBackController extends HttpServlet{
	
	FeedBackServlet feedBack = new FeedBackServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		feedBack.feedBack(request,response);
	}
	
}
