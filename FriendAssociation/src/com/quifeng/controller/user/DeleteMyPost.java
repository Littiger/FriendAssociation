package com.quifeng.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.user.DeleteMyPostServlet;
import com.quifeng.utils.error.ErrorUtils;

/**
 * APi : 删除我的帖子 URL : /api/info/getmepost
 * 
 * @param : token
 * @param : postid
 * 
 * @author 梦伴
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/info/deletemepost")
public class DeleteMyPost extends HttpServlet {

	DeleteMyPostServlet DeleteMyPostServlet = new DeleteMyPostServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			DeleteMyPostServlet.deleteMyPost(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(ErrorUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
