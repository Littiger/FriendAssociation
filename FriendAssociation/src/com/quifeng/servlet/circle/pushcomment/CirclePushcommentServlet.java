package com.quifeng.servlet.circle.pushcomment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.circle.CircleDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.dao.DateUtils;

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
				long time =	circleDao.addOsFiOs(postid,userMessage.get("uid").toString(),comment,userMessage.get("schoolid").toString());
				circleDao.addPostOsCount(postid);
				//获取一级评论信息
				Map<String, Object> OsMessage = circleDao.queryFiOsMessageById(userMessage.get("uid").toString(),time);
				Map<String, Object> userinfo = new HashMap<String, Object>();
				userinfo.put("uname", OsMessage.get("username").toString());
				userinfo.put("useravatar", OsMessage.get("useravatar").toString());
				Map<String, Object> data = new HashMap<>();
				data.put("userinfo", userinfo);
				data.put("osfirstid", OsMessage.get("osfirstid").toString());
				//是否点赞
				//查看该评论是否被点赞
				Map<String, Object> zan = circleDao.queryOsZan(OsMessage.get("osfirstid").toString() ,token);
				if(zan == null){
					data.put("isgreat", false);
				}
				else{
					data.put("isgreat", true);
				}
				//type....
				
				data.put("comment", OsMessage.get("ostext").toString());
				data.put("postzan", circleDao.queryOsZan(OsMessage.get("osfirstid").toString()));
				data.put("commenttime", DateUtils.MillToTime(OsMessage.get("createtime").toString()));
				data.put("postos", circleDao.queryOsCount(OsMessage.get("osfirstid").toString()));
				
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "发布成功");
				jsonObject.put("data", data);
				writer.write(jsonObject.toString());
				return;
			}
			//二级评论
			else if(Integer.parseInt(osfirstid) > 0){
				//查看是否有该评论
				Map<String, Object> osMess = circleDao.queryFiOs(osfirstid,postid);
				if(osMess == null){
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "未找到此评论");
					writer.write(jsonObject.toString());
					return;
				}
				//添加二级评论  帖子评论量+1
				long time = circleDao.addSeOs(osfirstid,comment,userMessage.get("schoolid").toString(),userMessage.get("uid").toString());
				circleDao.addPostOsCount(postid);
				//获取评论信息
				Map<String, Object> OsMessage = circleDao.queryOthOsMessageById(userMessage.get("uid").toString(),time);
				Map<String, Object> map = new HashMap<>();
				Map<String, Object> userinfo = new HashMap<>();
				userinfo.put("uname",OsMessage.get("username").toString());
				userinfo.put("useravatar", OsMessage.get("useravatar").toString());
				map.put("userinfo", userinfo);
				map.put("osfirstid", -Integer.parseInt(OsMessage.get("osotherid").toString()));
				//是否点赞
				if(circleDao.queryOsZan(OsMessage.get("osotherid").toString(), token) == null){
					map.put("isgreat", false);
				}
				else{
					map.put("isgreat", true);
				}
				map.put("comment", OsMessage.get("osothertext"));
				//获取赞
				if(OsMessage.get("count") == null || OsMessage.get("count").toString().equals("")){
					map.put("postzan","0");
				}
				else{
					map.put("postzan", OsMessage.get("count").toString());
				}
				//发布时间
				map.put("commenttime", DateUtils.MillToTime(OsMessage.get("createtime").toString()));
				//回复数量
				map.put("postos", circleDao.querySEOs(OsMessage.get("osotherid").toString()));
				
				//该评论upid是负数   二级评论
				if(Integer.parseInt(OsMessage.get("upid").toString()) < 0){
					map.put("superior", "");
 				}
				
				
				
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "发布成功");
				jsonObject.put("data", map);
				writer.write(jsonObject.toString());
				
				return;
			}
			//二级以上评论
			else if(Integer.parseInt(osfirstid) < 0){
				//查看是否有次二级评论
				Map<String, Object> osMap = circleDao.queryOthOsById(-Integer.parseInt(osfirstid)+"");
				if(osMap == null){
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "未找到此评论");
					writer.write(jsonObject.toString());
					return;
				}
				
				//添加二级以上评论 帖子评论量+1
				long time = circleDao.addOthOs(osfirstid,comment,userMessage.get("schoolid").toString(),userMessage.get("uid").toString());
				circleDao.addPostOsCount(postid);
				Map<String, Object> OsMessage = circleDao.queryOthOsMessageById(userMessage.get("uid").toString(),time);
				Map<String, Object> map = new HashMap<>();
				Map<String, Object> userinfo = new HashMap<>();
				userinfo.put("uname",OsMessage.get("username").toString());
				userinfo.put("useravatar", OsMessage.get("useravatar").toString());
				map.put("userinfo", userinfo);
				map.put("osfirstid", -Integer.parseInt(OsMessage.get("osotherid").toString()));
				//是否点赞
				if(circleDao.queryOsZan(OsMessage.get("osotherid").toString(), token) == null){
					map.put("isgreat", false);
				}
				else{
					map.put("isgreat", true);
				}
				map.put("comment", OsMessage.get("osothertext"));
				//获取赞
				if(OsMessage.get("count") == null || OsMessage.get("count").toString().equals("")){
					map.put("postzan","0");
				}
				else{
					map.put("postzan", OsMessage.get("count").toString());
				}
				//发布时间
				map.put("commenttime", DateUtils.MillToTime(OsMessage.get("createtime").toString()));
				//回复数量
				map.put("postos", circleDao.querySEOs(OsMessage.get("osotherid").toString()));
				
				//该评论upid是负数   二级评论
				if(Integer.parseInt(OsMessage.get("upid").toString()) < 0){
					map.put("superior", "");
 				}
				//upid是正数 二级以上评论
				else{
					//获取该评论上级id
					String upid = OsMessage.get("upid").toString();
					//获取上级平论信息
					Map<String, Object> upOs = circleDao.queryOthOsById(upid);
					System.out.println(upOs);
					//上级评论
					Map<String, Object> superior = new HashMap<>();
					//用户信息
					Map<String, Object> userinfo2 = new HashMap<>();
					userinfo2.put("uname", upOs.get("username").toString());
					userinfo2.put("useravatar", upOs.get("useravatar").toString());
					superior.put("userinfo", userinfo2);
					//评论信息
					superior.put("osfirstid", -Integer.parseInt(upOs.get("osotherid").toString()));
					//是否点赞
					if(circleDao.queryOsZan(-Integer.parseInt(upOs.get("osotherid").toString())+"", token) == null){
						superior.put("isgreat", false);
					}
					else{
						superior.put("isgreat", true);
					}
					superior.put("comment", upOs.get("osothertext").toString());
					//获取赞
					if(upOs.get("count") == null || upOs.get("count").toString().equals("")){
						superior.put("postzan","0");
					}
					else{
						superior.put("postzan", upOs.get("count").toString());
					}
					
					superior.put("commenttime", DateUtils.MillToTime(upOs.get("createtime").toString()));
					superior.put("postos", circleDao.querySEOs(upOs.get("osotherid").toString()));
					map.put("superior", superior);
				}
				
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "发布成功");
				jsonObject.put("data", map);
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
