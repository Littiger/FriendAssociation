package com.quifeng.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.utils.error.ErrorUtils;
import com.quifeng.servlet.user.GetMyFansServlet;

/**
 * APi : 获取所有粉丝 URL : /api/info/bean
 * 
 * @param : token
 * 
 * @author 梦伴
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/info/bean")
public class GetMyFansController extends HttpServlet {

	GetMyFansServlet GetMyFansServlet = new GetMyFansServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			GetMyFansServlet.getFans(request, response);
		} catch (Exception e) {
			out.print(ErrorUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
