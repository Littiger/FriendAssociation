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
 * @desc   修改指定用户登录权限
 * @author JZH
 * @time   2021年2月3日
 */
public class changeLoginServlet {
	userDao userDao = new userDao();

	public void changeLogin(HttpServletRequest request, HttpServletResponse response) {
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
			//查询黑名单
			Map<String, Object> blackname = userDao.queryBlackById(userid);
			if((blackname == null || blackname.get("display").toString().equals("1")) && status.equals("0")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请勿重复修改");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if((blackname != null && blackname.get("display").toString().equals("0")) && status.equals("1")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请勿重复修改");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			//修改状态
			int count = 0;
			if(status.equals("1")){//封禁
				if(blackname == null){//添加黑名单
					count = userDao.addBlcakName(userid);
				}
				else{//display变为0
					count = userDao.updateBlackName(userid,0);
				}
			}
			else{//解封
				//display变为1
				count = userDao.updateBlackName(userid,1);
			}
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
