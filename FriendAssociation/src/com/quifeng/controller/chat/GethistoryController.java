package com.quifeng.controller.chat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.chat.GethistoryServlet;

import com.quifeng.utils.error.ErrorUtils;

@SuppressWarnings("serial")
@WebServlet("/api/chat/gethistory")
public class GethistoryController extends HttpServlet {

	GethistoryServlet gethistory = new GethistoryServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			gethistory.gethistory(request, response);
		} catch (Exception e) {
			out.print(ErrorUtils.errorTomCat());
		}finally {
			out.close();
		}
	}
}
