package com.quifeng.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.user.GetMyPostServlet;
import com.quifeng.utils.error.ErrorUtils;

/**
 * APi : 获取我的发帖 URL : /api/info/getmepost
 * 
 * @param : token
 * @param : page
 * @param : size
 * 
 * @author 梦伴
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/info/getmepost")
public class GetMyPostController extends HttpServlet {

	GetMyPostServlet GetMyPostServlet = new GetMyPostServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			GetMyPostServlet.getMyPost(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(ErrorUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
