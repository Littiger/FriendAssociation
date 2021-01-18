package com.quifeng.controller.chat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.chat.GetMessListServlet;

/**
 * @desc 获取消息列表
 * @author JZH
 * @time 2021-01-02
 */
@SuppressWarnings("serial")
@WebServlet("/api/chat/getmesslist")
public class GetMessList extends HttpServlet {

	GetMessListServlet getmesslistServlet = new GetMessListServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getmesslistServlet.getMessList(request, response);
	}
}
