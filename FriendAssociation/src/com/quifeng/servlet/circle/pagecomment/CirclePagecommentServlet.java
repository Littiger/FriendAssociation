package com.quifeng.servlet.circle.pagecomment;

/**
 * @desc   获取一级评论的评论分页
 * @author JZH
 * @time   2020-12-28
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.circle.CircleDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.dao.DateUtils;

public class CirclePagecommentServlet {
	CircleDao circleDao = new CircleDao();
	TokenDao tokenDao = new TokenDao();

	public void queryPageComment(HttpServletRequest request, HttpServletResponse response) {
		// jsonObj
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		// 接值
		String token = request.getParameter("token");
		String comment = request.getParameter("comment");
		String page = request.getParameter("page");
		String count = request.getParameter("count");
		// 判空
		if (token == null || token.equals("")) {
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "token异常，请重新登录");
			writer.write(jsonObject.toString());
			return;
		}
		if (comment == null || comment.equals("")) {
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "参数异常");
			writer.write(jsonObject.toString());
			return;
		}
		if (page == null || page.equals("")) {
			page = "1";
		}
		if (count == null || count.equals("")) {
			count = "10";
		}
		try {
			if (circleDao.queryFiOsMessageById(comment) == null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "无此评论");
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

			// 获取二级或以上评论
			List<Map<String, Object>> allOs = circleDao.queryOthOs(comment, page, count);
			if (allOs == null || allOs.size() <= 0) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "已经到底了~");
				writer.write(jsonObject.toString());
				return;
			}
			List<Map<String, Object>> data = new ArrayList<>();
			for (Map<String, Object> os : allOs) {
				// 获取当前评论信息
				Map<String, Object> map = new HashMap<>();
				Map<String, Object> userinfo = new HashMap<>();
				userinfo.put("uid", os.get("uid").toString());
				userinfo.put("uname", os.get("username").toString());
				userinfo.put("useravatar", os.get("useravatar").toString());
				map.put("userinfo", userinfo);
				map.put("osfirstid", -Integer.parseInt(os.get("osotherid").toString()));
				// 是否点赞
				System.out.println(circleDao.queryOsZan(-Integer.parseInt(os.get("osotherid").toString()) + "", token));
				if (circleDao.queryOsZan(os.get("osotherid").toString(), token) == null) {
					map.put("isgreat", false);
				} else {
					map.put("isgreat", true);
				}
				map.put("comment", os.get("osothertext"));
				// 获取赞
				if (os.get("count") == null || os.get("count").toString().equals("")) {
					map.put("postzan", "0");
				} else {
					map.put("postzan", os.get("count").toString());
				}
				// 发布时间
				map.put("commenttime", DateUtils.MillToTime(os.get("createtime").toString()));
				// 回复数量
				map.put("postos", circleDao.querySEOs(os.get("osotherid").toString()));

				// 该评论upid是负数 二级评论
				if (Integer.parseInt(os.get("upid").toString()) < 0) {
					map.put("superior", "");
				}
				// upid是正数 二级以上评论
				else {
					// 获取该评论上级id
					String upid = os.get("upid").toString();
					// 获取上级平论信息
					Map<String, Object> upOs = circleDao.queryOthOsById(os.get("upid").toString());
					// 上级评论
					Map<String, Object> superior = new HashMap<>();
					// 用户信息
					Map<String, Object> userinfo2 = new HashMap<>();
					userinfo2.put("uid", upOs.get("uid").toString());
					userinfo2.put("uname", upOs.get("username").toString());
					userinfo2.put("useravatar", upOs.get("useravatar").toString());
					superior.put("userinfo", userinfo2);
					// 评论信息
					superior.put("osfirstid", -Integer.parseInt(upOs.get("osotherid").toString()));
					// 是否点赞
					if (circleDao.queryOsZan(-Integer.parseInt(upOs.get("osotherid").toString()) + "", token) == null) {
						superior.put("isgreat", false);
					} else {
						superior.put("isgreat", true);
					}
					superior.put("comment", upOs.get("osothertext").toString());
					// 获取赞
					if (upOs.get("count") == null || upOs.get("count").toString().equals("")) {
						superior.put("postzan", "0");
					} else {
						superior.put("postzan", upOs.get("count").toString());
					}

					superior.put("commenttime", DateUtils.MillToTime(upOs.get("createtime").toString()));
					superior.put("postos", circleDao.querySEOs(upOs.get("osotherid").toString()));
					map.put("superior", superior);
				}
				data.add(map);
			}

			jsonObject = new JSONObject();
			jsonObject.put("code", "200");
			jsonObject.put("msg", "获取成功");
			jsonObject.put("data", data);
			writer.write(jsonObject.toString());
			return;

		} catch (Exception e) {
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
