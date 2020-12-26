package com.turing.manage.subject;

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
public class SubjectServlet extends HttpServlet {

	ISubjectService subjectService = new SubjectServiceImpl();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("SubjectServlet--->service()");
		
		String method = request.getParameter("method");
		
		try {
			if ("query".equals(method)) {
				query(request,response);
			}else if ("edit".equals(method)) {
				edit(request,response);
			}else if ("editpage".equals(method)) {
				editpage(request,response);
			}else if ("delete".equals(method)) {
				delete(request,response);
			}else if ("addpage".equals(method)) {
				addpage(request,response);
			}else if ("add".equals(method)) {
				add(request,response);
			}else if ("conditionquery".equals(method)) {
				conditionquery(request,response);
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
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void conditionquery(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		System.out.println("SubjectServlet--->conditionquery");
		String condition = request.getParameter("condition");
		System.out.println(condition);
		String key = request.getParameter("key");
		List<Map<String,Object>> list= subjectService.queryCondition(condition,key);
		System.out.println(list);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/manage/subject/list.jsp").forward(request, response);
	}

	/**
	 * @desc  修改页面
	 * @param request
	 * @param response
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void editpage(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		System.out.println("SubjectServlet--->editpage()");
		String subject_id = request.getParameter("id");
		Map<String,Object> select_map = subjectService.query(subject_id);
		List<Map<String,Object>> list = subjectService.queryCourse();
		request.setAttribute("select_map", select_map);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/manage/subject/edit.jsp").forward(request, response);
	}

	/**
	 * @desc  添加
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		System.out.println("SubjectServlet--->add()");
		String course_id = request.getParameter("course_id");
		String subject_name = request.getParameter("subject_name");
		String subject_type = request.getParameter("subject_type");
		String subject_A = request.getParameter("subject_A");
		String subject_B = request.getParameter("subject_B");
		String subject_C = request.getParameter("subject_C");
		String subject_D = request.getParameter("subject_D");
		String[] answerArr = request.getParameterValues("answer");
		for (String str : answerArr) {
			System.out.println(str);
		}
		String subject_remark = request.getParameter("subject_remark");
		subjectService.add(course_id,subject_name,subject_type,subject_A,subject_B,subject_C,subject_D,answerArr,subject_remark);
		response.sendRedirect(request.getContextPath()+"/manage/subject.do?method=query");
	}
	
	/**
	 * @desc  添加页面
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void addpage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		System.out.println("SubjectServlet--->addpage()");
		List<Map<String,Object>> list = subjectService.queryCourse();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/manage/subject/add.jsp").forward(request, response);
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
		System.out.println("SubjectServlet--->delete()");
		String[] delIdArray = request.getParameterValues("delIdArray");
		subjectService.delete(delIdArray);
		response.sendRedirect(request.getContextPath()+"/manage/subject.do?method=query");
	}

	/**
	 * @desc  修改
	 * @param request
	 * @param response
	 * @throws SLException 
	 * @throws ClQassNotFoundException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private void edit(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		System.out.println("SubjectServlet--->edit()");
		String subject_id = request.getParameter("subject_id");
		System.out.println(subject_id);
		String lessonId = request.getParameter("lessonId");
		String subject_name = request.getParameter("subject_name");
		String subject_type = request.getParameter("subject_type");
		String subject_A = request.getParameter("subject_A");
		String subject_B = request.getParameter("subject_B");
		String subject_C = request.getParameter("subject_C");
		String subject_D = request.getParameter("subject_D");
		String[] answerArr = request.getParameterValues("answerArr");
		String subject_remark = request.getParameter("subject_remark");
		subjectService.edit(lessonId,subject_name,subject_type,subject_A,subject_B,subject_C,subject_D,answerArr,subject_remark,subject_id);
		response.sendRedirect(request.getContextPath()+"/manage/subject.do?method=query");
		
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
	private void query(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		System.out.println("SubjectServlet--->query()");
		List<Map<String,Object>> list = subjectService.queryAll();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/manage/subject/list.jsp").forward(request, response);
	}
	
}
