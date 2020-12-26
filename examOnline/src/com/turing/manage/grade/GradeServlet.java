package com.turing.manage.grade;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GradeServlet extends HttpServlet {
	
	IGradeService gradeService = new GradeServiceImpl();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ExamineeServlet--->service()");

		String method = request.getParameter("method");
		
		try {
			if ("query".equals(method)) {
				queryAll(request,response);
			}else if ("conditionquery".equals(method)) {
				queryCondition(request,response);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @desc  条件查询
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ServletException 
	 */
	private void queryCondition(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException, ServletException {
		System.out.println("GradeServlet--->queryCondition()");
		String key = request.getParameter("key");
		String condition = request.getParameter("condition");
		List<Map<String,Object>> list = gradeService.queryCondition(condition,key);
		request.setAttribute("condition_out", condition);
		request.setAttribute("key_out", key);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/manage/grade/list.jsp").forward(request, response);
	}

	/**
	 * @desc  查询全部
	 * @param request
	 * @param response
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void queryAll(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		System.out.println("GradeServlet--->queryAll()");
		List<Map<String,Object>> list= gradeService.queryAll();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/manage/grade/list.jsp").forward(request, response);
		
	}
	
}
