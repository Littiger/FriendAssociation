package com.quifeng.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.utils.error.ErrorUtils;
import com.quifeng.servlet.user.GetMyUserinfoServlet;

/**
 * APi : 获取个人信息 URL : /api/info/getmyuserinfo
 * 
 * @param : token
 * 
 * @author 梦伴
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/getmyuserinfo")
public class GetMyUserinfoController extends HttpServlet {

	GetMyUserinfoServlet GetMyUserinfoServlet = new GetMyUserinfoServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			GetMyUserinfoServlet.getinfo(request, response);
		} catch (Exception e) {
			out.print(ErrorUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
