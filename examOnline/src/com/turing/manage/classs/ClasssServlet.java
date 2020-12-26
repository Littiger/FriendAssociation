package com.turing.manage.classs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClasssServlet extends HttpServlet {
	
	IClasssService classService = new ClassServiceImpl();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("ClasssServlet--->service()");
		
		String method = request.getParameter("method");
		
		try {
			if ("query".equals(method)) {
				query(request,response);
			}else if ("add".equals(method)) {
				add(request,response);
			}else if ("editpage".equals(method)) {
				editPage(request,response);
			}else if ("edit".equals(method)) {
				edit(request,response);
			}else if ("delete".equals(method)) {
				delete(request,response);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @desc  删除班级<多条>
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		System.out.println("ClasssServlet--->delete()");
		String[] idArray = request.getParameterValues("delIdArray");
		for (String str : idArray) {
			System.out.println(str);
		}
		classService.delete(idArray);
		response.sendRedirect(request.getContextPath()+"/manage/classs.do?method=query");
	}

	/**
	 * @desc  修改班级名称
	 * @param request
	 * @param response
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	private void edit(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("ClasssServlet--->edit()");
		String classs_id = request.getParameter("classs_id");
		System.out.println(classs_id);
		String classs_name = request.getParameter("classs_name");
		classService.edit(classs_id,classs_name);
		response.sendRedirect(request.getContextPath()+"/manage/classs.do?method=query");
	}

	/**
	 * @desc  修改页面跳转
	 * @param request
	 * @param response
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void editPage(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		System.out.println("ClasssServlet--->editPage()");
		String classs_id = request.getParameter("classs_id");
		Map<String , Object>map = classService.queryOne(classs_id);
		request.setAttribute("map", map);
		request.getRequestDispatcher("/manage/classs/edit.jsp").forward(request, response);
		
		
	}

	/**
	 * @desc  添加班级
	 * @param request
	 * @param response
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	private void add(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("ClasssServlet--->add()");
		String name = request.getParameter("classs_name");
		classService.add(name);
		response.sendRedirect(request.getContextPath()+"/manage/classs.do?method=query");
	}

	/**
	 * @desc  查询班级
	 * @param request
	 * @param response
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void query(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		System.out.println("ClasssServlet--->query()");
		List<Map<String,Object>> list= classService.queryAll();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/manage/classs/list.jsp").forward(request, response);
	}
	
}
