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
import com.tencentcloudapi.trtc.v20190722.models.UserInformation;

/**
 * @Desc http：//127.0.0.1/api/user/userhome   代码怎么没有了 5555555555555
 * @author 语录
 *
 */
public class UserHomeServlet {
	
	UserDao userDao = new UserDao();
	UserHomeDao userHome = new UserHomeDao();
	/**
	 * @Desc 个人主页
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void getUserHome(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//接收值
		String token = request.getParameter("token");
		String userid = request.getParameter("userid");
		
		//创建 PrintWriter 对象
		PrintWriter out = response.getWriter();
		//创建返回出错的Map
		Map<String, Object> data = new LinkedHashMap<>();
				
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
		//查询主页的内容了
		//创建封装数据 map ------》用来存储基本的信息
		Map<String, Object> dataP = new HashMap<String, Object>();
		//获取用户的基本数据
		//存入uid
		dataP.put("userid", userid);
		//存入名字
		dataP.put("username", userTar.get("username"));
		//存入签名  ---------->签名是有默认值得是 --》该用户什么都没有留下哦
		dataP.put("uesrsign", "useras");
		//我是否关注他
		dataP.put("isconcern", userHome.isFixidez(myUid, userid));
		//这里是获取总共获赞的数量
		dataP.put("great", userHome.getUserZanSumBy(userid).get("count"));	
		//这里是获取粉丝数的
		dataP.put("fans", userHome.getUserFixidezCountById(userid).get("count"));
		//这里是获取关注数量的
		dataP.put("concern",userHome.getFixideById(userid).get("count"));
		//这里是获取我发送的帖子的 点进去之后会先获取前20个
		//先创建存数据的list
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		//获取贴子的内容 --------->这里有写好的 以防修改 重新写到了 userHome中-->这里获取第一页20条
		List<Map<String, Object>> postList = userHome.getUserPostById(userid,"1","20"); 
		
		for (Map<String, Object> map : postList) {
			//创建存帖子的map
			Map<String, Object> postMap =  new HashMap<>();
			//创建存user的map
			Map<String, Object> userInfo = new HashMap<>();
			//存用户数据
			userInfo.put("uname", userTar.get("username"));
			userInfo.put("useravatar", userTar.get("useravatar"));
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
			System.out.println(postList);

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
