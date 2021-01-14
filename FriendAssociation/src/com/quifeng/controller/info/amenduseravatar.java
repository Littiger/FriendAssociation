package com.quifeng.controller.info;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quifeng.servlet.info.amenduseravatarServlet;

/**
 * @desc 修改头像
 * @author JZH
 * @time 2021-01-12
 */
@SuppressWarnings("serial")
@WebServlet("/api/info/amenduseravatar")
public class amenduseravatar extends HttpServlet {
	amenduseravatarServlet amenduseravatarServlet = new amenduseravatarServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		amenduseravatarServlet.updateHeadImg(request, response);
	}

}
