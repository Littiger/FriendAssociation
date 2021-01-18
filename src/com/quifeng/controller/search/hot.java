package com.quifeng.controller.search;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @desc   获取热词
 * @author JZH
 * @time   2021-01-05
 */

import com.quifeng.servlet.search.HotServlet;

@SuppressWarnings("serial")
@WebServlet("/api/search/hot")
public class hot extends HttpServlet {

	HotServlet HotServlet = new HotServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HotServlet.getHotWord(request, response);
	}
}
