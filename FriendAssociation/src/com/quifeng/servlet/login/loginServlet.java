package com.quifeng.servlet.login;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.ndktools.javamd5.Mademd5;
import com.quifeng.dao.login.FaceDao;
import com.quifeng.dao.login.LoginDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.dao.user.SchoolDao;
import com.quifeng.dao.user.UserDao;
import com.quifeng.utils.face.Base64Utils;
import com.quifeng.utils.face.FaceEngineUtils;
import com.quifeng.utils.generate.TokenUtils;
import com.quifeng.utils.sms.SMSUtils;
import com.quifeng.utils.state.StateUtils;
import com.quifeng.utils.state.UserType;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

/**
 * @Desc 登录使用
 * @author 语录
 *
 */
public class loginServlet {

	UserDao userDao = new UserDao();
	LoginDao login = new LoginDao();
	FaceDao faceDao = new FaceDao();
	SchoolDao schoolDao = new SchoolDao();
	TokenDao tokenDao = new TokenDao();

	/**
	 * @Desc 登录 http：//127.0.0.1/api/user/login
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");//手机
		String type = request.getParameter("type");
		System.out.println(username + " --- " + type);
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> dataP = new HashMap<String, Object>();

		if (username == null || "".equals(username)) {
			print(out, data, "-1", "请输入用户名");
			return;
		}

		Map<String, Object> userMap = userDao.getUserByU(username);

		if (userMap == null) {
			print(out, dataP, "-1", "请先进行注册");
			return;
		}

		// 用户存在
		//查询用户token
		Map<String, Object> tokenMap = tokenDao.getTokenByID(userMap.get("uid").toString());
		String token = tokenMap.get("utoken").toString();//用户token
		
		String userzt = userMap.get("userzt").toString().trim();
		if (userzt.equals("5")) {
			data.put("token", token);
			data.put("state", 5);
			dataP.put("data", data);
			print(out, dataP, "200", "请先认证人脸");
			return;
		}
		if (userzt.equals("6")) {
			data.put("token", token);
			data.put("state", 6);
			dataP.put("data", data);
			print(out, dataP, "200", "未验证手机 验证码已发送");
			// 补发手机验证码
			postMessage(username, userMap, dataP);
			return;
		}

		// 登录
		if (type == null || type.equals("2")) {

			if (userzt.equals("6")) {
				data.put("token", token);
				data.put("state", 6);
				dataP.put("data", data);
				print(out, dataP, "200", "请先认证手机号");
				return;
			}

			String userpwd = request.getParameter("userpwd");
			System.out.println("userpwd ： " + userpwd);
			System.out.println("userpwd isempty ： " + userpwd != null ? "不为空" : "为空");
			if (userpwd == null || "".equals(userpwd)) {
				print(out, data, "-1", "请输密码");
				return;
			}
			Mademd5 md = new Mademd5();
			userpwd = md.toMd5(userpwd);

			String pwd = userMap.get("password").toString().trim();

			if (userpwd.equals(pwd)) {

				// 这里是更新token的
				String newtoken = TokenUtils.getToken(userMap.get("userphone").toString());
				TokenUtils.updateTokenByU(userMap.get("uid").toString(), newtoken);
				data.put("token", newtoken);
				data.put("state", userMap.get("userzt"));
				dataP.put("data", data);

				if (userzt.equals("7")) {
					data.put("token", token);
					data.put("state", 7);
					dataP.put("data", data);
					print(out, dataP, "200", "请先认证学校");
				} else {
					print(out, dataP, "200", "登录成功");
				}
				return;
			} else {
				print(out, dataP, "-1", "密码错误");
			}

		}

		else if (type.equals("1")) {
			String faceBase = request.getParameter("basedata");
			if (faceBase == null || "".equals(faceBase)) {
				print(out, dataP, "-5", "非法请求");
				return;
			}

			BufferedImage buff = Base64Utils.base642BufferedImage(faceBase);
			// 这里是活体检测
//			double isprocount = FaceEngineUtils.isPreson(buff);	
//			//官网给出是  0.5 
//			if (isprocount>0.4) {
//				print(out, dataP, "-2", "请您动一下");
//				return;
//			}

//			Mat mat_1=Base64Utils.BufImg2Mat(buff,BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3);
			// 查询数据库
			String uid = userMap.get("uid").toString();
			List<Map<String, Object>> faceList = faceDao.queryFaceByUid(uid);
			System.out.println("faceList.size() : " + faceList.size());
			if (faceList.size() < 8) {
				print(out, dataP, "200", "请先完成录入人脸");
				return;
			}
			System.out.println("人脸数量" + faceList.size());
			for (Map<String, Object> map : faceList) {
				// 这里是以前写的
//				String base64f = map.get("facebase").toString();
//				BufferedImage buff64 = Base64Utils.base642BufferedImage(base64f);
//				Mat mat_0=Base64Utils.BufImg2Mat(buff64,BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3);
//				//比较
//				double countx = FaceUtils.compare_image(mat_0, mat_1);
//				if (countx>0.9) {
//					String newtoken = TokenUtils.getToken(userMap.get("userphone").toString());
//					TokenUtils.updateTokenByU(userMap.get("uid").toString(),newtoken);
//					data.put("token", newtoken);
//					dataP.put("data", data);
//					print(out, dataP, "200", "登录成功");
//					return;
//				}else {
//					print(out, dataP, "-1", "请再次尝试");
//					return;
//				}

				// 获取数据 buff :是传过来的人脸 buff2 : 数据库中存的人脸
				String base64f = map.get("facebase").toString();
				BufferedImage buff2 = Base64Utils.base642BufferedImage(base64f);
				// 获取相差值
				double countx = FaceEngineUtils.face(buff, buff2);
				System.out.println("-----------------");
				System.out.println(userMap);
				// 这里是判定的 官网给出 0.8
				if (countx > 0.8) {
					String newtoken = TokenUtils.getToken(userMap.get("userphone").toString()); // 这是新的token
					TokenUtils.updateTokenByU(userMap.get("uid").toString(), newtoken); // 这里是修改token
					data.put("token", newtoken); // 返回的token数据
					dataP.put("data", data);
					System.out.println("返回" + dataP);
					print(out, dataP, "200", "登录成功");
					return;
				} else {
					print(out, dataP, "-1", "请再次尝试");
					return;
				}

			}

		} else {
			print(out, dataP, "-5", "非法请求");
		}

	}

	/**
	 * @Desc 获取user信息 http：//127.0.0.1/api/user/getuserinfo
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getUserinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getParameter("token");
		PrintWriter out = response.getWriter();

		Map<String, Object> userMap = userDao.getUserByToken(token);

		Map<String, Object> data = new HashMap<String, Object>();

		if (userMap == null) {
			print(out, data, "-1", "未登录");
			return;
		}

		Map<String, Object> dataP = new HashMap<String, Object>();

		dataP.put("code", "200");
		dataP.put("msg", "请求成功");
		data.put("username", userMap.get("username"));
		data.put("usersex", userMap.get("usersex"));
		data.put("phone", userMap.get("userphone"));
		data.put("usersede", userMap.get("usersede"));
		data.put("usersede", userMap.get("email"));
		data.put("usersede", userMap.get("useravatar"));
		dataP.put("data", data);
		out.print(JSON.toJSONString(dataP));

	}

	/**
	 * @Desc 登录验证 http：//127.0.0.1/api/user/loginverify
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void loginverify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userT = request.getParameter("username");
		PrintWriter out = response.getWriter();
		Map<String, Object> data = new HashMap<String, Object>();

		// 校验username参数
		if (userT == null || userT.trim().equals("")) {
			print(out, data, "-1", "请输入用户名");
			return;
		}

		// 通过username获取手机号或邮箱
		Map<String, Object> userMap = userDao.getUserByPhone(userT);
		Map<String, Object> dataP = new HashMap<String, Object>();

		// 查询结果 : 没有查询到时
		if (userMap == null) {
			print(out, dataP, "-1", "请先进行注册");
			return;
		}

		// 查询结果 : 查询到时
		String token = userDao.getUserTokenByUid(userMap.get("uid")).get("utoken").toString();// 通过uid获取用户token
		String phone = userMap.get("userphone").toString();// 获取用户手机号码

		String userzt = userMap.get("userzt").toString().trim();// 获取用户状态
		data.put("state", userzt);// 用户状态
		data.put("img", userMap.get("useravatar"));// 用户头像
		data.put("sex", userMap.get("usersex"));// 用户性别
		data.put("token", token);

		dataP.put("data", data);

		/**
		 * 获取用户状态 5未验证人脸 6未验证手机 7未选择学校
		 */
		if (userzt.equals("5")) {
			print(out, dataP, "200", "人脸没有录入");
		} else if (userzt.equals("6")) {
			// 补发手机验证码
			postMessage(phone, userMap, dataP);
			print(out, dataP, "200", "未验证手机,已补发验证码");
		} else if (userzt.equals("7")) {
			print(out, dataP, "200", "没有选择学校");
		} else {
			print(out, dataP, "200", "获取成功");
		}

	}

	/**
	 * @Desc 获取 School
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getSchool(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Map<String, Object>> schoolList = schoolDao.getSchool();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataP = new HashMap<>();
		PrintWriter out = response.getWriter();

		if (schoolList == null) {
			print(out, dataP, "-1", "请求失败");
			return;
		}

		for (Map<String, Object> map : schoolList) {
			Map<String, Object> everySchool = new HashMap<>();
			everySchool.put("value", map.get("schoolid"));
			everySchool.put("text" + "", map.get("schoolname"));
			data.add(everySchool);
		}
		dataP.put("data", data);
		print(out, dataP, "200", "获取成功");
	}

	/**
	 * @Desc 对学校的 添加学校信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void setSchool(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String schoolID = request.getParameter("schoolid");
		String token = request.getParameter("token");
		String auth = request.getParameter("auth");

		// 创建输出对象
		PrintWriter out = response.getWriter();

		Map<String, Object> data = new HashMap<>();
		if (schoolID == null || schoolID.equals("") || auth == null || auth.equals("")) {
			print(out, data, "-5", "非法请求");
			return;
		}

		int count = schoolDao.upadateSchool(schoolID, auth, token);
		Map<String, Object> dataP = new HashMap<>();

		data.put("data", dataP);
		if (count > 0) {
			// 认证学校成功 设置状态为0
			StateUtils.queryType(userDao.getUserByToken(token).get("uid").toString(), UserType.USERZERO);
			print(out, data, "200", "请求成功");
		} else {
			print(out, data, "-1", "请求失败");
		}

	}

	/**
	 * 补发验证码
	 * 
	 * @param phone   手机号
	 * @param dataMap userMap
	 * @param dataP   dataP对象
	 */
	public void postMessage(String phone, Map<String, Object> dataMap, Map<String, Object> dataP) {
		// 这里是发送验证码
		String code = SMSUtils.createdCode();

		try {
			String smsJson = SMSUtils.sendSms(code, new String[] { "86" + phone });
			System.out.println(smsJson);
			if (smsJson != null || (!(smsJson.equals("")))) {
				org.json.JSONObject jObject = new org.json.JSONObject(smsJson);
				org.json.JSONObject SendStatusSet = jObject.getJSONArray("SendStatusSet").getJSONObject(0);
				if (SendStatusSet.getString("SerialNo") != null || (!SendStatusSet.getString("SerialNo").equals(""))) {
					try {
						// 向code表中存入验证码
						login.addCode(dataMap.get("uid").toString(), code, "1");
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("插入验证码失败");
					}
				}
				dataP.put("postresult", "发送验证码成功");
			} else {
				dataP.put("postresult", "发送验证码失败");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TencentCloudSDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void print(PrintWriter out, Map<String, Object> data, String coed, String msg) {
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		System.out.println(JSON.toJSONString(data));
		out.close(); 
	}
}
