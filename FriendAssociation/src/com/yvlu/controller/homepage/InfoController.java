package com.yvlu.controller.homepage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yvlu.servlet.homepage.InfoServlet;

@SuppressWarnings("serial")
@WebServlet("/api/homepage/info")
public class InfoController extends HttpServlet{

	private InfoServlet infoServlet = new InfoServlet();
	
	
	
	
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//获取参数
		 String  token = request.getParameter("token");	 
		//创建返回对象
		Map<String,Object> data = new HashMap<String, Object>();
		PrintWriter out = response.getWriter();
		 if (token==null ||token.equals("")) {
				print(out, data, "-2", "参数异常");
		}else {
			out.print(infoServlet.info(token));
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
