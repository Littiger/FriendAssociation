package com.quifeng.servlet.circle.ospraise;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.circle.CircleDao;
import com.quifeng.dao.token.TokenDao;

/**
 * @desc   评论点赞
 * @author JZH
 * @time   2020-12-27
 */
public class CircleOspraiseServlet {

	CircleDao circleDao = new CircleDao();
	TokenDao tokenDao = new TokenDao();
	
	public void AddOsZan(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		//接值
		String token = request.getParameter("token");
		String commentid = request.getParameter("commentid");
		try{
			//判空
			if(token == null || token.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token获取异常，请重新登录");
				writer.write(jsonObject.toString());
				return;
			}
			if(commentid == null || commentid.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "评论获取异常");
				writer.write(jsonObject.toString());
				return;
			}
			//判断是否登录
			if(tokenDao.queryToken(token) == null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}
			//判断是否点过赞
			Map<String, Object> isOsZan = circleDao.isOsZan(commentid,token);
			if(isOsZan == null){//未点过赞
				//zan表添加数据
				circleDao.addOsZan(commentid,token);
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "已点赞");
				writer.write(jsonObject.toString());
				return;
			}
			else{
				//取消赞
				if(isOsZan.get("display").equals("0")){
					circleDao.DelOsZan(commentid,token);
					jsonObject = new JSONObject();
					jsonObject.put("code", "200");
					jsonObject.put("msg", "已取消");
					writer.write(jsonObject.toString());
					return;
				}
				//点赞
				else{
					circleDao.addOsZan2(commentid,token);
					jsonObject = new JSONObject();
					jsonObject.put("code", "200");
					jsonObject.put("msg", "已点赞");
					writer.write(jsonObject.toString());
					return;
				}
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toString());
			return;
		}finally {
			
		}
	}
	
}
