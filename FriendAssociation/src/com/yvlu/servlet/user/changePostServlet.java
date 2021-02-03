package com.yvlu.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.utils.state.StateUtils;
import com.quifeng.utils.state.UserType;
import com.yvlu.dao.posts.tokenUtils;
import com.yvlu.dao.user.userDao;

/**
 * @desc   修改指定用户发帖权限
 * @author JZH
 * @time   2021年2月3日
 */
public class changePostServlet {
	
	userDao userDao = new userDao();

	public void changePost(HttpServletRequest request, HttpServletResponse response) {
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
		String status = request.getParameter("status");
		
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
			if(status == null || status.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
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
			if(!status.equals("0") && !status.equals("1")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数错误");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if((user.get("userzt").toString().equals("1") || user.get("userzt").toString().equals("2")) && status.equals("1")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请勿重复修改");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if((!user.get("userzt").toString().equals("1") && !user.get("userzt").toString().equals("2")) && status.equals("0")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请勿重复修改");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//修改状态
			boolean flag = false;
			if(status.equals("1")){
				if(user.get("userzt").toString().equals("3")){//同时禁止了评论
					flag = StateUtils.queryType(userid, UserType.USRETOW);//状态改为2
				}
				else{
					flag = StateUtils.queryType(userid, UserType.USERONE);//状态改为1
				}
			}
			else{
				if(user.get("userzt").toString().equals("2")){//全部被禁止了
					flag = StateUtils.queryType(userid, UserType.USERTHREE);//状态改为3
				}
				else{
					flag = StateUtils.queryType(userid, UserType.USERZERO);//状态改为0
				}
			}
			if(flag == true){
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
