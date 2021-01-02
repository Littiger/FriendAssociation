package com.quifeng.controller.circle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.quifeng.servlet.circle.commentdetail.CircleCommentdetailServlet;
/**
 * @desc   获取一级评论的详情
 * @author JZH
 * @time   2020-12-27
 */
@SuppressWarnings("serial")
@WebServlet("/api/circle/commentdetail")
public class CircleCommentdetail extends HttpServlet{
	
	CircleCommentdetailServlet commentdetailServlet = new CircleCommentdetailServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		commentdetailServlet.queryFiOsAndOtherOs(request,response);
	}
}
