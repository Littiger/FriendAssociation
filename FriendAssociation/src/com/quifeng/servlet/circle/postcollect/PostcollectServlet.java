package com.quifeng.servlet.circle.postcollect;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.circle.CircleDao;
import com.quifeng.dao.token.TokenDao;

/**
 * @desc 帖子收藏
 * @author Littiger
 * @time
 */
@SuppressWarnings("serial")
public class PostcollectServlet extends HttpServlet {

	CircleDao circleDao = new CircleDao();
	TokenDao tokenDao = new TokenDao();

	public void addAos(HttpServletRequest request, HttpServletResponse response) {

		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		// 获取值ֵ
		String token = request.getParameter("token");
		String postid = request.getParameter("postid");

		try {
			if (token == null || token.equals("")) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token获取异常，请重新登陆");
				writer.write(jsonObject.toString());
				return;
			}
			if (postid == null || postid.equals("")) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token获取异常，请重新登陆");
				writer.write(jsonObject.toString());
				return;
			}

			// 判断token
			if (tokenDao.queryToken(token) == null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}

			// 收藏帖子
			circleDao.addPostAos(postid, token);

			// 查询收藏
			Map<String, Object> map = circleDao.queryPostAos(postid, token);
			if (map != null) {
				String display = map.get("display").toString();
				if (display.equals("0")) {
					jsonObject = new JSONObject();
					jsonObject.put("code", "200");
					jsonObject.put("msg", "收藏成功");
					writer.write(jsonObject.toString());
					return;
				} else {
					jsonObject = new JSONObject();
					jsonObject.put("code", "200");
					jsonObject.put("msg", "取消收藏");
					writer.write(jsonObject.toString());
					return;
				}
			} else {
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "收藏失败");
				writer.write(jsonObject.toString());
				return;
			}
		} catch (NumberFormatException | ClassNotFoundException | SQLException | IOException e) {
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toString());
			e.printStackTrace();
			return;
		} finally {
			writer.flush();
			writer.close();
		}

	}

}
