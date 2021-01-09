package com.quifeng.servlet.chat;
/**
 * @desc   查询历史聊天记录
 * @author JZH
 * @time   2021-01-02
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.chat.ChatDao;
import com.quifeng.dao.token.TokenDao;

public class GethistoryServlet {
	ChatDao chatDao = new ChatDao();
	TokenDao tokenDao = new TokenDao();
	
	public void queryHistory(HttpServletRequest request, HttpServletResponse response) {
		//json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		//接值
		String token = request.getParameter("token");
		String targetid = request.getParameter("targetid");
		try {
			
			//判空
			if(token == null || token.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token获取异常，请重新登录");
				writer.write(jsonObject.toString());
				return;
			}
			if(targetid == null || targetid.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				writer.write(jsonObject.toString());
				return;
			}
			//是否登录
			if(tokenDao.queryToken(token) == null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}
			if(chatDao.queryUserById(targetid) == null){
		    	jsonObject = new JSONObject();
				jsonObject.put("code", "-3");
				jsonObject.put("msg", "接受对象不存在");
				writer.write(jsonObject.toJSONString());
				return;
		    }
			//自己的id
			String uid = tokenDao.queryUidByToken(token);
			if(uid.equals(targetid)){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "无法获取与自己的聊天");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(chatDao.queryFixById(uid, targetid) == null  || chatDao.queryFixById(uid, targetid).size() < 2){//不是互相关注
		    	jsonObject = new JSONObject();
				jsonObject.put("code", "-2");
				jsonObject.put("msg", "接受对象不是好友关系");
				writer.write(jsonObject.toJSONString());
				return;
		    }
			//获取消息
			List<Map<String, Object>> newsList = chatDao.queryNewsById(uid,targetid);
			if(newsList == null || newsList.size() < 1){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "暂无消息");
				writer.write(jsonObject.toJSONString());
				return;
			}
			else{
				List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
				for (Map<String, Object> map : newsList) {
					Map<String, Object> map2 = new HashMap<String, Object>();
					Map<String, Object> data2 = new HashMap<>();
					Map<String, Object> contentJson = (Map<String, Object>)JSONObject.parseObject(map.get("content").toString());
					data2.put("chatid", map.get("chatid").toString());
					data2.put("data", contentJson.get("data").toString());
					data2.put("type", contentJson.get("type").toString());
					map2.put("data", data2);
					if(map.get("resserid").toString().equals(uid)){//自己发的
						map2.put("type", 0);
					}
					else{//对方发的
						map2.put("type", 1);
					}
					data.add(map2);
				}
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "获取成功");
				jsonObject.put("data", data);
				writer.write(jsonObject.toString());
			}
			
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
