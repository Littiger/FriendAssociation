package com.quifeng.servlet.circle.postpraise;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.circle.CircleDao;
import com.quifeng.dao.token.TokenDao;
/**
 * @desc   帖子点赞
 * @author JZH
 * @time   2020-12-27
 */
public class CirclePostpraiseServlet {

	CircleDao circleDao = new CircleDao();
	TokenDao tokenDao = new TokenDao();
	
	public void postAddZan(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		//接值
		String token = request.getParameter("token");
		String postid = request.getParameter("postid");
		try{
			//判空
			if(token == null || token.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token获取异常，请重新登陆");
				writer.write(jsonObject.toString());
				return;
			}
			if(postid == null || postid.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数获取异常");
				writer.write(jsonObject.toString());
				return;
			}
			//判断token
			if(tokenDao.queryToken(token) == null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}
			//点赞
			circleDao.addPostZan(postid,token);
			//查询点赞情况
			Map<String, Object> display = circleDao.queryPostZan(postid,token);
			if(display != null){
				if(display.get("display").toString().equals("0")){
					jsonObject = new JSONObject();
					jsonObject.put("code", "200");
					jsonObject.put("msg", "已点赞");
					writer.write(jsonObject.toString());
					return;
				}
				else{
					jsonObject = new JSONObject();
					jsonObject.put("code", "200");
					jsonObject.put("msg", "已取消");
					writer.write(jsonObject.toString());
					return;
				}
			}
			else{
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "请求异常");
				writer.write(jsonObject.toString());
				return;
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toString());
			return;
		}finally {
			writer.flush();
			writer.close();
		}
		
	}
	
}
