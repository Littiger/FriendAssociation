package com.quifeng.servlet.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.user.MarkUserDaoImpl;

/**
 * APi : 关注用户 URL : /api/user/focus
 * 
 * @param : token
 * @param : userid
 * 
 * @author 梦伴
 *
 */
public class MarkUserServlet {

	MarkUserDaoImpl markUserDaoImpl = new MarkUserDaoImpl();

	/**
	 * 关注用户
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void markUser(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		// token参数
		String token = request.getParameter("token");
		String userid = request.getParameter("userid");

		// 创建 PrintWriter 对象
		PrintWriter out = response.getWriter();
		// 创建返回出错的Map
		Map<String, Object> data = new LinkedHashMap<>();

		// 这里是防止非法调用的
		if (token == null || "".equals(token)) {
			print(out, data, "-5", "非法调用");
			return;
		}
		if (userid == null || "".equals(userid)) {
			print(out, data, "-5", "非法调用");
			return;
		}

		// 这里是验证token的
		Map<String, Object> userMap = markUserDaoImpl.getUserByToken(token);
		if (userMap == null) {
			print(out, data, "-1", "未登录");
			return;
		}
		// 这里是验证被关注的uid的
		Map<String, Object> useredMap = markUserDaoImpl.getUserByUid(userid);
		if (useredMap == null) {
			print(out, data, "-2", "请不要关注外星人~");
			return;
		}
		
		/**
		 * 通过所有校验 开始获取信息...
		 */
		
		//先判断是否关注
		Map<String, Object> eachStatusMap = markUserDaoImpl.getEachStatus(userMap.get("uid").toString(),userid);
		if (eachStatusMap == null) {//没有关注
			int markUser = markUserDaoImpl.markUser(userMap.get("uid").toString(),userid);
			if (markUser == 0) {
				print(out, data, "-3", "关注失败");
			}else {
				print(out, data, "200","关注成功" );
			}
		}else {//有关注
			String status = eachStatusMap.get("display").toString();//是否关注 的 状态
			if ("0".equals(status)) {//如果已经互相关注 取消关注
				int markUser = markUserDaoImpl.cancel(userMap.get("uid").toString(),userid,"1");
				if (markUser == 0) {
					print(out, data, "-3", "取消关注失败");
				}else {
					print(out, data, "200","取消关注成功" );
				}
			}else {//如果没有互相关注 关注一下
				int markUser = markUserDaoImpl.cancel(userMap.get("uid").toString(),userid,"0");
				if (markUser == 0) {
					print(out, data, "-3", "关注失败");
				}else {
					print(out, data, "200","关注成功" );
				}
			}
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
