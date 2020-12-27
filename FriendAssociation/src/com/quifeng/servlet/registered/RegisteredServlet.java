package com.quifeng.servlet.registered;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.login.LoginDao;
import com.quifeng.utils.generate.PasswordUtils;
import com.quifeng.utils.generate.ValiUtils;

/**
 * @Desc 注册  url:http：//127.0.0.1/api/user/sign
 * @author 语录
 *
 */
public class RegisteredServlet {

	LoginDao login = new LoginDao();

	/**
	 * @Desc 注册
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void registered(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//接值 为phone username userpwd
		PrintWriter out = response.getWriter();
		String phone = request.getParameter("phone");
		String username = request.getParameter("username");
		String userpwd = request.getParameter("userpwd");
		
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			
			//数据为空验证
			if (username==null||username.equals("")) {
				print(out, data, "-1", "请输入用户名");
				return;
			}
			if (userpwd==null||userpwd.equals("")) {
				print(out, data, "-1", "请输入密码");
				return;
			}
			if (phone==null||phone.equals("")) {
				print(out, data, "-1", "请输入手机号");
				return;
			}
			
			//格式验证
			if (ValiUtils.isPhone(phone)) {
				print(out, data, "-1", "请输入正确格式的手机号");
				return;
			}
			if (ValiUtils.isStrSize(username, 2)) {
				print(out, data, "-1", "请输入三位以上的用户名");
				return;
			}
			if (ValiUtils.isStrSize(userpwd, 5)) {
				print(out, data, "-1", "密码太简单了请输入六位以上的密码");
				return;
			}
			if (PasswordUtils.verify(userpwd, 5)!=null) {
				print(out, data, "-1", PasswordUtils.verify(userpwd, 5));
				return;
			}
			
			//验证手机号是否注册过
			Map<String, Object> phoneMap = login.isPhone(phone);
			if (phoneMap!=null) {
				print(out, data, "-2", isPhone(phoneMap,phone));
			}
			
			//这里是发送验证码
			
			//存入数据  设置状态为 4 注册什么都没有验证  这里是不用生成token的等待手机认证成功验证
			int count = login.addUser(phone, username, userpwd);
			if (count>0) {
				print(out, data, "200", "请求成功");
				return;
			}else {
				print(out, data, "-5", "严重错误");
				return;
			}		
			
		} finally {
			// TODO: handle finally clause
			out.close();
		}	
			
	}
	
	
	/**
	 * @Desc 验证码
	 * @param request
	 * @param response
	 */
	public void signverify(HttpServletRequest request, HttpServletResponse response){
		//接值
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		
	};
	
	
	private String isPhone(Map<String, Object> phoneMap,String phone) {
		Map<String, Object> data = login.isPhone(phone);
		if (data.get("userzt").toString().equals("0")) {
			return "手机号已经注册过了";
		}
		else if (data.get("userzt").toString().equals("4")) {
			return "请您先认证手机号和人脸";
		}
		else if (data.get("userzt").toString().equals("6")) {
			return "请认证人脸";
		}
		else if (data.get("userzt").toString().equals("5")) {
			return "请认证手机号";
		}else {
			return "用户异常";
		}
		
	}

	public void print(PrintWriter out,Map<String, Object> data,String coed,String msg){
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		out.close();
	}
	
}
