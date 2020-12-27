package com.quifeng.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Desc 1.设置编码 2.设置跨域  
 */
public class CharFilter implements Filter {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
			//转换
			HttpServletRequest request = (HttpServletRequest) arg0;
			HttpServletResponse response = (HttpServletResponse) arg1;
		
			//指定允许其他域名访问 
			response.setHeader("Access-Control-Allow-Origin", "*");
			//跨域 Header 
			response.setHeader("Access-Control-Allow-Headers", "*");
		    //允许请求的类型   post get  这些
		    response.setHeader("Access-Control-Allow-Methods", "*");
		    //设置返回的文件类型
		    response.setContentType("application/json;charset=UTF-8");
			//设置编码集
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");	
			//放行
			arg2.doFilter(request, response);
		
	}

}
