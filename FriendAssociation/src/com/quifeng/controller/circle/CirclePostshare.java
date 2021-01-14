package com.quifeng.controller.circle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.circle.postshare.CirclePostshareServlet;

/**
 * @desc 分享帖子
 * @author JZH
 * @time 2020-12-27
 */
@SuppressWarnings("serial")
@WebServlet("/api/circle/postshare")
public class CirclePostshare extends HttpServlet {

	CirclePostshareServlet postShareServlet = new CirclePostshareServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		postShareServlet.SharePost(request, response);
	}

}
