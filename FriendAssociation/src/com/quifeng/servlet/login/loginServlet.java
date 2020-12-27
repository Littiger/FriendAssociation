package com.quifeng.servlet.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.dao.login.LoginDao;

/**
 * @Desc 登录使用
 * @author 语录
 *
 */
public class loginServlet {

	
	
	LoginDao login = new LoginDao();
	
	/**
	 * @Desc 登录
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void  login(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
	}
}
