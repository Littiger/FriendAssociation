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

import org.apache.coyote.UpgradeToken;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.alibaba.fastjson.JSON;
import com.ndktools.javamd5.Mademd5;
import com.ndktools.javamd5.core.MD5;
import com.quifeng.dao.login.FaceDao;
import com.quifeng.dao.login.LoginDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.dao.user.SchoolDao;
import com.quifeng.dao.user.UserDao;
import com.quifeng.utils.face.Base64Utils;
import com.quifeng.utils.face.FaceUtils;
import com.quifeng.utils.generate.TokenUtils;

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
		
		if (username==null||username.equals("")) {			
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
		if (userzt.equals("4")) {
			print(out, dataP, "-1", "请先进行验证");
			return;
		}
		if (userzt.equals("5")) {
			print(out, dataP, "-1", "请先认证人脸");
			return;
		}
		if (userzt.equals("6")) {	
			print(out, dataP, "-1", "请先认证手机号");
			return;
		}

		
		
		//登录
		if (type.equals("2")) {
			
			if (userzt.equals("6")) {	
				print(out, dataP, "-1", "请先认证手机号");
				new RegisteredServlet().signverify(request, response);				
				//这里是获取token
				return;
			}
			

			String userpwd = request.getParameter("userpwd");
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
			String faceBase = request.getParameter("basedata");
			if (faceBase.equals("")) {
				print(out, dataP, "-5", "非法请求");
				return;
			}
			
			//转mat
			BufferedImage buff = Base64Utils.base642BufferedImage(faceBase);
			Mat mat_1=Base64Utils.BufImg2Mat(buff,BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3);
			//查询数据库 
			String uid = userMap.get("uid").toString().trim();
			List<Map<String, Object>> faceList = faceDao.queryFaceByUid(uid);
			if (faceList.size()<8) {
				print(out, dataP, "-1", "请先录入人脸");
			}
			for (Map<String, Object> map : faceList) {
				String base64f = map.get("facebase").toString();
				BufferedImage buff64 = Base64Utils.base642BufferedImage(base64f);
				Mat mat_0=Base64Utils.BufImg2Mat(buff64,BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3);
				//比较
				double countx = FaceUtils.compare_image(mat_0, mat_1);
				if (countx>0.9) {
					String newtoken = TokenUtils.getToken(userMap.get("userphone").toString());
					TokenUtils.updateTokenByU(userMap.get("uid").toString(),newtoken);
					data.put("token", newtoken);
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
	 * @Desc 对学校的认证
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void setchool(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String schoolID = request.getParameter("schoolid");
		String token=request.getParameter("token");
		String auth = request.getParameter("auth");	
		PrintWriter out = response.getWriter();
		Map<String, Object> data = new HashMap<>();
		if (schoolID==null||schoolID.equals("")||auth==null||auth.equals("")) {
			print(out, data, "-5", "非法请求");
			return;
		}
		
		int count = schoolDao.upadateSchool(schoolID, auth, token);
		Map<String, Object> schoolMap = schoolDao.getSchoolById(schoolID);
		Map<String, Object> dataP = new HashMap<>();
		dataP.put("name",schoolMap.get("schoolname"));
		dataP.put("sede", schoolMap.get("schoolsede"));
		data.put("data", dataP);
		if (count>0) {
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
