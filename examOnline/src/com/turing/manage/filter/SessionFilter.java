package com.turing.manage.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("SessionFilter-->初始化");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("SessionFilter开始工作...");
		
		//1.获取request对象和response对象
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		
		//2.针对特权页面放行
		String path=request.getServletPath();
		System.out.println("path--->"+path);
		if (("/manage/login.jsp".equals(path))||("/manage/login.do".equals(path))) 
		{
		 System.out.println("当前是特权页面，请放行");
		 chain.doFilter(request, response);
		 return ;
		}
		//3.过滤session
		HttpSession session = request.getSession();
		if (session.getAttribute("user")==null)
		{
		  System.out.println("您还没有登录");
		  String errorMsg="您还没有登录，请输入您的账号和密码";
		  request.setAttribute("error", errorMsg);
		  request.getRequestDispatcher("/manage/error.jsp").forward(request, response);
		  return ;
		}
		else
		{
			System.out.println("过滤通过了");
			chain.doFilter(request, response);
             return ;			
		}
		
	}

	@Override
	public void destroy() {
		System.out.println("SessionFilter销毁...");
	}

}
