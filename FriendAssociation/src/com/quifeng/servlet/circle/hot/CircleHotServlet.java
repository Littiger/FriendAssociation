package com.quifeng.servlet.circle.hot;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.circle.CircleDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.dao.DateUtils;

public class CircleHotServlet {
	
	CircleDao circleDao = new CircleDao();
	TokenDao tokenDao = new TokenDao();
	
	/**
	 * 推荐帖子  校内/校外
	 * @param request
	 * @param response
	 */
	public void queryHot(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("PrintWriter获取异常---->"+e.getMessage());
		}
		//接值
		String hottype = request.getParameter("hottype");
		String token = request.getParameter("token");
		String size = request.getParameter("size");
		//空值判断
		if(hottype == null || hottype.equals("")){
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "未获取到推荐类型");
			writer.write(jsonObject.toString());
			return;
		}
		if(token == null || token.equals("")){
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "未获取到token");
			writer.write(jsonObject.toString());
			return;
		}
		//获取帖子数量  默认值为10
		if(size == null || size.equals("")){
			size="10";
		}
		//推荐类型编号不属于校内、校外
		if(!hottype.equals("1") && !hottype.equals("2")){
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "hottype设置有误");
			writer.write(jsonObject.toString());
			return;
		}
		
		
		try{
			Map<String, Object> user = tokenDao.queryToken(token);
			//判断是否登录
			if(user == null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}
			//获取所有可推荐帖子信息
			List<Map<String, Object>> listPost = circleDao.queryAllPost(hottype,size,token);
			//数据不够用的话
			if(listPost.size()<1){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "达成成就，帖子全部看完了！！");
				writer.write(jsonObject.toString());
				return;
			}
			
			//搜寻前台所需数据
			List<Map<String, Object>> postListArr = new ArrayList<>();
			//获取广告
			if(Math.abs(Math.random()*10) > 7){
				listPost.remove(listPost.size()-1);
				listPost.add(circleDao.queryAllPost2("0","1",token).get(0));
			}
			for (Map<String, Object> mapPostArr : listPost) {
				Map<String, Object> mapPost = new HashMap<>();
				Map<String, Object> mapUser = new HashMap<>();
				//查看该帖子是否点赞或收藏
				Map<String, Object> map = circleDao.queryUserZanAndAos(mapPostArr.get("postid").toString(),token);
				//获取用户信息
				mapUser.put("uname", mapPostArr.get("username").toString());
				mapUser.put("useravatar", mapPostArr.get("useravatar").toString());
				//帖子信息
				mapPost.put("userinfo",mapUser);
				mapPost.put("postid", mapPostArr.get("postid"));
				
				//是否点赞或收藏
				if(map != null && !map.get("zuid").toString().equals("0")){
					System.out.println("zzz"+map.get("zuid").toString());
					mapPost.put("isgreat", true);
				}
				else{
					mapPost.put("isgreat", false);
				}
				if(map != null && !map.get("auid").toString().equals("0")){
					System.out.println("aaa"+map.get("auid").toString());
					mapPost.put("collect", true);
				}
				else{
					mapPost.put("collect", false);
				}
				//帖子类型
				//视频帖
				if(mapPostArr.get("postvideo") != null ){
					mapPost.put("type", 3);
				}
				//图片帖
				else if(mapPostArr.get("postimg") != null ){
					mapPost.put("type", 2);
				}
				//文字帖
				else if(mapPostArr.get("posttext") != null){
					mapPost.put("type", 1);
				}
				//如果有视频只显示视频
				if(mapPostArr.get("postvideo") != null){
					mapPost.put("postvideo", mapPostArr.get("postvideo"));
				}
				else if(mapPostArr.get("postimg") != null){
					mapPost.put("postimg", mapPostArr.get("postimg"));
				}
				else{
					mapPost.put("postimg", "");
					mapPost.put("postvideo", "");
				}
				//板块信息
				Map<String, Object> placas = circleDao.queryPlacaById(mapPostArr.get("placaid").toString());
				Map<String, Object> placa = new HashMap<>();
				placa.put("placaid", mapPostArr.get("placaid"));
				placa.put("placaname", placas.get("placaname").toString());
				mapPost.put("placa", placa);
				mapPost.put("createtime", DateUtils.MillToHourAndMin(mapPostArr.get("createtime").toString()));
				mapPost.put("posttext", mapPostArr.get("posttext"));
				mapPost.put("postzan", mapPostArr.get("postzan"));
				mapPost.put("postaos", mapPostArr.get("postaos"));
				mapPost.put("postshare", mapPostArr.get("postshare"));
				mapPost.put("postos", mapPostArr.get("postos"));
				mapPost.put("postsee", mapPostArr.get("postsee"));
				
				postListArr.add(mapPost);
			}
			//帖子获取完毕
			jsonObject = new JSONObject();
			jsonObject.put("code", "200");
			jsonObject.put("msg", "获取成功");
			jsonObject.put("data", postListArr);
			writer.write(jsonObject.toString());
			return;

		} catch (ClassNotFoundException e) {
			System.out.println("类未找到异常---->"+e.getMessage());
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "类未找到异常");
			writer.write(jsonObject.toString());
			return;
		} catch (SQLException e) {
			System.out.println("SQL异常---->"+e.getMessage());
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "SQL异常");
			writer.write(jsonObject.toString());
			return;
		}
		finally {
			writer.flush();
			writer.close();
		}

	}
	
}
