package com.quifeng.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.user.DeleteMyPostDaoImpl;

/**
 * APi : 删除我的帖子 URL : /api/info/getmepost
 * 
 * @param : token
 * @param : postid
 * 
 * @author 梦伴
 *
 */
public class DeleteMyPostServlet {

	DeleteMyPostDaoImpl DeleteMyPostDaoImpl = new DeleteMyPostDaoImpl();

	/**
	 * 删除我的帖子
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteMyPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// token参数
		String token = request.getParameter("token");
		String postid = request.getParameter("postid");

		// 创建 PrintWriter 对象
		PrintWriter out = response.getWriter();
		// 创建返回出错的Map
		Map<String, Object> data = new LinkedHashMap<>();

		// 这里是防止非法调用的
		if (token == null || "".equals(token)) {
			print(out, data, "-5", "非法调用");
			return;
		}
		if (postid == null || "".equals(postid)) {
			print(out, data, "-5", "非法调用");
			return;
		}

		// 这里是验证token的
		Map<String, Object> userMap = DeleteMyPostDaoImpl.getUserByToken(token);
		if (userMap == null) {
			print(out, data, "-1", "未登录");
			return;
		}

		/**
		 * 通过所有校验 开始获取信息...
		 */
		int result = DeleteMyPostDaoImpl.deletePost(userMap.get("uid").toString(), postid);
		if (result != 0) {
			print(out, data, "200", "删除成功");
		} else {
			print(out, data, "-2", "删除失败");
		}

	}

	/**
	 * 判断是否点赞
	 * 
	 * @param zanid
	 * @return
	 */
	private Boolean isGreat(Object zanid) {
		if (zanid != null || !"".equals(zanid)) {
			return true;
		}
		return false;
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
