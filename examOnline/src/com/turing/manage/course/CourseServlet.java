package com.turing.manage.course;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CourseServlet extends HttpServlet {
	
	ICourseService courseService = new CourseServiceImpl();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CourseServlet--->service()");
		
		String method = request.getParameter("method");
		
		try {
			if ("query".equals(method)) {
				query(request,response);
			}else if ("addpage".equals(method)) {
				addpage(request,response);
			}else if ("add".equals(method)) {
				add(request,response);
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
	 * @desc  删除
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		System.out.println("CourseServlet--->delete()");
		String[] delIdArray = request.getParameterValues("delIdArray");
		courseService.delete(delIdArray);
		response.sendRedirect(request.getContextPath()+"/manage/course.do?method=query");
	}

	/**
	 * @desc  添加操作
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	private void add(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		System.out.println("CourseServlet--->add()");
		String course_name = request.getParameter("course_name");
		courseService.add(course_name);
		response.sendRedirect(request.getContextPath()+"/manage/course.do?method=query");
	}

	/**
	 * @desc  跳转添加页面
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void addpage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CourseServlet--->addPage()");
		request.getRequestDispatcher("/manage/course/add.jsp").forward(request, response);
		
	}

	/**
	 * @Desc  查询全部课程信息
	 * @param request
	 * @param response
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void query(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		System.out.println("CourseServlet--->query()");
		List<Map<String,Object>> list= courseService.queryAll();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/manage/course/list.jsp").forward(request, response);
		
	}
	
	
}
