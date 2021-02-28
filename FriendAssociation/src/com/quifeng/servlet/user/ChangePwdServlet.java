package com.quifeng.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ndktools.javamd5.Mademd5;
import com.quifeng.dao.login.LoginDao;
import com.quifeng.dao.user.ChangePwdDao;


/**
 * @desc   修改密码
 * @author JZH
 * 
 */
public class ChangePwdServlet {
	ChangePwdDao changePwdDao = new ChangePwdDao();
	LoginDao login = new LoginDao();

	public void changePwd(HttpServletRequest request, HttpServletResponse response) {
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
		String code = request.getParameter("code");
		String pwd = request.getParameter("pwd");
		
		try {
		
			//判空
			if(phone == null || phone.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				return;
			}
			if(code == null || code.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入验证码");
				return;
			}
			if(pwd == null || pwd.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入新密码");
				return;
			}
			//判断用户是否存在
			Map<String, Object> user = changePwdDao.queryUserByPhone(phone);
			if(user == null || user.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "用户 不存在");
				return;
			}
			//判断密码长度
			if(pwd.length() < 6){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "密码不得小于6位");
				return;
			}
			//判断 是否与原密码相同
			String oldPwd = user.get("password").toString();
			//转换为 Md5
			String newPwd = new Mademd5().toMd5(pwd);
			if(oldPwd.equals(newPwd)){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请勿与原密码相同");
				return;
			}
			
			//判断验证码
			// 查询 所有可以使用的code
			List<Map<String, Object>> codeMap = login.queryCodeByU(phone);

			// 这是判断是插入验证码 sql 出现了异常
			if (codeMap == null || codeMap.size() == 0) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "验证码已失效，请重新获取验证码");
				return;
			}
			//判断输入次数  超过7次
			if(Integer.parseInt(codeMap.get(0).get("count").toString()) >= 7){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "验证失败超过6次，请重新获取验证码");
				//删除所有验证码
				login.updateCode(phone);
				return;
			}

			// 有验证码 判断验证码是否正确(遍历所有可使用验证码)
			for (Map<String, Object> map : codeMap) {
				//获取验证码
				String codeTemp = map.get("code").toString();
				
				if(codeTemp.equals(code)){//验证码正确
					//判断验证码是否超时（300秒）
					if(System.currentTimeMillis() - Long.parseLong(map.get("createtime").toString()) > 300000){
						//删除该验证码
						login.updateCode(phone,code);
						jsonObject = new JSONObject();
						jsonObject.put("code", "-1");
						jsonObject.put("msg", "该验证码已超时，请重新获取");
						return;
					}
					//没有超时
					//修改密码
					int count = changePwdDao.updateUserPwd(phone,newPwd);
					//code失效
					login.updateCode(phone);
					if(count != 0){
						jsonObject = new JSONObject();
						jsonObject.put("code", "200");
						jsonObject.put("msg", "修改成功");
						return;
					}
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "修改失败");
					return;
				}
			}
			//验证码全都不符合
			//使用次数+1
			login.uadateCodeByCount(phone);
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "验证码错误");
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
