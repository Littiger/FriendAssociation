package com.quifeng.controller.user;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.login.loginServlet;
import com.quifeng.utils.error.ErrorUtils;

/**
 * @desc 加载学校
 * @author 语录
 * @time 2021-01-02
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/getschool")
public class GetschoolController extends HttpServlet {

	loginServlet loginServlet = new loginServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			loginServlet.getSchool(request, response);
		} catch (Exception e) {
			out.print(ErrorUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
