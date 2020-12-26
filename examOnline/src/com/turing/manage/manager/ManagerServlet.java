package com.turing.manage.manager;

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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


public class ManagerServlet extends HttpServlet {
	
IManagerService managerService=new ManagerServiceImpl();
	
HttpSession session;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ManagerServlet--->service()");
		String method=request.getParameter("method");
		
		try 
		{
			if ("query".equals(method)) {
				this.query(request,response);
			}else if ("upload".equals(method)){
				this.upload(request,response);
			}else if ("download".equals(method)){
				this.download(request,response);
			}else if ("editpage".equals(method)){
				this.editpage(request,response);
			}else if ("edit".equals(method)){
				this.edit(request,response);
			}else if ("delete".equals(method)){
				this.delete(request,response);
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
		catch (FileUploadException e) 
		{
		  System.out.println("文件上传异常...");
		}
		
		
	}
	
	
/**
 * @desc  删除数据
 * @param request
 * @param response
 * @throws ClassNotFoundException
 * @throws SQLException
 * @throws IOException
 */
private void delete(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("ManagerServlet--->delete()");
		String manager_id = request.getParameter("manager_id");
		managerService.delete(manager_id);
		response.sendRedirect(request.getContextPath()+"/manage/manager.do?method=query");
		
	}

/**
 * @desc  修改数据
 * @param request
 * @param response
 * @throws FileUploadException
 * @throws IOException
 * @throws ClassNotFoundException
 * @throws SQLException
 */
private void edit(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IOException, ClassNotFoundException, SQLException {
		System.out.println("ManagerServlet--->edit()");
		
		//4.0.设定类型和编码
		  response.setContentType("text/html;charset=utf-8");
		
		  //4.1声明变量
		  String fileSaveName=null;//将来保存再服务器硬盘上的图片的名字
		  List<FileItem> formItemList;//解析的上传页面的表单元素的结果集合
		  
	      //4.2.设定图片保存在服务器上的路径
			String path = this.getServletContext().getRealPath("/") + "WEB-INF/image";
		    System.out.println("图片存储路径：" + path);
		
		    //4.3.根据路径名创建一个 File实例其目的是为了创建存储的路径目录
		    File file=new File(path);
		    if (!file.exists()) 
		    {
			 file.mkdir();//创建image文件夹	
			}
		    
		   //4.4.将请求消息实体中的每一个项目封装成单独的DiskFileItem (FileItem接口的实现) 对象的任务
	        //直白的说就是将本次请求的request封装成DiskFileItemFactory对象 
		   DiskFileItemFactory factory=new DiskFileItemFactory();
		   
		 //4.5.使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		   ServletFileUpload upload=new ServletFileUpload(factory);
		   //4.6设定中文处理
		   upload.setHeaderEncoding("utf-8");
		   formItemList =upload.parseRequest(request);

		   if ((formItemList!=null)&&(formItemList.size()>0))
		   {
			   for (FileItem Item : formItemList) 
			   {
				   if (!Item.isFormField()) 
				   {
					   //System.out.println("--->"+Item);
					   
					   //获取上传图片的名字
					   String fileName = Item.getName();
					   System.out.println("上传文件的名字:"+fileName);
					  //获取后缀
					   String prifix = fileName.substring(fileName.lastIndexOf(".")+1);
					   System.out.println("上传文件的后缀:"+prifix);
					   
					   //重新设定保存再服务器硬盘上的图片的名字，UUID+(session)
					   String id=UUID.randomUUID().toString();
					   fileSaveName =id+"."+prifix;
					   System.out.println("保存文件的名字："+fileSaveName);
					   
					   //利用commons-io.jar包中的方法实现保存到硬盘上
					   FileUtils.copyInputStreamToFile(Item.getInputStream(), new File(path+"/"+fileSaveName));
				   }
			   }
		   }
		   //5.将添加页面的数据信息保存到数据库中
		   String virtualPath=fileSaveName;
		   String manager_id = formItemList.get(0).getString("utf-8");
		   String manager_name=formItemList.get(1).getString("utf-8");
		   String manager_pass=formItemList.get(2).getString("utf-8");
		  managerService.edit(manager_id,manager_name,manager_pass,virtualPath);
		   
		   //6.重定向
		   response.sendRedirect(request.getContextPath()+"/manage/manager.do?method=query");
		
	}

/**
 * @desc  跳转修改页面
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 * @throws ClassNotFoundException
 * @throws SQLException
 */
private void editpage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
	
	System.out.println("ManagerServlet--->editpage()");
	String manager_id = request.getParameter("manager_id");
	System.out.println(manager_id);
	Map<String, Object> map = managerService.queryOne(manager_id);
	request.setAttribute("map", map);
	request.getRequestDispatcher("/manage/manager/edit.jsp").forward(request, response);
	
	}


public void downloadOutsider(HttpServletRequest request,HttpServletResponse response) throws IOException
{
	//1.外链图片的网络资源地址
	String imgUrl="https://www.baidu.com/img/bd_logo1.png";

	//2.实例化URL类
	URL  url=new URL(imgUrl);

	//3.获取URLConnection对象
	 URLConnection connection = url.openConnection();
	
	 //4.利用urlConnection获取输入流对象
	InputStream inputStream = connection.getInputStream();
	
	//5.设定允许跨域访问
	response.setContentType("image/jpg"); //设置返回的文件类型   
	response.setHeader("Access-Control-Allow-Origin", "*");//设置该图片允许跨域访问
	
	//6.下载图片
	IOUtils.copy(inputStream, response.getOutputStream());
}
	
	
	
	
/**
 * @desc  2.下载
 * @param request
 * @param response
 * @throws IOException 
 */
private void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
	System.out.println("ManagerServlet--->download()");
	  //1.接值：
		System.out.println(request.getParameter("map"));
		String manager_imgpath = request.getParameter("manager_imgpath");
		System.out.println(manager_imgpath);
		try {
			String path = this.getServletContext().getRealPath("\\") + "WEB-INF\\image\\";
			System.out.println(path+manager_imgpath);
			File file = new File(path+manager_imgpath);//将服务器上的图片路径转换为文件
			FileInputStream fileInputStream = new FileInputStream(file);//将上步的文件转变为流
			System.out.println("fileInputStream----->"+fileInputStream);
			response.setContentType("image/jpg"); //设置返回的文件类型   
			response.setHeader("Access-Control-Allow-Origin", "*");//设置该图片允许跨域访问
			IOUtils.copy(fileInputStream, response.getOutputStream());//利用commons-io.jar包中的方法实现根据文件输出流转换为图片
		} 
		catch (FileNotFoundException e) //当服务器响应异常时候的处理
		{
			/**
			String path = this.getServletContext().getRealPath("/") + "WEB-INF/image/默认.jpg";
			File  fileLocal=new File(path);
			FileInputStream fileInputStream=new FileInputStream(fileLocal);
			System.out.println("图片没找到。。。。。");
			response.setContentType("image/jpg"); //设置返回的文件类型   
			response.setHeader("Access-Control-Allow-Origin", "*");
			IOUtils.copy(fileInputStream, response.getOutputStream());**/
			System.out.println("图片没找到。。。。。");
			downloadOutsider(request,response);
		} 
	
	}


/**
 * @desc  1.上传
 * @param request
 * @param response
 * @throws FileUploadException 
 * @throws IOException 
 * @throws SQLException 
 * @throws ClassNotFoundException 
 */
private void upload(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IOException, ClassNotFoundException, SQLException {
		
	  //4.0.设定类型和编码
	  response.setContentType("text/html;charset=utf-8");
	
	  //4.1声明变量
	  String fileSaveName=null;//将来保存再服务器硬盘上的图片的名字
	  List<FileItem> formItemList;//解析的上传页面的表单元素的结果集合
	  
      //4.2.设定图片保存在服务器上的路径
		String path = this.getServletContext().getRealPath("/") + "WEB-INF/image";
	    System.out.println("图片存储路径：" + path);
	
	    //4.3.根据路径名创建一个 File实例其目的是为了创建存储的路径目录
	    File file=new File(path);
	    if (!file.exists()) 
	    {
		 file.mkdir();//创建image文件夹	
		}
	    
	   //4.4.将请求消息实体中的每一个项目封装成单独的DiskFileItem (FileItem接口的实现) 对象的任务
        //直白的说就是将本次请求的request封装成DiskFileItemFactory对象 
	   DiskFileItemFactory factory=new DiskFileItemFactory();
	   
	 //4.5.使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
	   ServletFileUpload upload=new ServletFileUpload(factory);

	   //4.6设定中文处理
	   upload.setHeaderEncoding("utf-8");
	   formItemList =upload.parseRequest(request);
	   
	   if ((formItemList!=null)&&(formItemList.size()>0))
	   {
		   for (FileItem Item : formItemList) 
		   {
			   if (!Item.isFormField()) 
			   {
				   //System.out.println("--->"+Item);
				   
				   //获取上传图片的名字
				   String fileName = Item.getName();
				   System.out.println("上传文件的名字:"+fileName);
				  //获取后缀
				   String prifix = fileName.substring(fileName.lastIndexOf(".")+1);
				   System.out.println("上传文件的后缀:"+prifix);
				   
				   //重新设定保存再服务器硬盘上的图片的名字，UUID+(session)
				   String id=UUID.randomUUID().toString();
				   fileSaveName =id+"."+prifix;
				   System.out.println("保存文件的名字："+fileSaveName);
				   
				   //利用commons-io.jar包中的方法实现保存到硬盘上
				   FileUtils.copyInputStreamToFile(Item.getInputStream(), new File(path+"/"+fileSaveName));
			   }
		   }
	   }
	   //5.将添加页面的数据信息保存到数据库中
	   String virtualPath=fileSaveName;
	   String manager_name=formItemList.get(0).getString("utf-8");
	   System.out.println("manager_name"+manager_name);
	   String manager_pass=formItemList.get(1).getString("utf-8");
	   System.out.println("manager_pass"+manager_pass);
	  managerService.save(manager_name,manager_pass,virtualPath);
	   
	   //6.重定向
	   response.sendRedirect(request.getContextPath()+"/manage/manager.do?method=query");
	   
	   
	   
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
	
	
	  List<Map<String, Object>> list =managerService.query();
	
	  request.setAttribute("list", list);
	
	  request.getRequestDispatcher("/manage/manager/list.jsp").forward(request, response);
}
}
