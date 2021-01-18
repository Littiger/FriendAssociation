package com.quifeng.controller.circle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.circle.topcomment.CircleTopcommentServlet;

/**
 * @desc 获取评论--增加表项 ----osfirstid --- 增加评论id 增加 是否点赞
 * @author JZH
 * @time 2020-12-27
 */
@SuppressWarnings("serial")
@WebServlet("/api/circle/topcomment")
public class CircleTopcomment extends HttpServlet {

	CircleTopcommentServlet topcommentServlet = new CircleTopcommentServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		topcommentServlet.queryOs(request, response);
	}
}
