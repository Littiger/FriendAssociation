package com.yvlu.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.yvlu.dao.posts.tokenUtils;
import com.yvlu.dao.user.userDao;

/**
 * @desc   获取指定用户发帖权限
 * @author JZH
 * @time   2021年2月3日
 */
public class getcommentServlet {
	userDao userDao = new userDao();

	public void getComment(HttpServletRequest request, HttpServletResponse response) {
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("PrintWriter获取异常");
		}
		
		//接值
		String token = request.getParameter("token");
		String userid = request.getParameter("userid");
		
		try {

			//判空
			if(token == null || token.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "非法请求");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(userid == null || userid.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//判断是否登录
			Map<String, Object> manager = tokenUtils.queryToken(token);
			if(manager == null || manager.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//判断用户是否存在
			Map<String, Object> user = userDao.queryUserById(userid);
			if(user == null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "无此用户");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			//获取信息
			Map<String, Object> status = new HashMap<String, Object>();
			if(user.get("userzt").toString().equals("3") || user.get("userzt").toString().equals("2")){
				status.put("status", 1);
			}
			else if(!user.get("userzt").toString().equals("3") && !user.get("userzt").toString().equals("2")){
				status.put("status", 0);
			}
			jsonObject = new JSONObject();
			jsonObject.put("code", "200");
			jsonObject.put("msg", "获取成功");
			jsonObject.put("data", status);
			writer.write(jsonObject.toJSONString());
			return;
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toJSONString());
			return;
		} finally {
			writer.flush();
			writer.close();
		}
	}
}
