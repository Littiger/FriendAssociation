package com.quifeng.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.user.FeedBackDao;

/**
 * @desc   发送反馈意见
 * @author JZH
 *
 */
public class FeedBackServlet {
	
	FeedBackDao feedBackDao = new FeedBackDao();

	public void feedBack(HttpServletRequest request, HttpServletResponse response) {
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		
		//接值
		String message = request.getParameter("message");
		String contact = request.getParameter("contact");
		
		try {
			
			//判空contact可以为空
			if(message == null || message.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入您的建议");
				return;
			}
			//发送
			int count = feedBackDao.addFeedBack(message,contact);
			if(count != 0){
				System.out.println("已接收到建议");
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "谢谢您的建议，我们会更加努力");
				return;
			}
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "发送失败");
			return;

		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toJSONString());
			return;
		} finally {
			writer.write(jsonObject.toJSONString());
			writer.flush();
			writer.close();
		}
		
	}
	
}
