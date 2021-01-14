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
import com.quifeng.dao.user.GetMyUserHistoryDaoImpl;

/**
 * APi : 获取浏览历史记录 URL : /api/info/history
 * 
 * @param : token
 * @param : page
 * @param : size
 * 
 * @author 梦伴
 *
 */
public class GetMyUserHistoryServlet {

	GetMyUserHistoryDaoImpl GetMyUserHistoryDaoImpl = new GetMyUserHistoryDaoImpl();

	/**
	 * 获取浏览历史记录
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getMyUserHistory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// token参数
		String token = request.getParameter("token");
		String page = request.getParameter("page");
		String size = request.getParameter("size");

		// 创建 PrintWriter 对象
		PrintWriter out = response.getWriter();
		// 创建返回出错的Map
		Map<String, Object> data = new LinkedHashMap<>();

		// 这里是防止非法调用的
		if (token == null || "".equals(token)) {
			print(out, data, "-5", "非法调用");
			return;
		}
		if (page == null || "".equals(page)) {
			print(out, data, "-5", "非法调用");
			return;
		}
		if (size == null || "".equals(size)) {
			print(out, data, "-5", "非法调用");
			return;
		}

		// 这里是验证token的
		Map<String, Object> userMap = GetMyUserHistoryDaoImpl.getUserByToken(token);
		if (userMap == null) {
			print(out, data, "-1", "未登录");
			return;
		}

		/**
		 * 通过所有校验 开始获取信息...
		 */
		List<Map<String, Object>> historyinfo = GetMyUserHistoryDaoImpl.getHistoryinfo(userMap.get("uid").toString(),
				page, size);// 获取数据

		List dataList = new ArrayList<>();

		for (Map<String, Object> map : historyinfo) {
			Map<String, Object> dataP = new HashMap<String, Object>();// data json容器
			Map<String, Object> userinfo = new HashMap<String, Object>();// userinfo json容器
			userinfo.put("username", map.get("username").toString());// 发帖人id
			userinfo.put("uid", map.get("uid").toString());// userid
			userinfo.put("useravatar", map.get("useravatar").toString());// 用户头像

			int postid = Integer.valueOf(map.get("postid").toString());// 帖子id
			boolean isgreat = isGreat(map.get("zanid"));// 是否点赞
			String great = map.get("postzan").toString();// 点赞数量
			String comment = map.get("postos").toString();// 评论数量
			String share = map.get("postshare").toString();// 分享数量
			int type = isType(map);// 帖子类型

			Map<String, Object> placa = new HashMap<String, Object>();// placa json容器
			placa.put("placaid", Integer.valueOf(map.get("postbk_placaid").toString()));// 帖子模块 id
			placa.put("placaname", map.get("placaname").toString());// 帖子模块name

			String createtime = map.get("historytime").toString();// 此历史记录访问时间
			String posttext = map.get("posttext").toString();// 文本
			String postimg = map.get("postimg") == null ? "null" : map.get("postimg").toString();// 图片地址
			String postvideo = map.get("postvideo") == null ? "null" : map.get("postvideo").toString();// 视频地址

			dataP.put("userinfo", userinfo);
			dataP.put("postid", postid);
			dataP.put("great", great);
			dataP.put("type", type);
			dataP.put("isgreat", isgreat);
			dataP.put("comment", comment);
			dataP.put("share", share);
			dataP.put("placa", placa);
			dataP.put("createtime", createtime);
			dataP.put("posttext", posttext);
			dataP.put("postimg", postimg);
			dataP.put("postvideo", postvideo);
			dataList.add(dataP);
		}
		data.put("data", dataList);
		print(out, data, "200", "请求成功!");

	}

	/**
	 * 判断帖子类型
	 * 
	 * @param map
	 * @return
	 */
	private int isType(Map<String, Object> map) {
		// TODO Auto-generated method stub

		Object posttext = map.get("posttext");// 帖子文字内容
		Object postimg = map.get("postimg");// 帖子图片内容
		Object postvideo = map.get("postvideo");// 帖子视频内容

		if (!isNull(postvideo)) {// 如果 视频不为空
			return 3;// 视频帖子
		} else if (!isNull(postimg)) {// 如果 图片不为空
			return 2;// 图片帖子
		} else {
			return 1;// 文字帖子
		}
	}

	/**
	 * 判断是否为空
	 */
	private boolean isNull(Object param) {
		if (param == null || "".equals(param)) {
			return true;
		} else {
			return false;
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