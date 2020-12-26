package com.turing.manage.testpaper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.turing.manage.manager.IManagerService;
import com.turing.manage.manager.ManagerServiceImpl;

/**
 * 
 * @author Littiger
 *
 */
@SuppressWarnings("serial")
public class TestpaperServlet extends HttpServlet {

	ITestpaperService testpaperService=new TestpaperServiceImpl();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("TestpaperServlet--->service()");
		String method=request.getParameter("method");
		
		try 
		{
			if ("query".equals(method)) 
			{
				this.query(request,response);
			}
			else if ("addPage".equals(method)) 
			{
				this.addPage(request,response);
			}
			else if ("add".equals(method)) 
			{
				this.add(request,response);
			}
			else if ("createTest".equals(method)) 
			{
				this.createTest(request,response);
			}
			else if ("previewTestpaper".equals(method)) 
			{
				this.previewTestpaper(request,response);
			}
			else if ("canTest".equals(method)) 
			{
				this.canTest(request,response);
			}
			else if ("finishTest".equals(method)) 
			{
				this.finishTest(request,response);
			}
			else if ("delete".equals(method)) 
			{
				this.delete(request,response);
			}
			else if ("editPage".equals(method)) 
			{
				this.editPage(request,response);
			}
			else if ("edit".equals(method)) 
			{
				this.edit(request,response);
			}
		} 
		catch (ClassNotFoundException e) 
		{
		  System.out.println("类没有找到异常....");
		} 
		catch (SQLException e) 
		{
			System.out.println("SQL异常....");
		} 
		
		
	}

/**
 * @desc  修改
 * @param request
 * @param response
 * @throws IOException 
 * @throws SQLException 
 * @throws FileNotFoundException 
 * @throws ClassNotFoundException 
 */
private void edit(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
	System.out.println("TestpaperServlet--->edit()");
	//1.接值：
	String testpaper_id = request.getParameter("testpaper_id");
	String testpaper_name=request.getParameter("testpaper_name");
	String testpaper_radio_num=request.getParameter("testpaper_radio_num");
	String testpaper_check_num=request.getParameter("testpaper_check_num");
	String testpaper_time_use=request.getParameter("testpaper_time_use");
	String classs_id=request.getParameter("classs_id");
	String course_id=request.getParameter("course_id");
	
	//2.更新
	testpaperService.edit(testpaper_id,testpaper_name,testpaper_radio_num,testpaper_check_num,testpaper_time_use,course_id,classs_id);
	response.sendRedirect(request.getContextPath() + "/manage/testpaper.do?method=query");
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
private void editPage(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		System.out.println("TestpaperServlet--->editPage()");
		//借值
		String testpaper_id = request.getParameter("id");
		
		//查询
		Map<String,Object> map= testpaperService.queryOne(testpaper_id);
		List<Map<String, Object>> list_classs=testpaperService.queryClasss();
		List<Map<String, Object>> list_course=testpaperService.queryCourse();
		System.out.println(list_course);
		//设置属性
		request.setAttribute("map", map);
		request.setAttribute("list_classs",list_classs );
		request.setAttribute("list_course",list_course );
		
		request.getRequestDispatcher("/manage/testpaper/edit.jsp").forward(request, response);;
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
		System.out.println("TestpaperServlet--->delete()");
		String[] delIdArray = request.getParameterValues("delIdArray"); 
		testpaperService.delete(delIdArray);
		response.sendRedirect(request.getContextPath()+"/manage/testpaper.do?method=query");
	}


/**
 * @desc  7.结束考试
 * @param request
 * @param response
 * @throws IOException 
 * @throws SQLException 
 * @throws FileNotFoundException 
 * @throws ClassNotFoundException 
 */
private void finishTest(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
	    //1.接值
		String id=request.getParameter("id");
		//2.调用M层中的方法实现修改试卷状态位
		testpaperService.updateByFinishTest(id);
		//3.重定向
		response.sendRedirect(request.getContextPath()+"/manage/testpaper.do?method=query");
	}


/**
 * @desc  6.可以考试
 * @param request
 * @param response
 * @throws IOException 
 * @throws SQLException 
 * @throws FileNotFoundException 
 * @throws ClassNotFoundException 
 */
private void canTest(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
	
//1.接值
String id=request.getParameter("id");

//2.调用M层的方法实现修改试卷的状态位
testpaperService.updateByCanTest(id);
	
//3.重定向：其目的是刷新页面
response.sendRedirect(request.getContextPath()+"/manage/testpaper.do?method=query");
	}


/**
 * @desc  5.查看试卷
 * @param request
 * @param response
 * @throws SQLException 
 * @throws ClassNotFoundException 
 * @throws IOException 
 * @throws ServletException 
 */
private void previewTestpaper(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {

	//1.接值：接受试卷id
		String id=request.getParameter("id");
	
	//2.取值：查看该试卷id对应的试卷的详情
	Map<String, Object>  testpaperMap=testpaperService.queryTestPaperById(id);
	
	Integer testpaper_radio_num = (Integer) testpaperMap.get("testpaper_radio_num");//获取单选题的数量
	Integer testpaper_check_num = (Integer) testpaperMap.get("testpaper_check_num");//获取多选题的数量
	
    List<Map<String, Object>> list_testpaper_radio_info=testpaperService.queryTestPaperRadioInfo(id);//查询获取单选题的信息
	
	List<Map<String, Object>> list_testpaper_check_info=testpaperService.queryTestPaperCheckInfo(id);//查询获取多选题的信息
	//3.存值
		request.setAttribute("testpaper_radio_num", testpaper_radio_num);
		request.setAttribute("testpaperMap", testpaperMap);
		request.setAttribute("testpaper_check_num", testpaper_check_num);
	    request.setAttribute("list_testpaper_radio_info", list_testpaper_radio_info);
	    request.setAttribute("list_testpaper_check_info", list_testpaper_check_info);

		
		//4.转向：
		request.getRequestDispatcher("/manage/testpaper/startExam.jsp").forward(request, response);
	
}



/**
 * @desc  4.生成试卷
 * @param request
 * @param response
 * @throws IOException 
 * @throws SQLException 
 * @throws FileNotFoundException 
 * @throws ClassNotFoundException 
 */
private void createTest(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
	
	//1.接值：接收本张试卷的id，其目的是为该试卷生成题目，进而生成试卷
	String id=request.getParameter("id");
	
	//2.生成试卷
	testpaperService.createTestPaperById(id);
	
	//3.重定向：
	 response.sendRedirect(request.getContextPath()+"/manage/testpaper.do?method=query");
	
	}



/**
 * @desc  3.保存添加页面的数据
 * @param request
 * @param response
 * @throws IOException 
 * @throws SQLException 
 * @throws FileNotFoundException 
 * @throws ClassNotFoundException 
 */
private void add(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		//1.接值：
		String testpaper_name=request.getParameter("testpaper_name");
		String testpaper_radio_num=request.getParameter("testpaper_radio_num");
		String testpaper_check_num=request.getParameter("testpaper_check_num");
		String testpaper_time_use=request.getParameter("testpaper_time_use");
		String classs_id=request.getParameter("classs_id");
		String course_id=request.getParameter("course_id");

		//2.存值：
		  testpaperService.save(testpaper_name,testpaper_radio_num,testpaper_check_num,testpaper_time_use,classs_id,course_id);
	
		//3.重定向
		  response.sendRedirect(request.getContextPath()+"/manage/testpaper.do?method=query");
	}




/**
 * @desc  2.进入添加页面
 * @param request
 * @param response
 * @throws SQLException 
 * @throws ClassNotFoundException 
 * @throws IOException 
 * @throws ServletException 
 */
private void addPage(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {

	//1.取值：查询班级信息和课程信息
	List<Map<String, Object>> list_classs=testpaperService.queryClasss();
	List<Map<String, Object>> list_course=testpaperService.queryCourse();

	//2.存值：
	request.setAttribute("list_classs",list_classs );
	request.setAttribute("list_course",list_course );
	
	
	//3.转向
	request.getRequestDispatcher("/manage/testpaper/add.jsp").forward(request, response);
}





/**
 * @desc  1.查询
 * @param request
 * @param response
 * @throws SQLException 
 * @throws ClassNotFoundException 
 * @throws IOException 
 * @throws ServletException 
 */
private void query(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
	
	System.out.println("TestpaperServlet--->query()");
	List<Map<String, Object>> list=testpaperService.queryTestpaper();
	request.setAttribute("list", list);
	request.getRequestDispatcher("/manage/testpaper/list.jsp").forward(request, response);
}

	
	
	
	
	
}
