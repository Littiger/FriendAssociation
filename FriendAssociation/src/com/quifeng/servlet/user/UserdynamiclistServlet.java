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
import com.quifeng.dao.user.UserDao;
import com.quifeng.dao.user.UserHomeDao;
import com.quifeng.utils.dao.DateUtils;

/**
 * @Desc  http：//127.0.0.1/api/user/userdynamiclist
 * @author 语录
 *
 */
public class UserdynamiclistServlet {

	UserDao userDao = new UserDao();
	UserHomeDao userHome = new UserHomeDao();
	
	/**
	 * @Desc 对个人主页下滑数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void userdynamiclist(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//接收值
		String token = request.getParameter("token");
		String userid = request.getParameter("userid");
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		//创建 PrintWriter 对象
		PrintWriter out = response.getWriter();
		//创建返回出错的Map
		Map<String, Object> data = new LinkedHashMap<>();
			
		int paeg1 = 0;
		int size1 = 0;
		try {
			paeg1 = Integer.parseInt(page);
			size1 = Integer.parseInt(page);
		} catch (Exception e) {
			// TODO: handle exception
			print(out, data, "-5", "你是来找事的吧");
		}

		if (page==null||page.equals("")||size1<1||paeg1<1) {
			page="1";			
		}
		if (size==null||size.equals("")) {
			size="20";			
		}
			
		//这里是防止非法调用的
		if (userid==null||token==null||userid.equals("")||token.equals("")) {
				print(out, data, "-5", "非法调用");
				return;
		}
		//这里是验证token的
		Map<String, Object> userMap = userDao.getUserByToken(token);
			if (userMap==null) {
				print(out, data, "-1", "未登录");
				return;
			}
		//这里是先获取自己的uid在下面会用到'
		String myUid = userMap.get("uid").toString();
		//查询是否存在 也是防止非法调用 ----------- 可删
		Map<String, Object> userTar = userDao.getUserById(userid);
		//为空接收对象不存在
		if (userTar==null) {
			print(out, data, "-3", "请不要访问外星人的主页");
			return;
		}
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		//获取贴子的内容 --------->这里有写好的 以防修改 重新写到了 userHome中-->这里获取第一页20条
		List<Map<String, Object>> postList = userHome.getUserPostById(userid,page,size); 
		//帖子数据没有了
		if (postList.size()==0) {
			print(out, data, "200", "没有更多的数据了");
			return;
		}
		
		for (Map<String, Object> map : postList) {
			//创建存帖子的map
			Map<String, Object> postMap =  new HashMap<>();
			//创建存user的map
			Map<String, Object> userInfo = new HashMap<>();
			//存用户数据
			userInfo.put("uname", userTar.get("username"));
			userInfo.put("useravatar", userTar.get("useravatar"));
			userInfo.put("uid", userTar.get("uid"));
			//存入帖子的数据
			postMap.put("postid", map.get("postid"));
			//是否点赞
			postMap.put("isgreat", userHome.isZan(map.get("postid").toString(),myUid));
			//贴子赞的数量
			postMap.put("great", userHome.getPostZanCount(map.get("postid").toString()).get("count"));
			//获取评论的数量
			postMap.put("comment", userHome.getPostFiCount(map.get("postid").toString()).get("count"));
			//分享数量
			postMap.put("share", userHome.getShareCount(map.get("postid").toString()).get("count"));
			//帖子的类型
			//获取帖子的
			 
			if (map.get("postimg")!=null) {
				postMap.put("type", "2");
			}
			else if (map.get("postvideo")!=null) {
				postMap.put("type", "3");
			}
			else {
				postMap.put("type", "1");
			}
			//查询板块信息
			//创建板块的map
			Map<String, Object> placaMap = new HashMap<>();
			//获取信息
			Map<String, Object> placa = userHome.getPlacMap(map.get("placaid").toString());
			//获取板块信息
			placaMap.put("placaid",placa.get("placaid"));
			placaMap.put("placaname",placa.get("placaname"));
			postMap.put("placa", placa);
			//获取时间
			postMap.put("createtime", DateUtils.MillToTime(map.get("createtime").toString()));
			//存入内容
			postMap.put("posttext", map.get("posttext"));
			postMap.put("postimg", map.get("postimg"));
			postMap.put("postvideo", map.get("postvideo"));
			//存入数据
			postMap.put("userinfo", userInfo);
			dataList.add(postMap);
		}

		//返回数据
		data.put("data", dataList);
		print(out, data, "200", "请求成功");
		
	}
	
	/**
	 * @Desc 返回json的封装
	 * @param out
	 * @param data
	 * @param coed
	 * @param msg
	 */
	public void print(PrintWriter out,Map<String, Object> data,String coed,String msg){
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		out.close();
	}
}
