package com.quifeng.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.qiniu.util.Json;
import com.quifeng.dao.user.GetMyNoticerDaoImpl;

/**
 * APi : 获取所有粉丝 URL : /api/info/bean
 * 
 * @param : token
 * 
 * @author 梦伴
 *
 */
public class GetMyNoticerServlet {

	GetMyNoticerDaoImpl GetMyNoticerDaoImpl = new GetMyNoticerDaoImpl();

	/**
	 * 获取所有关注的人
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getMyNoticer(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		// token参数
		String token = request.getParameter("token");

		// 创建 PrintWriter 对象
		PrintWriter out = response.getWriter();
		// 创建返回出错的Map
		Map<String, Object> data = new LinkedHashMap<>();

		// 这里是防止非法调用的
		if (token == null || "".equals(token)) {
			print(out, data, "-5", "非法调用");
			return;
		}

		// 这里是验证token的
		Map<String, Object> userMap = GetMyNoticerDaoImpl.getUserByToken(token);
		if (userMap == null) {
			print(out, data, "-1", "未登录");
			return;
		}

		/**
		 * 通过所有校验 开始获取信息...
		 */

		List<Map<String, Object>> userNoticerinfo = GetMyNoticerDaoImpl
				.getUserNoticerinfo(userMap.get("uid").toString());// 获取粉丝详细信息数组
		data.put("data", userNoticerinfo);

		print(out, data, "200", "获取成功");

	}

	/**
	 * 返回json的封装
	 * 
	 * @param out
	 * @param data
	 * @param coed
	 * @param msg
	 */
	public void print(PrintWriter out, Map<String, Object> data, String coed, String msg) {
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		out.close();
	}

}
