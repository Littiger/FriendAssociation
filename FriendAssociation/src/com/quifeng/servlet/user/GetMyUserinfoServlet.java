package com.quifeng.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.user.GetMyUserinfoDaoImpl;

/**
 * APi : 获取个人信息 URL : /api/info/getmyuserinfo
 * 
 * @param : token
 * 
 * @author 梦伴
 *
 */
public class GetMyUserinfoServlet {

	GetMyUserinfoDaoImpl getMyUserinfoDaoImpl = new GetMyUserinfoDaoImpl();

	/**
	 * 获取个人信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
		Map<String, Object> userMap = getMyUserinfoDaoImpl.getUserByToken(token);
		if (userMap == null) {
			print(out, data, "-1", "未登录");
			return;
		}

		/**
		 * 通过所有校验 开始获取信息...
		 */

		String uid = isNull(userMap.get("uid"));// 用户id
		String username = isNull(userMap.get("username"));// 用户昵称
		String useras = isNull(userMap.get("useras"));// 个性签名
		String useravatar = isNull(userMap.get("useravatar"));// 用户头像
		String postCount = isNull(getMyUserinfoDaoImpl.getUserPostCount(uid));// 发帖数量
		String fansCount = isNull(getMyUserinfoDaoImpl.getUserFans(uid));// 粉丝数量
		String concernCount = isNull(getMyUserinfoDaoImpl.getUserConcernCount(uid));// 关注数量
		String historyCount = isNull(getMyUserinfoDaoImpl.getUserHistoryCount(uid));// 历史记录数量
		String usersex = isNull(userMap.get("usersex"));// 用户性别
		String usermajor = isNull(userMap.get("usermajor"));// 用户专业
		String userbirth = isNull(userMap.get("userbirth"));// 用户生日
		String auth = isNull(userMap.get("auth"));// 用户是否在校生 毕业生
		String schoolname = isNull(userMap.get("schoolname"));// 用户学校

		// 创建封装数据 map ------》用来存储基本的信息
		Map<String, Object> dataP = new HashMap<String, Object>();
		dataP.put("userid", Integer.valueOf(uid));
		dataP.put("username", username);
		dataP.put("uesrsign", useras);
		dataP.put("useravatar", useravatar);
		dataP.put("dynamic", postCount);
		dataP.put("fans", fansCount);
		dataP.put("concern", concernCount);
		dataP.put("history", historyCount);
		dataP.put("usersex", usersex);
		dataP.put("usermajor", usermajor);
		dataP.put("userbir", userbirth);
		dataP.put("ugraduate", Integer.valueOf(auth));
		dataP.put("uschool", schoolname);

		data.put("data", dataP);
		print(out, data, "200", "获取成功");
	}

	/**
	 * 校验结果是否为空 如果为空返回字符串null
	 */
	private String isNull(Object str) {
		// TODO Auto-generated method stub
		if (str == null || "".equals(str)) {
			return "null";
		}
		return str.toString();
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
