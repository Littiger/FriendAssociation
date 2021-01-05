package com.quifeng.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.chat.GethistoryServlet;
import com.quifeng.servlet.user.UserHomeServlet;
import com.quifeng.utils.error.ErrorUtils;

/**
 * @Desc  http：//127.0.0.1/api/user/userhome
 * @author 语录
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/userhome")
public class UserhomeController extends HttpServlet {

	UserHomeServlet userHomeServlet = new UserHomeServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			userHomeServlet.getUserHome(request, response);
		} catch (Exception e) {
			out.print(ErrorUtils.errorTomCat());
		}finally {
			out.close();
		}
	}
	
}
