package com.yvlu.servlet.circle;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.yvlu.dao.circle.circleDao;
import com.yvlu.dao.posts.tokenUtils;

/**
 * @desc   获取所有版块信息
 * @author JZH
 * @time   2021年2月3日
 */
public class getinfoServlet {
	
	circleDao circleDao = new circleDao();
	
	public void getCircleInfo(HttpServletRequest request, HttpServletResponse response) {
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("PrintWriter获取异常");
		}

		try {

			// 取值
			String token = request.getParameter("token");
			// 判空
			if (token == null || token.equals("")) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "非法请求");
				writer.write(jsonObject.toJSONString());
				return;
			}
			// 判断是否登录
			if (tokenUtils.queryToken(token) == null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toJSONString());
				return;
			}
			// 获取信息
			List<Map<String, Object>> circleMessage = circleDao.queryAllCircle();
			if (circleMessage == null || circleMessage.size() < 1) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "暂无圈子");
				writer.write(jsonObject.toJSONString());
				return;
			}
			// 提取信息到json
			// data
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : circleMessage) {
				// 单个圈子
				Map<String, Object> circleMap = new HashMap<String, Object>();
				circleMap.put("value", map.get("placaid").toString());
				// isschool字段是1 校内 否则 校外
				circleMap.put("text",
						map.get("isschool").toString().equals("1") ? 
								"校内-" + map.get("placaname").toString()
								: 
								"校外-" + map.get("placaname").toString());
				// 加到data集合
				data.add(circleMap);
			}
			// 返回json
			jsonObject = new JSONObject();
			jsonObject.put("code", "200");
			jsonObject.put("msg", "请求成功");
			jsonObject.put("data", data);
			writer.write(jsonObject.toJSONString());
			return;

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
