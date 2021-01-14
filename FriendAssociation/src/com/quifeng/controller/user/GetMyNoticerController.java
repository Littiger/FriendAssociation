package com.quifeng.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.utils.error.ErrorUtils;
import com.quifeng.servlet.user.GetMyNoticerServlet;

/**
 * APi : 获取所有关注的人 URL : /api/info/noticer
 * 
 * @param : token
 * 
 * @author 梦伴
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/info/noticer")
public class GetMyNoticerController extends HttpServlet {

	GetMyNoticerServlet GetMyNoticerServlet = new GetMyNoticerServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			GetMyNoticerServlet.getMyNoticer(request, response);
		} catch (Exception e) {
			out.print(ErrorUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
