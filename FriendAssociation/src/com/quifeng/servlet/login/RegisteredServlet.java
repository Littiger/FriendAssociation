package com.quifeng.servlet.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.login.LoginDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.dao.user.UserDao;

import com.quifeng.utils.generate.PasswordUtils;
import com.quifeng.utils.generate.TokenUtils;
import com.quifeng.utils.generate.ValiUtils;
import com.quifeng.utils.sms.SMSUtils;
import com.quifeng.utils.state.StateUtils;
import com.quifeng.utils.state.UserType;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

/**
 * @Desc 注册  url:http：//127.0.0.1/api/user/sign
 * @author 语录
 *
 */
public class RegisteredServlet {

	LoginDao login = new LoginDao();
	UserDao userDao = new UserDao();
	TokenDao tokenDao = new TokenDao();
	
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
			if (!ValiUtils.isPhone(phone)) {
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
			
			
			Map<String, Object>  phoneMap = login.isPhone(phone);
			Map<String, Object> user1 = login.getUserByUserName(username);
			
			if (user1!=null) {
				print(out, data, "-2", "用户已经有人注册过了");
				return;
			}
				
			//验证手机号是否注册过
			if (phoneMap!=null) {
				print(out, data, "-2", isPhone(phoneMap,phone));
				return;
			}
			
			//这里是发送验证码
			String code = SMSUtils.createdCode();

			String smsJson=SMSUtils.sendSms(code,new String[]{
				"86"+phone
			});
			System.out.println(smsJson);
			if (smsJson!=null||(!(smsJson.equals("")))) {
				org.json.JSONObject jObject = new org.json.JSONObject(smsJson);
				org.json.JSONObject SendStatusSet = jObject.getJSONArray("SendStatusSet").getJSONObject(0);
				if(SendStatusSet.getString("SerialNo") != null || (!SendStatusSet.getString("SerialNo").equals(""))){
					
					int count = login.addUser(phone, username, userpwd);
					
					if (count>0) {
						try {
				
							Map<String, Object> dataMap = userDao.getUserByPhone(phone);
					
//							//向code表中存入验证码
							login.addCode(dataMap.get("uid").toString(), code, "1");
							print(out, data, "200", "请求成功");
						} catch (Exception e) {
							// TODO: handle exception
							print(out, data, "-5", "插入验证码失败");
						}
					}else {
						print(out, data, "-5", "严重错误");
						return;
					}			
				}
			}else {
				print(out, data, "-1", "验证码发送失败");
			}
			
			//存入数据  设置状态为 注册什么都没有验证  这里是不用生成token的等待手机认证成功验证
			
		} catch (TencentCloudSDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			out.close();
		}	
			
	}
	
	
	/**
	 * @Desc 验证码
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public void signverify(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		//接值
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		PrintWriter out = response.getWriter();
		Map<String, Object> data = new HashMap<String, Object>();
	
		//判断参数是否为null
		if (phone==null||phone.equals("")) {
			print(out, data, "-1", "请输入手机号");
			return;
		}
		if (code==null||code.equals("")) {
			print(out, data, "-1", "请输入验证码");
		}
		 
		//查询 code 判断是第几次 
		Map<String, Object> codeMap=login.queryCodeByU(phone);
	
		//这是判断是插入验证码 sql 出现了异常  
		if (codeMap==null) {
			print(out, data, "-1", "验证码出现了一点小问题，请重新认证");
			return;
		}
		
		//有验证码 判断验证码的值是否为正确
		if (codeMap.get("code").toString().equals(code)) {
			String codeCreateTime =codeMap.get("createtime").toString();
			//没有超过时间
			if (System.currentTimeMillis()-Long.parseLong(codeCreateTime) < 300000) {
				//获取验证码的次数
				int codeCount = new Integer(codeMap.get("count").toString());//获取验证次数
				if (codeCount<=7) {
					//返回值
					Map<String, String> data1 = new HashMap<>();
					String token = TokenUtils.getToken(phone); //获取token
					Map<String, Object> userMap = userDao.getUserByPhone(phone);
					String uid = userMap.get("uid").toString();
				
					//认证成功 将token 插入 log 中
					int isC = tokenDao.addToken(uid, token,System.currentTimeMillis()+"", request.getRemoteAddr().toString());
					
					if (isC>0) {
						data1.put("token", token);
						data.put("data", data1);
						data.put("msg", "认证成功");
						data.put("code", "200");
						out.print(JSON.toJSONString(data));
						out.close();
						//修改用户状态 USERFIVE 没有认证人脸
						StateUtils.queryType(phone, UserType.USERFIVE);
						return;
					}
					else{
						print(out, data, "-5", "插入失败");
						return;
					}
					
				}
				else {
					print(out, data, "-1", "验证码超过六次失效");
					//设置 display=1 删除
					login.updateCode(phone);
					return;
				}
				
			}
			else {
				print(out, data, "-1", "验证码超时请重新获取");
				//先删除
				login.updateCode(phone);
				return;
			}
		}
		else {
			//这里是验证码输入错误的 code +1
			//1. 获取count
			Map<String, Object> codeM=login.queryCodeByU(phone);
			int count = Integer.parseInt(codeM.get("count").toString());
			count++;
			//更新
			login.uadateCodeByCount(codeM.get("codeid").toString(), count+"");
			print(out, data, "-1", "验证码错误，请重新验证");
		}
		
		
		
	};
	
	
	
	
	private String isPhone(Map<String, Object> phoneMap,String phone) {
		Map<String, Object> data = login.isPhone(phone);
		if (data.get("userzt").toString().equals("0")) {
			return "手机号已经注册过了";
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
