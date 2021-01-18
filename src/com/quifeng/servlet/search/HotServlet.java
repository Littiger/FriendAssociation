package com.quifeng.servlet.search;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.search.searchDao;
import com.quifeng.dao.token.TokenDao;

/**
 * @desc 获取热词
 * @author JZH
 * @time 2021-01-05
 */
public class HotServlet {
	searchDao searchDao = new searchDao();
	TokenDao tokenDao = new TokenDao();

	public void getHotWord(HttpServletRequest request, HttpServletResponse response) {
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		// 接值
		String token = request.getParameter("token");
		try {
			// 判空
			if (token == null || token.equals("")) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token获取异常，请重新登录");
				writer.write(jsonObject.toString());
				return;
			}
			// 判断是否登录
			if (tokenDao.queryToken(token) == null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}
			// 查询热词
			List<Map<String, Object>> hotWord = searchDao.queryHotWord();
			if (hotWord != null) {
				List<String> data = new ArrayList<String>();
				// 添加热词
				for (Map<String, Object> map : hotWord) {
					data.add(map.get("word").toString());
				}
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "获取成功");
				jsonObject.put("data", data);
				writer.write(jsonObject.toString());
				return;
			}
			// 没获取到
			else {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "暂无热词");
				writer.write(jsonObject.toJSONString());
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toJSONString());
			return;
		} finally {
			writer.flush();
			writer.close();
		}

	}

}
