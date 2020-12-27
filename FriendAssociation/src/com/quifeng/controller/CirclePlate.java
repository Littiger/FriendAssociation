package com.quifeng.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @desc   获取单个板块的帖子---帖子模块的信息------ 增加是否点赞  是否收藏
 * @author JZH
 * @time   2020-12-27
 */

import com.quifeng.servlet.circle.plate.CirclePlateServlet;
@WebServlet("/api/circle/plate")
public class CirclePlate extends HttpServlet{
	
	CirclePlateServlet plateServlet = new CirclePlateServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		plateServlet.queryOnlyTypePost(request,response);
	}
}
