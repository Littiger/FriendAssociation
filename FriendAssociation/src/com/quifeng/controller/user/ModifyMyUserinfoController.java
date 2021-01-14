package com.quifeng.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.user.ModifyMyUserinfoServlet;
import com.quifeng.utils.error.ErrorUtils;

/**
 * APi : 修改个人信息 URL : /api/info/modify
 * 
 * @param : token
 * @param : uname
 * @param : usigntext
 * @param : usex
 * @param : umajor
 * @param : ubir
 * @param : ugraduate
 * 
 * @author 梦伴
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/info/modify")
public class ModifyMyUserinfoController extends HttpServlet {

	ModifyMyUserinfoServlet modifyMyUserinfoServlet = new ModifyMyUserinfoServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			modifyMyUserinfoServlet.modify(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(ErrorUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
