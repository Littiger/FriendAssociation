package com.quifeng.servlet.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.chat.ChatDao;
import com.quifeng.dao.user.UserDao;
import com.quifeng.utils.dao.DateUtils;

/**
 * @Desc url : http：//127.0.0.1/api/chat/getmesslist
 * @author 语录
 *
 */
public class GetmesslistServlet {

	//操作user的dao
	UserDao userDao = new UserDao();
	ChatDao chatDao =new ChatDao();

	/**
	 * @Desc  api 的逻辑代码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getMesslist(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//接值
		String token = request.getParameter("token");
		//创建 PrintWriter 对象
		PrintWriter out = response.getWriter();
		//创建返回出错的Map
		Map<String, Object> data = new LinkedHashMap<>();
		//用来防止非法请求
		if (token==null||token.equals("")) {
			print(out, data, "-5", "请求非法");
		}		
		//查询id
		Map<String, Object> userMap = userDao.getUserByToken(token);
		//没有查询到用户 token 失效 
		if (userMap==null) {
			print(out, data, "-1", "请重新登陆");
			return;
		}
		//获取到user
		
		//获取uid
		String uid = userMap.get("uid").toString();
		//查询聊天表
		List<Map<String, Object>> messList = chatDao.getChatMess(uid);
		//查询到用户没有聊过天
		if (messList.size()==0) {
			print(out, data, "200", "您还没有发送过消息");
			return;
		}
		//创建dataList
		List<Map<String, Object>> dataList = new  ArrayList<Map<String,Object>>();
		//向list中添加数据
		for (Map<String, Object> map : messList) {
			//创建dataList中的Map
			Map<String, Object> dataP=new HashMap<String, Object>();
			//添加需要的数据
			dataP.put("targetname", map.get("username")); //获取对方的用户名
			dataP.put("targetid", map.get("uid")); //对方的id 没有加密
			dataP.put("targetimg", map.get("useravatar")); //对方的头像
			System.out.println(map.get("uid"));
			
			//获取最后一条消息
			Map<String, Object> endMap = chatDao.getEnd(uid,map.get("uid").toString());
			//添加最后一条对话的时间
			dataP.put("lastonetime", DateUtils.getForMat(endMap.get("createtime").toString()));
			//添加聊天内容 
			dataP.put("lastone", endMap.get("content"));
			//获取没有读到消息的多少
			Map<String, Object> countMap = chatDao.getCountZ(map.get("uid").toString(), uid);
			//添加没有读消息的多少
			dataP.put("unread", countMap.get("count"));
			dataList.add(dataP);
		}
		//向data中添加list
		data.put("data", dataList);
		//向前端返回数据
		print(out, data, "200", "获取成功");
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
