package com.quifeng.controller.issue;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.issue.dynamicServlet;

/**
 * @desc   发布帖子
 * @author JZH
 * @time   2021-01-12
 */
@SuppressWarnings("serial")
@WebServlet("/api/issue/dynamic")
public class dynamic extends HttpServlet{
	
	dynamicServlet dynamicServlet = new dynamicServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
