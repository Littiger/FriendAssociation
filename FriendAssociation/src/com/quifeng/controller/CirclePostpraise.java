package com.quifeng.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @desc   帖子点赞
 * @author JZH
 * @time   2020-12-27
 */

import com.quifeng.servlet.circle.postpraise.CirclePostpraiseServlet;
@SuppressWarnings("serial")
@WebServlet("/api/circle/postpraise")
public class CirclePostpraise extends HttpServlet{
	
	CirclePostpraiseServlet postpariseServlet = new CirclePostpraiseServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		postpariseServlet.postAddZan(request,response);
	}
}
