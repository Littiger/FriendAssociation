package com.quifeng.controller.circle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.circle.pagecomment.CirclePagecommentServlet;

/**
 * @desc 获取一级评论的评论分页
 * @author JZH
 * @time 2020-12-28
 */
@SuppressWarnings("serial")
@WebServlet("/api/circle/pagecomment")
public class CirclePagecomment extends HttpServlet {
	CirclePagecommentServlet pagecommentServlet = new CirclePagecommentServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		pagecommentServlet.queryPageComment(request, response);
	}
}
