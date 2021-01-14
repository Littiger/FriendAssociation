package com.quifeng.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.user.UserHomeServlet;
import com.quifeng.servlet.user.UserdynamiclistServlet;
import com.quifeng.utils.error.ErrorUtils;

/**
 * @Desc
 * @author 语录
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/userdynamiclist")
public class UserdynamiclistController extends HttpServlet {

	UserdynamiclistServlet userdynamiclistServlet = new UserdynamiclistServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			userdynamiclistServlet.userdynamiclist(request, response);
		} catch (Exception e) {
			out.print(ErrorUtils.errorTomCat());
			System.out.println(e.getMessage());
		} finally {
			out.close();
		}
	}

}
