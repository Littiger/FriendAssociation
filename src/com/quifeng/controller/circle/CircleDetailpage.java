package com.quifeng.controller.circle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.circle.detailpage.CircleDetailpageServlet;
/**
 * @desc   获取一级评论+帖子的详细信息 -- 修改增加了 osfirstid 评论id   是否点赞   是否收藏
 * @author JZH
 * @time 2020-12-27
 */
@SuppressWarnings("serial")
@WebServlet("/api/circle/detailpage")
public class CircleDetailpage extends HttpServlet{
	CircleDetailpageServlet detailpageServlet = new CircleDetailpageServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		detailpageServlet.queryPostOs(request,response);
	}
}
