package com.quifeng.servlet.circle.postshare;

/**
 * @desc   分享帖子
 * @author JZH
 * @time   2020-12-27
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ndktools.javamd5.Mademd5;
import com.quifeng.dao.circle.CircleDao;
import com.quifeng.dao.token.TokenDao;

public class CirclePostshareServlet {
	CircleDao circleDao = new CircleDao();
	TokenDao tokenDao = new TokenDao();

	public void SharePost(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		// 接值
		String token = request.getParameter("token");
		String postid = request.getParameter("postid");
		try {
			// 判空
			if (token == null || token.equals("")) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token异常，请重新登录");
				writer.write(jsonObject.toString());
				return;
			}
			if (postid == null || postid.equals("")) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				writer.write(jsonObject.toString());
				return;
			}
			// 判断token是否有效
			if (tokenDao.queryToken(token) == null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}
			//判断帖子是否可查
			Map<String, Object> post = circleDao.queryPostById(postid);
			if(post == null || post.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "该帖子不可分享");
				writer.write(jsonObject.toString());
				return;
			}
			
			
			// share 表添加数据
			String key = circleDao.addShare(postid, token);
			System.out.println(key);
			
			// postinfo分享列+1
			circleDao.updateShare(postid);

			//生成url
			String url = "www.qiufengvip.top/share.html?id="+key;
			System.out.println(url);
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("url", url);
			
 			jsonObject = new JSONObject();
			jsonObject.put("code", "200");
			jsonObject.put("msg", "分享成功");
			jsonObject.put("data", data);
			writer.write(jsonObject.toString());
			return;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toString());
			return;
		} finally {
			writer.flush();
			writer.close();
		}
	}

}
