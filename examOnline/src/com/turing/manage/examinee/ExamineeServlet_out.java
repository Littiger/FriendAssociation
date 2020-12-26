package com.turing.manage.examinee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExamineeServlet_out extends HttpServlet {
	
	IExamineeService_out examineeService = new ExamineeServletImpl_out();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ExamineeServlet--->service()");
		
		String method = request.getParameter("method");
		
		try {
			if ("query".equals(method)) {
				queryAll(request,response);
			}else if ("delete".equals(method)) {
				delete(request,response);
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
		System.out.println("ExamineeServlet--->queryCondition()");
		String classs_name = request.getParameter("classs_name");
		String examinee_name = request.getParameter("examinee_name");
		List<Map<String,Object>> list = null;
		if (examinee_name.equals("") && classs_name.equals("%")) {
			response.sendRedirect(request.getContextPath()+"/manage/examinee.do?method=query");
		}else if ((!examinee_name.equals("")) && classs_name.equals("%")) {
			list= examineeService.queryExamineeName(examinee_name);
			request.setAttribute("list", list);
		}else if (examinee_name.equals("") &&(!classs_name.equals("%"))) {
			list= examineeService.queryClasssName(classs_name);
			request.setAttribute("list", list);
		}else if((!examinee_name.equals("")) && (!classs_name.equals("%"))){
			list= examineeService.queryCEName(classs_name,examinee_name);
			request.setAttribute("list", list);
		}
		request.setAttribute("list", list);
		System.out.println(list);
		request.getRequestDispatcher("/manage/examinee/list.jsp").forward(request, response);
		
		
	}

	/**
	 * @desc  删除
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		System.out.println("ExamineeServlet--->delete()");
		String[] idArray = request.getParameterValues("delIdArray");
		examineeService.delete(idArray);
		response.sendRedirect(request.getContextPath()+"/manage/examinee.do?method=query");
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
		System.out.println("ExamineeServlet--->queryAll()");
		List<Map<String,Object>> list = examineeService.queryAll();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/manage/examinee/list.jsp").forward(request, response);
		
	}
	
	
	
}
