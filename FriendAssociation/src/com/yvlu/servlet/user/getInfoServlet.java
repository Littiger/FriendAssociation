package com.yvlu.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.yvlu.dao.posts.tokenUtils;
import com.yvlu.dao.user.GetUserInfoUtil;
import com.yvlu.dao.user.userDao;

/**
 * @desc   获取所有用户
 * @author JZH
 * @time   2021年2月3日
 */
public class getInfoServlet{

	userDao userDao = new userDao();
	
	public void getInfo(HttpServletRequest request, HttpServletResponse response) {
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
		
		try {
			//判空
			if(token == null || token.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "非法请求");
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
			
			//获取数据
			List<Map<String, Object>> userList = userDao.getAllUser();
			if(userList == null || userList.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "暂无用户");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//data
			Map<String, Object> data = new HashMap<String, Object>();
			//users
			List<Map<String, Object>> users = new ArrayList<Map<String,Object>>();
			//提取信息
			for (Map<String, Object> map : userList) {
				users.add(GetUserInfoUtil.getUserInfo(map));
			}
			data.put("users", users);
			
			if(data != null || data.size() != 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "获取成功");
				jsonObject.put("data", data);
				writer.write(jsonObject.toJSONString());
				return;
			}
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "获取失败");
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
