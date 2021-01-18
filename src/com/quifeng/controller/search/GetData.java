package com.quifeng.controller.search;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @desc   获取搜索结果
 * @author JZH
 * @time   2021-01-05
 */

import com.quifeng.servlet.search.GetDataServlet;

@SuppressWarnings("serial")
@WebServlet("/api/search/getdata")
public class GetData extends HttpServlet {

	GetDataServlet dataServlet = new GetDataServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		dataServlet.querySearch(request, response);
	}
}
