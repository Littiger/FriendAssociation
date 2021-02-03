package com.yvlu.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ndktools.javamd5.Mademd5;
import com.yvlu.dao.posts.tokenUtils;
import com.yvlu.dao.user.userDao;

/**
 * @desc   修改指定用户密码
 * @author JZH
 * @time   2021年2月3日
 */
public class changeKeyServlet {
	userDao userDao = new userDao();
	Mademd5 md5 = new Mademd5();

	public void changeKey(HttpServletRequest request, HttpServletResponse response) {
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("PrintWriter获取异常");
		}
		
		//接值
		String token = request.getParameter("token");
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		
		try {
			
			//判空
			if(token == null || token.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "非法请求");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(userid == null || userid.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(password == null || password.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入新密码");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//判断是否登录
			Map<String, Object> manager = tokenUtils.queryToken(token);
			if(manager == null || manager.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//判断用户是否存在
			Map<String, Object> user = userDao.queryUserById(userid);
			if(user == null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "无此用户");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//转换为MD5
			password = md5.toMd5(password);
			//判断新密码是否与原密码相同
			if(user.get("password").toString().equals(password)){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请勿与原密码相同");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//修改密码
			int count = userDao.updatePasswordById(userid,password);
			if(count != 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "修改成功");
				writer.write(jsonObject.toJSONString());
				return;
			}
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "修改失败");
			writer.write(jsonObject.toJSONString());
			return;
			
			
			
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
