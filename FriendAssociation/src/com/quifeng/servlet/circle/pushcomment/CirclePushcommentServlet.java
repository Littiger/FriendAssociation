package com.quifeng.servlet.circle.pushcomment;

import java.io.FileNotFoundException;
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
 * @desc   发布评论
 * @author JZH
 * @time   2020-12-27
 */
public class CirclePushcommentServlet {

	CircleDao circleDao = new CircleDao();
	TokenDao tokenDao = new TokenDao();
	
	public void pushComment(HttpServletRequest request, HttpServletResponse response) {
		//初始化PrintWriter
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("PrintWriter获取异常---->"+e.getMessage());
		}
		//初始化json类
		JSONObject jsonObject = null;
		//接值
		String token = request.getParameter("token");
		String postid = request.getParameter("postid");
		String osfirstid = request.getParameter("osfirstid");
		String comment = request.getParameter("comment");
		
		try {
			//判断空值
			if(token == null || token.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token获取失败，登录后才可以评论哦");
				writer.write(jsonObject.toString());
				return;
			}
			if(postid == null || postid.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "postid获取异常");
				writer.write(jsonObject.toString());
				return;
			}
			if(comment == null || comment.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入评论内容");
				writer.write(jsonObject.toString());
				return;
			}
			//控制评论内容长度
			if(comment.length() > 50){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "评论内容过长，请控制在25个字符以内");
				writer.write(jsonObject.toString());
				return;
			}
			//获取用户信息
			Map<String, Object> user =  tokenDao.queryToken(token);
			Map<String, Object> userMessage = circleDao.queryUserById(user.get("uid").toString());
			//判断用户是否存在
			if(user == null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未获取到信息，请重新登录");
				writer.write(jsonObject.toString());
				return;
			}
			if(userMessage.get("userzt") == null){//未获取到用户状态
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "非法请求");
				writer.write(jsonObject.toString());
				return;
			}
			if(userMessage.get("userzt").toString().equals("3")){//禁止评论
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "您已被禁止评论");
				writer.write(jsonObject.toString());
				return;
			}
			if(userMessage.get("userzt").toString().equals("2")){//全部封禁
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "您已被封禁");
				writer.write(jsonObject.toString());
				return;
			}
			
			//通过osfirstid判断是几级评论
			//一级评论
			if(osfirstid == null || osfirstid.equals("")){
				//osfirst表增加评论、帖子评论量+1
				circleDao.addOsFiOs(postid,userMessage.get("uid").toString(),comment,userMessage.get("schoolid").toString());
				circleDao.addPostOsCount(postid);
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "发布成功");
				writer.write(jsonObject.toString());
				return;
			}
			//二级评论
			else if(Integer.parseInt(osfirstid) > 0){
				//添加二级评论  帖子评论量+1
				circleDao.addSeOs(osfirstid,comment,userMessage.get("schoolid").toString());
				circleDao.addPostOsCount(postid);
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "发布成功");
				writer.write(jsonObject.toString());
				return;
			}
			//二级以上评论
			else if(Integer.parseInt(osfirstid) < 0){
				//添加二级以上评论 帖子评论量+1
				circleDao.addOthOs(osfirstid,comment,userMessage.get("schoolid").toString());
				circleDao.addPostOsCount(postid);
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "发布成功");
				writer.write(jsonObject.toString());
				return;
			}
			else{
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请求异常");
				writer.write(jsonObject.toString());
				return;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发现异常---->"+e.getMessage());
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
