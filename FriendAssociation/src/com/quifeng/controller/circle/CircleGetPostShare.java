package com.quifeng.controller.circle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.circle.postshare.GetPostShareServlet;

/**
 * @desc   查询分享 
 * @author JZH
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/api/circle/getpostshare")
public class CircleGetPostShare extends HttpServlet{
	
	GetPostShareServlet getPostShare = new GetPostShareServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getPostShare.getPostShare(request,response);
	}
}
