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

@SuppressWarnings("serial")
@WebServlet("/api/user/setschool")
public class SetschoolController extends HttpServlet{

		loginServlet loginServlet = new loginServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			loginServlet.setSchool(request, response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			out.print(ErrorUtils.errorTomCat());
		}finally {
			out.close();
		}
	}
	
}
