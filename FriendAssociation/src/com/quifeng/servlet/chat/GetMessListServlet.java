package com.quifeng.servlet.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.quifeng.dao.chat.ChatDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.dao.DateUtils;

/**
 * @desc   聊天-获取消息列表
 * @author JZH
 * @time   2021-01-02
 */
public class GetMessListServlet {
	ChatDao chatDao = new ChatDao();
	TokenDao tokenDao = new TokenDao();
	
	public void getMessList(HttpServletRequest request, HttpServletResponse response) {
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
		try {
			
			//判空
			if(token == null || token.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token获取异常，请重新登录");
				writer.write(jsonObject.toString());
				return;
			}
			//判断是否登录
			if (tokenDao.queryToken(token) == null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}
			//获取消息列表
			List<Map<String, Object>> messageList = chatDao.queryMessListByToken(token);
			//没有任何消息
			if(messageList == null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "暂无消息");
				writer.write(jsonObject.toString());
				return;
			}

			//获取自己的id
			String id = tokenDao.queryUidByToken(token);
			//存储对方id
			List<String> list = new ArrayList<>();
			//用于返回json
			List<Map<String, Object>> data = new ArrayList<>();
			//处理数据
			for (Map<String, Object> map : messageList) {
				//判断是否已经获取过该用户消息
				if(list.contains(map.get("resserid").toString()) || list.contains(map.get("recipients").toString())){
					continue;//跳过
				}
				//存储每个消息
				Map<String, Object> map2 = new HashMap<String, Object>();
				if(id.equals(map.get("resserid").toString())){
					list.add(map.get("recipients").toString());
					//获取对方用户信息
					Map<String, Object> user = chatDao.queryUserById(map.get("recipients").toString());
					map2.put("targetname", user.get("username").toString());
					map2.put("targetid", user.get("uid").toString());
					if( user.get("useravatar") != null){//有头像
						map2.put("targetimg", user.get("useravatar").toString());
					}
					else{
						map2.put("targetimg", "");
					}
					map2.put("lastonetime", DateUtils.MillToHourAndMin(map.get("createtime").toString()));
					Map<String, Object> contentJson = (Map<String, Object>)JSONObject.parseObject(map.get("content").toString());
					map2.put("lastone", contentJson.get("data").toString());
					//获取未读消息数
					int unMessCount = chatDao.queryUnReadMessCount(id,map.get("recipients").toString());
					map2.put("unread", unMessCount);
				}
				else{
					list.add(map.get("resserid").toString());
					//获取对方用户信息
					Map<String, Object> user = chatDao.queryUserById(map.get("resserid").toString());
					map2.put("targetname", user.get("username").toString());
					map2.put("targetid", user.get("uid").toString());
					if( user.get("useravatar") != null){//有头像
						map2.put("targetimg", user.get("useravatar").toString());
					}
					else{
						map2.put("targetimg", "");//默认头像
					}
					
					map2.put("lastonetime", DateUtils.MillToHourAndMin(map.get("createtime").toString()));
					map2.put("lastone", map.get("content").toString());
					//获取未读消息数
					int unMessCount = chatDao.queryUnReadMessCount(id,map.get("resserid").toString());
					map2.put("unread", unMessCount);
				}
				//添加到data
				data.add(map2);
			}
			jsonObject = new JSONObject();
			jsonObject.put("code", "200");
			jsonObject.put("msg", "获取成功");
			jsonObject.put("data", data);
			writer.write(jsonObject.toString());
			return;
		}catch (Exception e) {
			System.out.println("异常---->"+e.getMessage());
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toString());
			return;
		} finally {
			writer.flush();
			writer.close();
		}
		
	}
	
	
}
