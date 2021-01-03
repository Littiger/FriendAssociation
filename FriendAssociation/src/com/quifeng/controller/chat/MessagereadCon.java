package com.quifeng.controller.chat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.chat.Messageread;
import com.quifeng.utils.error.ErrorUtils;

@SuppressWarnings("serial")
@WebServlet("/api/chat/messageread")
public class MessagereadCon  extends HttpServlet{
	Messageread  messageread = new Messageread();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			messageread.messageread(request, response);
			
		} catch (Exception e) {
			out.print(ErrorUtils.errorTomCat());
		}finally {
			out.close();
		}
	}
}
