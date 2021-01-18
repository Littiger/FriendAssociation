package com.quifeng.controller.chat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.chat.SendUsermsgServlet;

/**
 * @desc 发送消息 & 发送图片
 * @author JZH
 * @time 2021-01-02
 */
@SuppressWarnings("serial")
@WebServlet("/api/chat/send2usermsg")
public class Sendusermsg extends HttpServlet {

	SendUsermsgServlet sendusermsgServlet = new SendUsermsgServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sendusermsgServlet.sendMessage(request, response);
	}
}
