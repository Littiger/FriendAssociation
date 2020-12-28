package com.quifeng.servlet.login;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.log.LogDao;
import com.quifeng.dao.login.FaceDao;
import com.quifeng.dao.login.LoginDao;
import com.quifeng.dao.user.UserDao;
import com.quifeng.utils.face.Base64Utils;
import com.quifeng.utils.face.FacePathUtis;
import com.quifeng.utils.face.ImgeUtise;
import com.quifeng.utils.state.StateUtils;
import com.quifeng.utils.state.UserType;

/**
 * @Desc 注册人脸  登录人脸 版本1.1
 * @Url 1. http：//127.0.0.1/api/user/setface  
 * 		2. http：//127.0.0.1/api/user/login
 * @author 语录
 *
 */
public class FaceServlet {
	
	LoginDao login = new LoginDao();
	UserDao userDao =new UserDao();
	FaceDao faceDao = new FaceDao();
	LogDao log = new LogDao();
	
	
	/**
	 * @Desc 注册成功
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void signFace(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//接值
		String token =request.getParameter("token");
		String basedata =request.getParameter("basedata");
		//写入返回的数据
		PrintWriter out = response.getWriter();
		
		Map<String, Object> data = new HashMap<>();

		// 非空验证
		if (token==null||token.equals("")) {
			print(out, data, "-4", "请先注册");
			return;
		}
		if (basedata==null||basedata.equals("")) {
			print(out, data, "-4", "非法请求");
			return;
		}
		
		Map<String, Object> userMap = userDao.getUserByToken(token);
	
		if (userMap==null) {
			print(out, data, "-1", "请先进行注册");
			return;
		}		
		int userzt = Integer.parseInt(userMap.get("userzt").toString().trim());
		
		if (userzt==0||userzt==1||userzt==3) {
			print(out, data, "-1", "您已经注册过了，请直接登录");
			return;
		}
		if (userzt==2) {
			print(out, data, "-1", "您已被禁封");
			return;
		}
		if (userzt==6) {
			print(out, data, "-1", "请先认证手机号");
			return;
		}
		
		BufferedImage buff_01 = Base64Utils.base642BufferedImage(basedata);
		Mat mat_01 = Base64Utils.BufImg2Mat(buff_01,BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3);

		//如果有人脸
		if (ImgeUtise.imgIsFace(mat_01)) {
		Map<String,Object> dataF = new  HashMap<>();
		dataF.put("code", "200");
		dataF.put("msg", "验证成功");
		data.put("isfase", true);
		data.put("verify", true);	
		
		String uid = userMap.get("uid").toString().trim();
		
		int count = faceDao.queryCountById(uid);
		
		if (count<8) {
			String[] faceJpegPath = ImgeUtise.detectFaceImage(mat_01, FacePathUtis.getPath(request, "face/"));
			
			for (String string : faceJpegPath) {
				String faceBase64 = Base64Utils.GetImgBase(string);
				faceDao.addFace(uid, faceBase64, System.currentTimeMillis()+"");	
			}
			data.put("isover", false);
			dataF.put("data", data);
			out.print(JSON.toJSONString(dataF));
			out.close();
			return;
		}else{
			//有8张后
			log.addUserlog(uid, System.currentTimeMillis()+"", "用户进行了注册", request.getRemoteAddr().toString(), "积分无变化", "普通用户");
			StateUtils.queryType(uid, UserType.USERSEVEN);
			data.put("isover", true);
			dataF.put("data", data);
			out.print(JSON.toJSONString(dataF));
			out.close();
			return;
		}
		
		
		}else {
			print(out, data, "-1", "请将人脸放入框中");
			return;
		}
		
		
	}

	 	
	
	public void print(PrintWriter out,Map<String, Object> data,String coed,String msg){
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		out.close();
	}
	
	public String  getUUID(HttpServletRequest request,String file){
		String facePath = request.getSession().getServletContext().getRealPath(file);	
		File upload = new File(facePath);
		  if (!upload.exists()) {
			  upload.mkdir();
			  
		  }
		return facePath+"1"+".jpeg";
	}

	String getPath(HttpServletRequest request,String file){
		return request.getSession().getServletContext().getRealPath(file) ;	
	}
	
}
