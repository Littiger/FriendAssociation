package com.yvlu.servlet.posts;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.yvlu.dao.posts.getPostInfoUtil;
import com.yvlu.dao.posts.postsDao;
import com.yvlu.dao.posts.tokenUtils;

/**
 * @desc   获取所有审核过了的帖子信息
 * @author JZH
 * @time   2021年2月3日
 */
public class getInfoedServlet {

	postsDao postsDao = new postsDao();
	
	public void getInfoed(HttpServletRequest request, HttpServletResponse response) {
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
				jsonObject.put("code", "-2");
				jsonObject.put("msg", "非法调用");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//判断是否登录
			Map<String, Object> manager = tokenUtils.queryToken(token);
			if(manager == null || manager.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-2");
				jsonObject.put("msg", "非法调用");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			//获取数据
			List<Map<String, Object>> posts = postsDao.getAllEdPosts();
			if(posts == null || posts.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "暂无帖子");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//data
			List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : posts) {
				data.add(getPostInfoUtil.getInfo(map));
			}
			if(data != null || data.size() != 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "请求成功");
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
