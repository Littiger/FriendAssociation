package com.quifeng.controller.issue;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.issue.circleServlet;

/**
 * @desc   获取圈子信息
 * @author JZH
 * @time   2021-01-12
 */
@SuppressWarnings("serial")
@WebServlet("/api/issue/circle")
public class circle extends HttpServlet{
	
	circleServlet circleServlet = new circleServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		circleServlet.queryCircleMessage(request,response);
	}
}
