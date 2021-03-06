package com.yvlu.servlet.posts;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.yvlu.dao.posts.postsDao;
import com.yvlu.dao.posts.tokenUtils;

/**
 * @desc   审核指定帖子
 * @author JZH
 * @time   2021年2月3日
 */
public class auditServlet {

	postsDao postsDao = new postsDao();
	
	public void auditPost(HttpServletRequest request, HttpServletResponse response) {
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
		String postid = request.getParameter("postid");
		
		try {
			//判空
			if(token == null || token.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "非法请求");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(postid == null || postid.equals("")){
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
			
			//通过审核
			int count = postsDao.auditPost(postid);
			if (count != 0) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "审核成功");
				writer.write(jsonObject.toJSONString());
				return;
			}
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "审核失败");
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
