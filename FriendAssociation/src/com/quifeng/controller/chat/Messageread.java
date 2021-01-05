package com.quifeng.controller.chat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.servlet.chat.MessageReadServlet;
/**
 * @desc   聊天-消息置为已读
 * @author JZH
 * @time   2021-01-02
 */
@SuppressWarnings("serial")
@WebServlet("/api/chat/messageread")
public class Messageread extends HttpServlet{
	
	MessageReadServlet readServlet = new MessageReadServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		readServlet.isRead(request,response);
	}
}
