package com.quifeng.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.login.RegisteredServlet;
import com.quifeng.utils.error.ErrorUtils;
import com.sun.org.apache.regexp.internal.recompile;

/**
 * @Desc 注册 api     url： http://127.0.0.1/api/user/sign 
 *  
 * @author 语录
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/sign")
public class SignController extends HttpServlet{

	RegisteredServlet recompile = new RegisteredServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			recompile.registered(request, response);
		} catch (Exception e) {
			out.print(ErrorUtils.errorTomCat());
		}finally {
			out.close();
		}
	}
}
