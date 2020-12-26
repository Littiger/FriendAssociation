package com.turing.manage.login;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

public class LoginServlet extends HttpServlet {
	
	ILoginService loginService = new LoginServiceImpl();
	
	HttpSession session;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("LoginServlet--->service()");
		
		String method = request.getParameter("method");
		try {
			if ("login".equals(method)) {
				login(request,response);
			}else if ("logout".equals(method))
			{
				logout(request,response);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("类无法找到异常");
		} catch (SQLException e) {
			System.out.println("SQL异常");
		}
		
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("LoginServlet--->logout()");
		session.invalidate();
		request.getRequestDispatcher("/manage/login.jsp").forward(request, response);
	}

	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		System.out.println("LoginServlet--->login()");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		System.out.println("name:" + name + "\n" + "password:" + pass);
		Map<String,Object> map = loginService.login(name,pass);
		
		if (map == null) {
			System.out.println("登陆失败");
			String errorMsg = "用户名、密码错误，请重新登陆";
			request.setAttribute("error", errorMsg);
			request.getRequestDispatcher("/manage/error.jsp").forward(request, response);
		}else {
			System.out.println("登陆成功");
			session = request.getSession();
			session.setAttribute("user", map);
			String sessionId = session.getId();
			response.sendRedirect(request.getContextPath()+"/manage/manager.do?method=query");
		}
		
	}
	
}
