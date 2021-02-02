package com.yvlu.controller.manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yvlu.servlet.manager.LoginServicve;

/**
 * @Desc root 登录
 * @author 语录
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/manager/login")
public class LoginController  extends HttpServlet{

	//获取service
	private LoginServicve loginServicve = new LoginServicve();
	
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取参数
		 String  username = request.getParameter("username");
		 String  password = request.getParameter("password");		 
		//创建返回对象
		Map<String,Object> data = new HashMap<String, Object>();
		PrintWriter out = response.getWriter();
		 if (username==null ||username.equals("")||password==null||password.equals("")) {
				print(out, data, "-2", "请输入用户名密码");
		}else {
			out.print(loginServicve.login(username, password));
		}
	}
	
	
	
	/**
	 * @Desc 返回数据
	 * @param out
	 * @param data
	 * @param coed
	 * @param msg
	 */
	public void print(PrintWriter out,Map<String, Object> data,String coed,String msg){
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		out.close();
	}
	public Map<String, Object> pring(Map<String,Object> data ,String code,String msg){	
		data.put("code", code);
		data.put("msg", msg);
		return data;
	}
	
	
}
