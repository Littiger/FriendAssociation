package com.quifeng.controller.circle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.circle.ospraise.CircleOspraiseServlet;

/**
 * @desc   评论点赞
 * @author JZH
 * @time   2020-12-27
 */
@SuppressWarnings("serial")
@WebServlet("/api/circle/ospraise")
public class CircleOspraise extends HttpServlet{
	
	CircleOspraiseServlet osPraiseServlet = new CircleOspraiseServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		osPraiseServlet.AddOsZan(request,response);
	}
}
