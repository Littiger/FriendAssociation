package com.quifeng.servlet.circle.topcomment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.circle.CircleDao;

import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.dao.DateUtils;

/**
 * @desc 获取评论--增加表项 ----osfirstid --- 增加评论id 增加 是否点赞
 * @author JZH
 * @time 2020-12-27
 */
@SuppressWarnings("serial")
public class CircleTopcommentServlet extends HttpServlet {
	CircleDao circleDao = new CircleDao();
	TokenDao tokenDao = new TokenDao();

	public void queryOs(HttpServletRequest request, HttpServletResponse response) {
		// 接值
		String token = request.getParameter("token");
		String size = request.getParameter("size");
		String page = request.getParameter("page");
		String postId = request.getParameter("postid");

		Map<String, Object> datamap = new HashMap<String, Object>();
		PrintWriter out = null;
		try {
			// 写入返回的数据
			out = response.getWriter();

			// 验证值是否为空
			if (token == null || token.equals("")) {
				datamap.put("code", "-1");
				datamap.put("msg", "未获取到token，请重新登录");
				out.print(JSON.toJSONString(datamap));
				out.close();
				return;
			}
			// 不传默认为10
			if (size == null || size.equals("")) {
				size = "10";
			}
			// 默认为1
			if (page == null || page.equals("")) {
				page = "1";
			}
			if (postId == null || postId.equals("")) {
				datamap.put("code", "-1");
				datamap.put("msg", "参数不正确");
				out.print(JSON.toJSONString(datamap));
				out.close();
				return;
			}
			// 验证token是否有效
			if (tokenDao.queryToken(token) == null) {
				datamap.put("code", "-2");
				datamap.put("msg", "token失效,请重新登陆");
				out.print(JSON.toJSONString(datamap));
				out.close();
				return;
			}
			// 获取评论
			List<Map<String, Object>> list = circleDao.getOSfir(postId, size, page);
			if (list.size() == 0) {
				datamap.put("code", "-3");
				datamap.put("msg", "已经到底了");
				out.print(JSON.toJSONString(datamap));
				out.close();
				return;
			}

			List<Map<String, Object>> data = new ArrayList<>();

			for (Map<String, Object> map : list) {
				Map<String, Object> postmap = new HashMap<String, Object>();
				Map<String, Object> userinfo = new HashMap<String, Object>();
				// 查看该帖子是否点赞或收藏
				Map<String, Object> zan = circleDao.queryOsZan(postId, map.get("osfirstid").toString(), token);
				userinfo.put("uid", map.get("uid").toString());
				userinfo.put("uname", map.get("username"));
				userinfo.put("useravatar", map.get("useravatar"));
				postmap.put("userinfo", userinfo);
				postmap.put("osfirstid", map.get("osfirstid"));
				// 是否点赞
				if (zan == null) {
					postmap.put("isgreat", false);
				} else {
					postmap.put("isgreat", true);
				}
				postmap.put("placaid", map.get("placaid"));
				postmap.put("postid", map.get("postid"));
				postmap.put("createtime", DateUtils.MillToTime(map.get("createtime") + ""));
				postmap.put("postos", circleDao.getAllOSF(map.get("osfirstid").toString()));
				postmap.put("comment", map.get("ostext"));
				postmap.put("postzan", circleDao.getAllZan(postId));
				data.add(postmap);
			}

			datamap.put("code", "200");
			datamap.put("msg", "获取成功");
			datamap.put("data", data);

			out.print(JSON.toJSONString(datamap));
			System.out.println(datamap);
			out.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			datamap.put("code", "-1");
			datamap.put("msg", "获取异常");
			out.print(JSON.toJSONString(datamap));
			out.close();
			return;
		}
	}
}
