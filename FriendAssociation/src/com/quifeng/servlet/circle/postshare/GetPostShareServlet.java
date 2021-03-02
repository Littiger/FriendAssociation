package com.quifeng.servlet.circle.postshare;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.circle.CircleDao;
import com.quifeng.utils.dao.DateUtils;

/**
 * @desc   查询分享 
 * @author JZH
 * 
 */
public class GetPostShareServlet {
	CircleDao circleDao = new CircleDao();

	public void getPostShare(HttpServletRequest request, HttpServletResponse response) {
		//json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		//接值
		String id = request.getParameter("id");
		
		try {
			//判空
			if(id == null || id.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "非法请求");
				return;
			}
			//帖子信息
			Map<String, Object> postInfo = circleDao.querySharePostInfo(id);
			System.out.println(postInfo);
			if(postInfo == null || postInfo.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "该帖子有误");
				return;
			}
			//分享人信息
			Map<String, Object> shareUserInfo = circleDao.queryUserById(postInfo.get("shareuid").toString());
			System.out.println(shareUserInfo);
			
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String, Object> post = new HashMap<>();
			Map<String, Object> userinfo = new HashMap<>();
			//发帖人用户名
			userinfo.put("uname", postInfo.get("uname").toString());
			//发帖人用户头像
			userinfo.put("useravatar", postInfo.get("useravatar").toString());
			post.put("userinfo", userinfo);
			post.put("postzan", postInfo.get("postzan").toString());//点赞
			post.put("postos", postInfo.get("postos").toString());//评论
			post.put("postshare", postInfo.get("postshare").toString());//分享
			// 帖子类型
			// 视频帖
			if (postInfo.get("postvideo") != null) {
				post.put("type", 3);
			}
			// 图片帖
			else if (postInfo.get("postimg") != null) {
				post.put("type", 2);
			}
			// 文字帖
			else if (postInfo.get("posttext") != null) {
				post.put("type", 1);
			}
			//板块信息
			Map<String, Object> placa = new HashMap<>();
			placa.put("placaid", postInfo.get("placaid").toString());//板块id
			placa.put("placaname", postInfo.get("placaname").toString());//板块名字
			post.put("placa", placa);
			// 判断是否有文本信息
			if (postInfo.get("posttext") == null) {
				post.put("posttext", "");
			} 
			else {
				post.put("posttext", postInfo.get("posttext"));
			}
			//图片视频
			if (postInfo.get("postvideo") != null) {
				post.put("postvideo", postInfo.get("postvideo"));
			} 
			else if (postInfo.get("postimg") != null) {
				post.put("postimg", postInfo.get("postimg"));
			} 
			else {
				post.put("postimg", "");
				post.put("postvideo", "");
			}
			post.put("createtime", DateUtils.MillToTime(postInfo.get("createtime").toString()));//发帖时间
			
			//分享人
			Map<String, Object> user = new HashMap<>();
			user.put("uname", shareUserInfo.get("username").toString());
			user.put("useravatar", shareUserInfo.get("useravatar").toString());
			data.put("post", post);
			data.put("user", user);
			
			jsonObject = new JSONObject();
			jsonObject.put("code", "200");
			jsonObject.put("msg", "获取成功");
			jsonObject.put("data", data);
			return;
				
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toString());
			return;
		} finally {
			writer.write(jsonObject.toString());
			writer.flush();
			writer.close();
		}
	}
}
