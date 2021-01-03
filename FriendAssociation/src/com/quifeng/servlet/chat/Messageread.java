package com.quifeng.servlet.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.chat.ChatDao;
import com.quifeng.dao.user.UserDao;

/**
 * @Desc  url : http：//127.0.0.1/api/chat/messageread
 * @author 语录
 *
 */
public class Messageread {

	UserDao userDao = new UserDao();
	ChatDao chatDao = new ChatDao();
	
	/**
	 * @Desc 设置消息为已读
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void messageread(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//接值
		String token = request.getParameter("token");
		String targetid = request.getParameter("targetid");
		String chatid = request.getParameter("chatid");
	
		//创建 PrintWriter 对象
		PrintWriter out = response.getWriter();
		//创建返回出错的Map
		Map<String, Object> data = new LinkedHashMap<>();
	
		//这里是防止非法调用的
		if (targetid==null||chatid==null||targetid.equals("")||chatid.equals("")) {
			print(out, data, "-5", "非法调用");
			return;
		}
		//验证token
		Map<String, Object> userMap = userDao.getUserByToken(token);
		if (userMap==null) {
			print(out, data, "-1", "未登录");
			return;
		}
		//查询发送人是否是正确的
		Map<String, Object> userTar = userDao.getUserById(targetid);
		//为空对象不存在
		if (userTar==null) {
			print(out, data, "-3", "你的好友在外太空哦");
			return;
		}
		//判断传递过来的chat是否为all
		if (chatid.equals("all")) {
			//是all删除所有的
			int count = chatDao.updateChatAll(userMap.get("uid").toString(), targetid);
			if (count>0) {
				print(out, data, "200", "设置成功");
			}else {
				print(out, data, "-1", "设置失败");
			}
		}else {
			//不是是设置一个的
			int count = chatDao.updateChatByID(chatid);
			if (count>0) {
				print(out, data, "200", "设置成功");
			}else {
				print(out, data, "-1", "设置失败");
			}
		}
	}
	

	/**
	 * @Desc 返回json的封装
	 * @param out
	 * @param data
	 * @param coed
	 * @param msg
	 */
	public void print(PrintWriter out,Map<String, Object> data,String coed,String msg){
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		out.close();
	}
}
