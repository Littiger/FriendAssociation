package com.quifeng.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.login.RegisteredServlet;
import com.quifeng.utils.error.ErrorUtils;

/**
 * @Desc 验证码验证
 * @author 语录
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/signverify")
public class SignverifyController extends HttpServlet{

	RegisteredServlet recompile = new RegisteredServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			recompile.signverify(request, response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			out.print(ErrorUtils.errorTomCat());
		}finally {
			out.close();
		}
	}
}
