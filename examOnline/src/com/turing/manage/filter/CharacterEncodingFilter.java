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

public class CharacterEncodingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
           System.out.println("CharacterEncodingFilter--->初始化");		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
            System.out.println("CharacterEncodingFilter--->开始工作");
            //1.获取request和response对象
            HttpServletRequest request=(HttpServletRequest) arg0;
            HttpServletResponse response=(HttpServletResponse) arg1;
            
            //2.设定编码
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            
            //3.放行
            chain.doFilter(request, response);
            
	}
        
	@Override
	public void destroy() {
           System.out.println("CharacterEncodingFilter--->销毁");		
	}

}
