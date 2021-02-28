package com.quifeng.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.login.LoginDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.dao.user.ChangePwdDao;
import com.quifeng.dao.user.UserDao;
import com.quifeng.utils.sms.SMSUtils;
import com.yvlu.dao.posts.tokenUtils;

/**
 * @desc   修改密码，发送验证码
 * @author JZH
 *
 */
public class ChangePwdCodeServlet {

	ChangePwdDao changePwdDao =  new ChangePwdDao();
	TokenDao tokenDao = new TokenDao();
	LoginDao login = new LoginDao();
	UserDao userDao = new UserDao();
	
	public void changePwdCode(HttpServletRequest request, HttpServletResponse response) {
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		
		//接值
		String phone = request.getParameter("phone");
		
		try {
			//判空
			if(phone == null || phone.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				return;
			}
			//判断是否有此用户
			Map<String, Object> user = changePwdDao.queryUserByPhone(phone); 
			System.out.println(user);
			if(user == null || user.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "无此用户");
				return;
			}
			//判断是否封禁
			//uid
			String uid = user.get("uid").toString();
			//判断是否在黑名单
			Map<String, Object> blackUser = changePwdDao.queryBlackUserByUid(uid);
			if(blackUser != null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "您已被封禁");
				return;
			}
			
			//判断无误发送验证码
			//生成验证码
			String code = SMSUtils.createdCode();
			//发送
			String smsJson = SMSUtils.sendSms(code, new String[] { "86" + phone });
			System.out.println(smsJson);//返回的json
			if (smsJson != null || (!(smsJson.equals("")))) {
				//判断是否发送成功
				org.json.JSONObject jObject = new org.json.JSONObject(smsJson);
				org.json.JSONObject SendStatusSet = jObject.getJSONArray("SendStatusSet").getJSONObject(0);
				//解析json后判断里面的SerialNo是否有值
				if (SendStatusSet.getString("SerialNo") != null && (!SendStatusSet.getString("SerialNo").equals(""))) {
					
					//将此手机之前的验证码删除
					login.updateCode(phone);
					//向code表中存入验证码
					login.addCode(user.get("uid").toString(), code, "1");
					
					jsonObject = new JSONObject();
					jsonObject.put("code", "200");
					jsonObject.put("msg", "发送成功");
					return;
				}
			}
			
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请勿频繁发送(当天最多可发送10条)");
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
