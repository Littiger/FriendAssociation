package com.quifeng.controller.circle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.circle.hot.CircleHotServlet;

/**
 * @desc 帖子推荐获取------是否点赞 ，是否收藏，帖子模块信息名
 * @author JZH
 * @time 2020-12-27
 */
@SuppressWarnings("serial")
@WebServlet("/api/circle/hot")
public class CircleHot extends HttpServlet {

	CircleHotServlet circleHotServlet = new CircleHotServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		circleHotServlet.queryHot(request, response);

	}

}
