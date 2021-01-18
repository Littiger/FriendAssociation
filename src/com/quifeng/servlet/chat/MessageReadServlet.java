package com.quifeng.servlet.chat;

/**
 * @desc   聊天-消息置为已读
 * @author JZH
 * @time   2021-01-02
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.chat.ChatDao;
import com.quifeng.dao.token.TokenDao;

public class MessageReadServlet {
	ChatDao chatDao = new ChatDao();
	TokenDao tokenDao = new TokenDao();

	public void isRead(HttpServletRequest request, HttpServletResponse response) {
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		// 接值
		String token = request.getParameter("token");
		String targetid = request.getParameter("targetid");
		String chatid = request.getParameter("chatid");
		try {

			// 判空
			if (token == null || token.equals("")) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token获取异常，请重新登录");
				writer.write(jsonObject.toString());
				return;
			}
			if (targetid == null || targetid.equals("")) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				writer.write(jsonObject.toString());
				return;
			}
			if (chatDao.queryUserById(targetid) == null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "无此接收对象用户");
				writer.write(jsonObject.toString());
				return;
			}
			if (chatid == null || chatid.equals("")) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				writer.write(jsonObject.toString());
				return;
			}
			if (tokenDao.queryToken(token) == null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}

			// 自己的id
			String uid = tokenDao.queryUidByToken(token);
			if (chatid.equals("all")) {
				// 全部已读
				chatDao.updateIsRead(uid, targetid);
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "设置成功");
				writer.write(jsonObject.toString());
				return;
			} else {
				int count = chatDao.updateIsReadById(chatid);
				System.out.println(count);
				if (count > 0) {
					// 单条已读
					jsonObject = new JSONObject();
					jsonObject.put("code", "200");
					jsonObject.put("msg", "设置成功");
					writer.write(jsonObject.toString());
					return;
				} else {
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "无此消息");
					writer.write(jsonObject.toString());
					return;
				}
			}

		} catch (Exception e) {
			System.out.println("异常---->" + e.getMessage());
			e.printStackTrace();
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
