package com.quifeng.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.user.ModifyMyUserinfoDaoImpl;

/**
 * APi : 修改个人信息 URL : /api/info/modify
 * 
 * @param : token
 * @param : uname
 * @param : usigntext
 * @param : usex
 * @param : umajor
 * @param : ubir
 * @param : ugraduate
 * 
 * @author 梦伴
 *
 */
public class ModifyMyUserinfoServlet {

	ModifyMyUserinfoDaoImpl modifyMyUserinfoDaoImpl = new ModifyMyUserinfoDaoImpl();

	/**
	 * 修改个人信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void modify(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
		Map<String, Object> userMap = modifyMyUserinfoDaoImpl.getUserByToken(token);
		if (userMap == null) {
			print(out, data, "-1", "未登录");
			return;
		}

		/**
		 * 通过所有校验 开始获取信息...
		 */
		String uname = request.getParameter("uname");// 用户名
		String usigntext = request.getParameter("usigntext");// 个性签名
		String usex = request.getParameter("usex");// 性别
		String umajor = request.getParameter("umajor");// 所选专业
		String ubir = request.getParameter("ubir");// 生日
		String ugraduate = request.getParameter("ugraduate");// 在校状态

		/**
		 * 再次校验
		 */
		if (uname == null || "".equals(uname)) {
			print(out, data, "-5", "非法调用");
			return;
		}
		if (ugraduate == null || "".equals(ugraduate)) {
			print(out, data, "-5", "非法调用");
			return;
		}
		if(uname.length() > 8){
			print(out, data, "-1", "用户名过长");
			return;
		}
		if(usigntext.length() > 12){
			print(out, data, "-1", "个性签名过长");
			return;
		}

		// 修改信息
		int result = modifyMyUserinfoDaoImpl.modify(userMap.get("uid").toString(), uname, usigntext, usex, umajor, ubir,
				ugraduate);
		if (result != 0) {
			print(out, data, "200", "修改成功!");
		}

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
