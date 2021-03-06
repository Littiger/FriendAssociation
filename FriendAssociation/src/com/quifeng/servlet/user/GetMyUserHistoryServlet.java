package com.quifeng.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.circle.CircleDao;
import com.quifeng.dao.user.GetMyUserHistoryDaoImpl;
import com.quifeng.utils.dao.DateUtils;
import com.yvlu.dao.circle.circleDao;

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
	CircleDao circleDao = new CircleDao();

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

		try {
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
			if(historyinfo == null || historyinfo.size() == 0){
				print(out, data, "-1", "已经到底了~");
				return;
			}

			List dataList = new ArrayList<>();

			for (Map<String, Object> map : historyinfo) {

				// 查看该帖子是否点赞或收藏
				Map<String, Object> zanAos = circleDao.queryUserZanAndAos(map.get("postid").toString(), token);
				Map<String, Object> postmap = new HashMap<String, Object>();
				Map<String, Object> userinfo = new HashMap<String, Object>();
				userinfo.put("uname", map.get("username"));
				userinfo.put("uid", map.get("uid"));
				userinfo.put("useravatar", map.get("useravatar"));
				postmap.put("userinfo", userinfo);
				// 是否点赞或收藏
				if (zanAos != null && !zanAos.get("zuid").toString().equals("0")) {
					System.out.println(map.get("zuid"));
					postmap.put("isgreat", true);
				} else {
					postmap.put("isgreat", false);
				}
				if (zanAos != null && !zanAos.get("auid").toString().equals("0")) {
					postmap.put("collect", true);
				} else {
					postmap.put("collect", false);
				}
				// 帖子类型
				// 视频帖
				if (map.get("postvideo") != null) {
					postmap.put("type", 3);
				}
				// 图片帖
				else if (map.get("postimg") != null) {
					postmap.put("type", 2);
				}
				// 文字帖
				else if (map.get("posttext") != null) {
					postmap.put("type", 1);
				}
				// 板块信息
				Map<String, Object> placas = circleDao.queryPlacaById(map.get("placaid").toString());
				Map<String, Object> placa = new HashMap<>();
				placa.put("placaid", map.get("placaid"));
				placa.put("placaname", placas.get("placaname").toString());
				postmap.put("placa", placa);
				postmap.put("postid", map.get("postid"));
				postmap.put("createtime", DateUtils.MillToTime(map.get("createtime").toString()));
				// 判断是否有文本信息
				if (map.get("posttext") == null) {
					postmap.put("posttext", "");
				} else {
					postmap.put("posttext", map.get("posttext"));
				}
				postmap.put("postzan", map.get("postzan"));//点赞数量
				postmap.put("postaos", map.get("postaos"));//收藏数量
				postmap.put("postshare", map.get("postshare"));//分享数量
				postmap.put("postos", map.get("postos"));//评论数量
				postmap.put("postsee", map.get("postsee"));//观看量

				if (map.get("postvideo") != null) {
					postmap.put("postvideo", map.get("postvideo"));
				} else if (map.get("postimg") != null) {
					postmap.put("postimg", map.get("postimg"));
				} else {
					postmap.put("postimg", "");
					postmap.put("postvideo", "");
				}
				dataList.add(postmap);
			}
			data.put("data", dataList);
			print(out, data, "200", "请求成功!");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		if (zanid != null && !zanid.equals("")) {
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
