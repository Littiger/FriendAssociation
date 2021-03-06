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
import com.quifeng.dao.user.GetMyPostDaoImpl;
import com.quifeng.utils.dao.DateUtils;

/**
 * APi : 获取我的发帖 URL : /api/info/getmepost
 * 
 * @param : token
 * @param : page
 * @param : size
 * 
 * @author 梦伴
 *
 */
public class GetMyPostServlet {

	GetMyPostDaoImpl GetMyPostDaoImpl = new GetMyPostDaoImpl();
	CircleDao circleDao = new CircleDao();
	
	/**
	 * 获取我的发帖
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getMyPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			Map<String, Object> userMap = GetMyPostDaoImpl.getUserByToken(token);
			if (userMap == null) {
				print(out, data, "-1", "未登录");
				return;
			}

			/**
			 * 通过所有校验 开始获取信息...
			 */

			Map<String, Object> dataList = new HashMap<String, Object>();// data json容器

			Map<String, Object> userinfoz = new HashMap<String, Object>();// userinfo json容器
			String uid = userMap.get("uid").toString();
			userinfoz.put("userid", Integer.valueOf(uid));// userid
			userinfoz.put("username", userMap.get("username").toString());// 发帖人id
			userinfoz.put("uesrsign", userMap.get("useras").toString());// 签名
			userinfoz.put("useravatar", userMap.get("useravatar").toString());// 用户头像
			userinfoz.put("great", GetMyPostDaoImpl.getUserZanSumBy(uid).get("count"));// 获赞
			userinfoz.put("fans", GetMyPostDaoImpl.getUserFixidezCountById(uid).get("count"));// 粉丝
			userinfoz.put("concern", GetMyPostDaoImpl.getUserConcernCount(uid));// 关注

			dataList.put("userinfo", userinfoz);// 存放到 userinfo json对象

			List invitaionList = new ArrayList<>();// invitaion json容器
			List<Map<String, Object>> postinfo = GetMyPostDaoImpl.getMyPost(uid, page, size);// 获取invitaionList数据
			if(postinfo == null || postinfo.size() == 0){
				print(out, data, "-1", "已经到底了~");
				return;
			}

			for (Map<String, Object> map : postinfo) {
				//查看是否点赞或收藏
				Map<String, Object> zanAos = circleDao.queryUserZanAndAos(map.get("postid").toString(), token);
				Map<String, Object> dataP = new HashMap<String, Object>();// invitaionList data json容器

				Map<String, Object> userinfo = new HashMap<String, Object>();// userinfo json容器
				userinfo.put("username", map.get("username").toString());// 发帖人id
				userinfo.put("uid", map.get("uid").toString());// userid
				userinfo.put("useravatar", map.get("useravatar").toString());// 用户头像

				int postid = Integer.valueOf(map.get("postid").toString());// 帖子id
				//boolean isgreat = isGreat(map.get("zanid"));// 是否点赞
				System.out.println(map);
				String postzan = map.get("postzan").toString();// 点赞数量
				String postos = map.get("postos").toString();// 评论数量
				String postshare = map.get("postshare").toString();// 分享数量
				// 帖子类型
				// 视频帖
				if (map.get("postvideo") != null) {
					dataP.put("type", 3);
				}
				// 图片帖
				else if (map.get("postimg") != null) {
					dataP.put("type", 2);
				}
				// 文字帖
				else if (map.get("posttext") != null) {
					dataP.put("type", 1);
				}

				Map<String, Object> placa = new HashMap<String, Object>();// placa json容器
				placa.put("placaid", Integer.valueOf(map.get("placaid").toString()));// 帖子模块 id
				placa.put("placaname", map.get("placaname").toString());// 帖子模块name

				String createtime = DateUtils.MillToTime(map.get("createtime").toString());// 发帖时间
				String posttext = map.get("posttext").toString();// 文本
				String postimg = map.get("postimg") == null ? "null" : map.get("postimg").toString();// 图片地址
				String postvideo = map.get("postvideo") == null ? "null" : map.get("postvideo").toString();// 视频地址

				dataP.put("userinfo", userinfo);
				dataP.put("postid", postid);
				dataP.put("postzan", postzan);
				//dataP.put("type", type);
				//dataP.put("isgreat", isgreat);
				// 是否点赞或收藏
				if (zanAos != null && !zanAos.get("zuid").toString().equals("0")) {
					//System.out.println(zanAos.get("zuid"));
					dataP.put("isgreat", true);
				} else {
					dataP.put("isgreat", false);
				}
				if (zanAos != null && !zanAos.get("auid").toString().equals("0")) {
					dataP.put("collect", true);
				} else {
					dataP.put("collect", false);
				}
				dataP.put("postos", postos);
				dataP.put("postshare", postshare);
				dataP.put("placa", placa);
				dataP.put("createtime", createtime);
				dataP.put("posttext", posttext);
				dataP.put("postimg", postimg);
				dataP.put("postvideo", postvideo);
				invitaionList.add(dataP);
				
			}
			dataList.put("invitaionList", invitaionList);// 存放

			data.put("data", dataList);// 存放
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
