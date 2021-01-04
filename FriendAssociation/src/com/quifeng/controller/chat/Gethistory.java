package com.quifeng.controller.chat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.chat.GethistoryServlet;

/**
 * @desc   查询历史聊天记录
 * @author JZH
 * @time   2021-01-02
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/gethistory")
public class Gethistory extends HttpServlet{
	
	GethistoryServlet historyServlet = new GethistoryServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		historyServlet.queryHistory(request,response);
	}
}
