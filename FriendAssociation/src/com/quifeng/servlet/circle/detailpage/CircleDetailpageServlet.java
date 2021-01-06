package com.quifeng.servlet.circle.detailpage;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.quifeng.dao.circle.CircleDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DateUtils;
/**
 * @desc   获取一级评论+帖子的详细信息 -- 修改增加了 osfirstid   评论id     是否点赞   是否收藏
 * @author JZH
 * @time   2020-12-27
 */
public class CircleDetailpageServlet {

	CircleDao circleDao = new CircleDao();
	TokenDao tokenDao = new TokenDao();
	
	public void queryPostOs(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			//接值
			String postId = request.getParameter("postId");
			String token = request.getParameter("token");
			//判断空值
			if(postId == null || postId.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "postId未获取到");
				writer.write(jsonObject.toString());
				return;
			}
			if(token == null || token.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token未获取到");
				writer.write(jsonObject.toString());
				return;
			}
			Map<String, Object> user = null;
			try {
				user = tokenDao.queryToken(token);
				//判断是否登录
				if(user == null){
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "未登录");
					writer.write(jsonObject.toString());
					return;
				}
				
				//初始化json
				jsonObject = new JSONObject();
				//获取热评
				List<Map<String, Object>> listHot = circleDao.queryHotOs(postId);
				System.out.println(listHot);
				//获取普通评论
				List<Map<String, Object>> listPt = circleDao.queryPtOs(postId);
				System.out.println(listPt);
				//获取帖子信息
				Map<String, Object> mapPost = circleDao.queryPost(postId);
				if(mapPost == null){
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "帖子获取有误");
					writer.write(jsonObject.toString());
					return;
				}
				//禁止用户点开广告贴
				if(mapPost.get("placaid").toString().equals("0")){
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "广告贴不可以点开哦");
					writer.write(jsonObject.toString());
					return;
				}
				
				
				//查看该帖子是否点赞或收藏
				Map<String, Object> zanAos = circleDao.queryUserZanAndAos(mapPost.get("postid").toString(),token);
				Map<String, Object> detailpage = new HashMap<>();
				Map<String, Object> userinfo = new HashMap<>();
				Map<String, Object> placa = new HashMap<>();
				userinfo.put("uname", mapPost.get("username"));
				userinfo.put("useravatar", mapPost.get("useravatar"));
				detailpage.put("userinfo", userinfo);
				placa.put("placaid", mapPost.get("placaid"));
				placa.put("placaname",mapPost.get("placaname"));
				detailpage.put("placa", placa);
				//是否点赞或收藏
				if(!zanAos.get("zuid").toString().equals("0")){
					detailpage.put("isgreat", true);
				}
				else{
					detailpage.put("isgreat", false);
				}
				if(!zanAos.get("auid").toString().equals("0")){
					detailpage.put("collect", true);
				}
				else{
					detailpage.put("collect", false);
				}
				
				//帖子类型
				//视频帖
				if(mapPost.get("postvideo") != null){
					detailpage.put("type", 3);
				}
				//图片帖
				else if(mapPost.get("postimg") != null ){
					detailpage.put("type", 2);
				}
				//文字帖
				else if(mapPost.get("posttext") != null ){
					detailpage.put("type", 1);
				}
				
				detailpage.put("placaid", mapPost.get("placaid"));
				detailpage.put("createtime", DateUtils.MillToHourAndMin(mapPost.get("createtime").toString()));
				detailpage.put("posttext", mapPost.get("posttext"));
				//详细信息图片视频全部显示
				
				detailpage.put("postvideo", mapPost.get("postvideo"));
				detailpage.put("postimg", mapPost.get("postimg"));
				
				detailpage.put("postzan", mapPost.get("postzan"));
				detailpage.put("postshare", mapPost.get("postshare"));
				detailpage.put("postaos", mapPost.get("postaos"));
				detailpage.put("postos", mapPost.get("postos"));
				detailpage.put("postsee", mapPost.get("postsee"));
				boolean flag = false;
				//无热评
				if(listHot == null || listHot.isEmpty()){
					jsonObject.put("msg", "获取成功,暂无热评");
					flag = true;
				}
				else{
					//添加热评
					List<Map<String, Object>> hotcomments = new ArrayList<Map<String,Object>>();
					for (Map<String, Object> map : listHot) {
						Map<String, Object> map2 = new HashMap<>();
						Map<String,Object> userinfo2 =new HashMap<String, Object>();
						//查看该评论是否被点赞
						Map<String, Object> zan = circleDao.queryOsZan(postId,map.get("osfirstid").toString() ,token);
						userinfo2.put("uname", map.get("username"));
						userinfo2.put("useravatar", map.get("useravatar"));
						map2.put("userinfo", userinfo2);
						map2.put("osfirstid", map.get("osfirstid"));
						map2.put("comment", map.get("ostext"));
						//获取评论点赞数量
						int OsZan = circleDao.queryOsZan(map.get("osfirstid").toString());
						map2.put("oszan", OsZan);
						map2.put("commenttime",com.quifeng.utils.dao.DateUtils.MillToTime(map.get("createtime").toString()));
						//是否点赞
						if(zan == null){
							map2.put("isgreat", false);
						}
						else{
							map2.put("isgreat", true);
						}
						//获取回复评论数量
						try{
							int count = circleDao.queryOsCount(map.get("osfirstid").toString());
							map2.put("postos", count);
						}catch (NullPointerException e) {
							map2.put("postos", 0);
						}
						
						
						hotcomments.add(map2);
					}
					detailpage.put("hotcomments", hotcomments);
				}
				if(listPt == null || listPt.isEmpty()){
					jsonObject.put("msg", "获取成功,暂无评论");
					flag = true;
				}
				else{
					//获取普通评论
					List<Map<String, Object>> genecomments = new ArrayList<Map<String,Object>>();
					for (Map<String, Object> map : listPt) {
						Map<String, Object> map2 = new HashMap<>();
						Map<String,Object> userinfo3 =new HashMap<String, Object>();
						//查看该帖子是否点赞或收藏
						Map<String, Object> zan = circleDao.queryOsZan(postId,map.get("osfirstid").toString() , token);
						userinfo3.put("uname", map.get("username"));
						userinfo3.put("useravatar", map.get("useravatar"));
						map2.put("userinfo", userinfo3);
						map2.put("osfirstid", map.get("osfirstid"));
						map2.put("comment", map.get("ostext"));
						//获取评论点赞数量
						int OsZan = circleDao.queryOsZan(map.get("osfirstid").toString());
						map2.put("oszan", OsZan);
						//是否点赞
						if(zan == null){
							map2.put("isgreat", false);
						}
						else{
							map2.put("isgreat", true);
						}
						map2.put("commenttime", com.quifeng.utils.dao.DateUtils.MillToTime(map.get("createtime").toString()));
						//获取回复评论数量
						try{
							int count = circleDao.queryOsCount(map.get("osfirstid").toString());
							map2.put("postos", count);
						}catch (NullPointerException e) {
							map2.put("postos", 0);
						}
						
						genecomments.add(map2);
					}
					detailpage.put("genecomments", genecomments);
				}
				
				//添加足迹（历史记录）
				//根据token获取id
				String uid = user.get("uid").toString();
				String placaid = mapPost.get("placaid").toString();
				String schoolid = circleDao.queryUserById(uid).get("schoolid").toString();
				circleDao.addHastory(postId,placaid,schoolid,uid);
				//该帖子观看量+1
				circleDao.addPostSee(postId);
				
				jsonObject.put("code", "200");
				if(flag == false){
					jsonObject.put("msg", "获取成功");
				}
				jsonObject.put("data", detailpage);
				String jString = JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
				writer.write(jString);
				return;
			}catch (Exception e) {
				e.printStackTrace();
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "获取失败");
				writer.write(jsonObject.toString());
				return;
			} 
			finally {
				writer.flush();
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "获取失败");
			writer.write(jsonObject.toString());
			return;
		}
	}
	
	
}
