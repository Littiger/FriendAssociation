package com.quifeng.servlet.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.chat.ChatDao;
import com.quifeng.dao.user.UserDao;

/**
 * @Desc http：//127.0.0.1/api/user/gethistory
 * @author 语录
 *
 */
public class GethistoryServlet {
	UserDao userDao = new UserDao();
	ChatDao chatDao =new ChatDao();
	/**
	 * @Desc 查询聊天记录的业务逻辑
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void gethistory(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//接值
		String token = request.getParameter("token");
		String targetid = request.getParameter("targetid");
		//创建 PrintWriter 对象
		PrintWriter out = response.getWriter();
		//创建返回出错的Map
		Map<String, Object> data = new LinkedHashMap<>();
		
		//这里是防止非法调用的
		if (targetid==null||token==null||targetid.equals("")||token.equals("")) {
			print(out, data, "-5", "非法调用");
			return;
		}
		
		//验证token
		Map<String, Object> userMap = userDao.getUserByToken(token);
		if (userMap==null) {
			print(out, data, "-1", "未登录");
			return;
		}
		//查询接收的账户是否存在
		Map<String, Object> userTar = userDao.getUserById(targetid);
		//为空接收对象不存在
		if (userTar==null) {
			print(out, data, "-3", "接收对象不存在");
			return;
		}
		//查询发送的人是否是自己的好友
		
		//获取uid
		String uid = userMap.get("uid").toString();
		//查询是否相互关注
		Map<String, Object> fix1 = chatDao.isFixidez(uid, targetid);
		Map<String, Object> fix2 = chatDao.isFixidez(targetid, uid);
		//不是好友返回数据
		if (fix1==null||fix2==null) {
			print(out, data, "-2", "接收对象不是好友关系");
			return;
		}
		//这里是查询聊天记录了
		List<Map<String, Object>> chatLiatu = chatDao.getThisTory(uid,targetid);
		List<Map<String, Object>> chatLiatt = chatDao.getThisTory(targetid,uid);
		//创建一个list合并他俩
		List<Map<String, Object>> chatList = new ArrayList<>(); 
		chatList.addAll(chatLiatt);
		chatList.addAll(chatLiatu);
		//合并后对chatLiat进行排序
		Collections.sort(chatList, new Comparator<Map<String, Object>>(){ 
			   public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			    long t1 = Long.parseLong(o1.get("createtime").toString());
			    long t2 = Long.parseLong(o2.get("createtime").toString());
			    if (t1>t2) {
					return -1;
				}else {
					return 1;
				}
			    
			}
		}
		);
		//创建返回的List
		List<Map<String, Object>> dataList = new ArrayList<>(); 
		
		//向dataList中添加数据
		for (Map<String, Object> map : chatList) {
			Map<String, Object> dataP = new HashMap<>(); 
			//这里是判断是谁给谁发送的
			String resserid = map.get("resserid").toString();			
			if (resserid.equals(uid)) {
				dataP.put("type", "0");
			}else {
				dataP.put("type", "1");
			}
			JSONObject contenetJson = JSONObject.parseObject(map.get("content").toString());	
			//添加聊天内容 这里存的就是 json 直接取就可以了
			dataP.put("data", contenetJson);
			//向datalist中添加数据
			dataList.add(dataP);
		}
		data.put("data", dataList);
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
