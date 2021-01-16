package com.quifeng.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.user.MarkUserServlet;
import com.quifeng.utils.error.ErrorUtils;

/**
 * APi : 关注用户 URL : /api/user/focus
 * 
 * @param : token
 * @param : userid
 * 
 * @author 梦伴
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/focus")
public class MarkUserController extends HttpServlet {

	MarkUserServlet MarkUserServlet = new MarkUserServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			MarkUserServlet.markUser(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(ErrorUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}