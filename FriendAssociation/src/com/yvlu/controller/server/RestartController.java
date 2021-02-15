package com.yvlu.controller.server;

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
import com.yvlu.servlet.server.Restart;

@SuppressWarnings("serial")
@WebServlet("/api/server/restart")
public class RestartController extends HttpServlet{

	Restart restart = new Restart();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取参数
		 String  token = request.getParameter("token");	 
		//创建返回对象
		 
		Map<String,Object> data = new HashMap<String, Object>();
		PrintWriter out = response.getWriter();
		 if (token==null ||token.equals("")) {
				print(out, data, "-1", "请输入正确的参数");
		}else {
			out.print(restart.restart(token));
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
	
}
