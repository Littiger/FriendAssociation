package com.quifeng.controller.chat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.chat.Send2usermsgServlet;
import com.quifeng.utils.error.ErrorUtils;

@SuppressWarnings("serial")
@WebServlet("/api/chat/send2usermsg")
public class Send2usermsgController extends HttpServlet{

	Send2usermsgServlet  send2usermsgServlet = new Send2usermsgServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			send2usermsgServlet.send2Usermsg(request, response);
		} catch (Exception e) {
//			out.print(ErrorUtils.errorTomCat());
			e.printStackTrace();
		}finally {
			out.close();
		}
	}
}
