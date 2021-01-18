package com.quifeng.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.utils.error.ErrorUtils;
import com.quifeng.servlet.user.GetMyUserHistoryServlet;

/**
 * APi : 获取浏览历史记录 URL : /api/info/history
 * 
 * @param : token
 * @param : page
 * @param : size
 * 
 * @author 梦伴
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/info/history")
public class GetMyUserHistoryController extends HttpServlet {

	GetMyUserHistoryServlet GetMyUserHistoryServlet = new GetMyUserHistoryServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			GetMyUserHistoryServlet.getMyUserHistory(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(ErrorUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
