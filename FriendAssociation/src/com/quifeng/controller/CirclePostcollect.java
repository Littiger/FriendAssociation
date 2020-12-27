package com.quifeng.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.circle.postcollect.PostcollectServlet;

@SuppressWarnings("serial")
@WebServlet("/api/circle/postcollect")
public class CirclePostcollect extends HttpServlet{
	
	PostcollectServlet postcollectServlet = new PostcollectServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		postcollectServlet.addAos(request, response);
	}
}
