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

import com.ndktools.javamd5.Mademd5;
import com.quifeng.dao.login.FaceDao;
import com.quifeng.dao.login.LoginDao;
import com.quifeng.dao.user.SchoolDao;
import com.quifeng.dao.user.UserDao;
import com.quifeng.utils.face.Base64Utils;
import com.quifeng.utils.face.FaceEngineUtils;
import com.quifeng.utils.generate.TokenUtils;
import com.quifeng.utils.state.StateUtils;
import com.quifeng.utils.state.UserType;

/**
 * @Desc 登录使用
 * @author 语录
 *
 */
public class loginServlet {

	
	UserDao userDao =new UserDao();
	LoginDao login = new LoginDao();
	FaceDao faceDao = new FaceDao();
	SchoolDao schoolDao= new  SchoolDao();
	/**
	 * @Desc 登录 http：//127.0.0.1/api/user/login
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public void  login(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String type = request.getParameter("type").trim();
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> dataP = new HashMap<String, Object>();
		
		if (username==null||"".equals(username)) {			
			print(out, data, "-1", "请输入用户名");
			return;
		}
		
		
		Map<String, Object> userMap = userDao.getUserByU(username);
		
		if (userMap==null) {
			print(out, dataP, "-1", "请先进行注册");
			return;
		}
		
		//用户存在
		String userzt = userMap.get("userzt").toString().trim();
		if (userzt.equals("5")) {
			print(out, dataP, "-1", "请先认证人脸");
			return;
		}
		if (userzt.equals("6")) {	
			print(out, dataP, "-1", "请先认证手机号");
			new RegisteredServlet().signverify(request, response);		
			return;
		}

		
		
		//登录
		if (type==null||type.equals("2")) {
			
			if (userzt.equals("6")) {	
				print(out, dataP, "-1", "请先认证手机号");
		
				//这里是获取token
				return;
			}
			
			
			String userpwd = request.getParameter("userpwd");
			System.out.println("userpwd ： " + userpwd);
			System.out.println("userpwd isempty ： " + userpwd != null ? "不为空" : "为空");
			if (userpwd==null||"".equals(userpwd)) {
				print(out, data, "-1", "请输密码");
				return;
			}
			Mademd5 md = new Mademd5();
			userpwd=md.toMd5(userpwd);

			String pwd = userMap.get("password").toString().trim();
			
			if (userpwd.equals(pwd)) {
	
				//这里是更新token的
				String newtoken = TokenUtils.getToken(userMap.get("userphone").toString());
				TokenUtils.updateTokenByU(userMap.get("uid").toString(),newtoken);
				data.put("token", newtoken);
				data.put("state", userMap.get("userzt"));
				dataP.put("data", data);
								
				if (userzt.equals("7")) {
					print(out, dataP, "200", "请先认证学校");
				}
				else {
					print(out, dataP, "200", "登录成功");
				}
				return;
			}else {
				print(out, dataP, "-1", "密码错误");
			}
			
		}
		
		else if (type.equals("1")) {
			String faceBase = request.getParameter("basedata").replace(" ", ""); ;
			if (faceBase==null||"".equals(faceBase)) {
				print(out, dataP, "-5", "非法请求");
				return;
			}
			

			BufferedImage buff = Base64Utils.base642BufferedImage(faceBase);
			//这里是活体检测
			double isprocount = FaceEngineUtils.isPreson(buff);	
			//官网给出是  0.5 
			if (isprocount>0.4) {
				print(out, dataP, "-2", "请您动一下");
				return;
			}
			
//			Mat mat_1=Base64Utils.BufImg2Mat(buff,BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3);
			//查询数据库 
			String uid = userMap.get("uid").toString().trim();
			List<Map<String, Object>> faceList = faceDao.queryFaceByUid(uid);
			if (faceList.size()<8) {
				print(out, dataP, "-1", "请先录入人脸");
				return;
			}
			for (Map<String, Object> map : faceList) {
				//这里是以前写的
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

			//获取数据   buff :是传过来的人脸  	 buff2  : 数据库中存的人脸	 
			String base64f = map.get("facebase").toString();
			BufferedImage buff2 = Base64Utils.base642BufferedImage(base64f);
			//获取相差值
			double countx = FaceEngineUtils.face(buff, buff2);
			
			//这里是判定的  官网给出 0.8 
			if (countx>0.8) {
				String newtoken = TokenUtils.getToken(userMap.get("userphone").toString());  //这是新的token
				TokenUtils.updateTokenByU(userMap.get("uid").toString(),newtoken);  // 这里是修改token
				data.put("token", newtoken);   //返回的token数据
				dataP.put("data", data);

				print(out, dataP, "200", "登录成功");
				return;	
			}else {
				print(out, dataP, "-1", "请再次尝试");
				return;
			}
		
			}
			
		}
		else {
			print(out, dataP, "-5", "非法请求");
		}
		
		
		
	}
	
	
	/** 
	 * @Desc 获取user信息 http：//127.0.0.1/api/user/getuserinfo
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void getUserinfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String token = request.getParameter("token");
		PrintWriter out = response.getWriter();

		Map<String, Object> userMap = userDao.getUserByToken(token);
		
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		if (userMap==null) {
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
	public void loginverify(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String userT = request.getParameter("username");
		PrintWriter out = response.getWriter();
		Map<String, Object> data = new HashMap<String, Object>();

		if (userT==null||userT.trim().equals("")) {
			print(out, data, "-1", "请输入用户名");
			return;
		}
		
		//
		Map<String, Object> userMap = userDao.getUserByPhone(userT);				
		Map<String, Object> dataP = new HashMap<String, Object>();
		
		if (userMap==null) {
			print(out, dataP, "-1", "请先进行注册");
			return;
		}
		 String userzt = userMap.get("userzt").toString().trim();
		data.put("state", userzt);
		data.put("img", userMap.get("useravatar"));
		data.put("sex",userMap.get("usersex"));
		
		
		dataP.put("data", data);
		
		if (userzt.equals("5")) {
			print(out, dataP, "200", "人脸没有录入");
		}
		else if (userzt.equals("6")) {
			print(out, dataP, "200", "手机号没有验证");
		}
		else if (userzt.equals("7")) {
			print(out, dataP, "200", "没有选择学校");
		}else {
			print(out, dataP, "200", "获取成功");

		}
		
		
	}
	
	
	
	/**
	 * @Desc 获取 School
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void getSchool(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<Map<String, Object>> schoolList = schoolDao.getSchool();
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		Map<String, Object> dataP = new HashMap<>();
		PrintWriter out = response.getWriter();

		if (schoolList==null) {
			print(out, dataP, "-1", "请求失败");
			return;
		}
		      
		
		for (Map<String, Object> map : schoolList) {
			Map<String, Object>  everySchool= new HashMap<>();
			everySchool.put("value", map.get("schoolid"));
			everySchool.put("text"
					+ "", map.get("schoolname"));
			data.add(everySchool);
		}
		dataP.put("data", data);
		print(out , dataP, "200", "获取成功");
	}
	
	
	/**
	 * @Desc 对学校的 添加学校信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void setSchool(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String schoolID = request.getParameter("schoolid");
		String token=request.getParameter("token");
		String auth = request.getParameter("auth");	
		
		//创建输出对象
		PrintWriter out = response.getWriter();
		
		Map<String, Object> data = new HashMap<>();
		if (schoolID==null||schoolID.equals("")||auth==null||auth.equals("")) {
			print(out, data, "-5", "非法请求");
			return;
		}
		
		int count = schoolDao.upadateSchool(schoolID, auth, token);
		Map<String, Object> dataP = new HashMap<>();

		data.put("data", dataP);
		if (count>0) {
			//认证学校成功 设置状态为0
			StateUtils.queryType(userDao.getUserByToken(token).get("uid").toString(),UserType.USERZERO );
			print(out, data, "200", "请求成功");
		}
		else{
			print(out, data, "-1", "请求失败");
		}
		
	}
	
	
	
	
	
	
	public void print(PrintWriter out,Map<String, Object> data,String coed,String msg){
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		out.close();
	}
}
