package com.quifeng.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.login.FaceServlet;
import com.quifeng.servlet.login.loginServlet;
import com.quifeng.utils.error.ErrorUtils;

/**
 * @Desc 1. http：//127.0.0.1/api/user/getuserinfo
 * @author 语录
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/getuserinfo")
public class GetUserinfoController extends HttpServlet {

	loginServlet lohin = new loginServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			lohin.getUserinfo(request, response);
		} catch (Exception e) {
			out.print(ErrorUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
