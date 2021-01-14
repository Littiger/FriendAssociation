package com.quifeng.servlet.search;

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
import com.quifeng.dao.search.searchDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.dao.DateUtils;
/**
 * @desc   获取搜索结果
 * @author JZH
 * @time   2021-01-05
 */

public class GetDataServlet {
	searchDao searchDao = new searchDao();
	TokenDao tokenDao = new TokenDao();
	static CircleDao circleDao = new CircleDao();
	
	public void querySearch(HttpServletRequest request, HttpServletResponse response) {
		//json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		//接值
		String token = request.getParameter("token");
		String wd = request.getParameter("wd");
		String type = request.getParameter("type");
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		try {
			
			//判空
			if(token == null || token.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token获取异常，请重新登录");
				writer.write(jsonObject.toString());
				return;
			}
			//判断是否登录
			if(tokenDao.queryToken(token) == null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}
			//关键词
			if(wd == null || wd.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入搜索词");
				writer.write(jsonObject.toString());
				return;
			}
			//判断类型
			if(type == null || type.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				writer.write(jsonObject.toString());
				return;
			}
			//页数默认为1
			if(page == null || page.equals("")){
				page = "1";
			}
			//长度默认为10
			if(size == null || size.equals("")){
				size = "10";
			}
			//搜索热词库  判断是未找到还是没有更多数据
			String errorMsg = "";
			if(searchDao.queryHotWordByWd(wd) == null){
				errorMsg = "未找到";
			}
			else{
				errorMsg = "没有更多数据了";
			}
			
			//搜索全部
			if(type.equals("all")){
				List<Map<String, Object>> data = new ArrayList();
				//根据内容
				List<Map<String, Object>> contentPostList = null;
				if(size.equals("1") || size.equals("2")){
					contentPostList = searchDao.queryPostByText(wd,page,size);
				}
				else{
					contentPostList = searchDao.queryPostByText(wd,page,Integer.parseInt(Integer.parseInt(size)/3+"")+"");
				}
				
				if(contentPostList != null){
					for (Map<String, Object> map : contentPostList) {
						//获取信息
						//添加
						data.add(getPostMessageByList(map,token,1));
					}
				}
				
				//根据用户
				List<Map<String, Object>> userPostList = null;
				if(size.equals("1") || size.equals("2")){
					userPostList = searchDao.queryUserByName(wd,page,size);
				}
				else{
					userPostList = searchDao.queryUserByName(wd,page,Integer.parseInt(Integer.parseInt(size)/3+"")+"");
				}
				
				if(userPostList != null){
					for (Map<String, Object> map : userPostList) {
						Map<String, Object> map2 = new HashMap<>();
						map2.put("searchdata", 2);
						map2.put("uid", map.get("uid"));
						map2.put("uname", map.get("username").toString());
						map2.put("uavatar", map.get("useravatar").toString());
						//签名
						if(map.get("useras") != null){
							map2.put("usign", map.get("useras").toString());
						}
						else{
							map2.put("usign", "");
						}
						//是否关注
						Map<String, Object> guanZhu = searchDao.queryGuanZhu(token,map.get("uid").toString());
						if(guanZhu == null){
							map2.put("isfocus", false);
						}
						else{
							map2.put("isfocus", true);
						}
						//关注粉丝数
						Map<String, Object> gzMess = searchDao.queryGuanZhu(map.get("uid").toString());
						if(gzMess == null){
							map2.put("ufocus", 0);
							map2.put("ufans", 0);
						}
						else{
							if(gzMess.get("guanzhu") != null || !gzMess.get("guanzhu").toString().equals("")){
								map2.put("ufocus", gzMess.get("guanzhu").toString());
							}
							else{
								map2.put("ufocus", 0);
							}
							if(gzMess.get("fensi") != null || !gzMess.get("fensi").toString().equals("")){
								map2.put("ufans", gzMess.get("fensi").toString());
							}
							else{
								map2.put("ufans", 0);
							}
						}
						data.add(map2);
					}
				}
				//根据圈子
				List<Map<String, Object>> bkPostList  = null;
				if(size.equals("1") || size.equals("2")){
					bkPostList = searchDao.querybkBynName(wd,page,size);
				}
				else{
					bkPostList = searchDao.querybkBynName(wd,page,Integer.parseInt(Integer.parseInt(size)/3+"")+"");
				}
				
				if(bkPostList != null){
					for (Map<String, Object> map : bkPostList) {
						Map<String, Object> map2 = new HashMap<>();
						map2.put("searchdata", 3);
						map2.put("pid", map.get("placaid").toString());
						map2.put("pname", map.get("placaname").toString());
						if(map.get("pinfo") != null){
							map2.put("pinfo", map.get("pinfo").toString());
						}
						else{
							map2.put("pinfo", "暂无描述");
						}
						
						map2.put("puser", map.get("puser")==null ? "0":map.get("puser").toString());
						map2.put("pdynamic", map.get("pdynamic")==null ? "0":map.get("pdynamic").toString());
						//头像
						if(map.get("pavatar") == null){
							map2.put("pavatar", "");
						}
						else{
							map2.put("pavatar", map.get("pavatar").toString());
						}
						data.add(map2);
					}
						
				
				}
				//如果没数据
				if(data == null || data.size() < 1){
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", errorMsg);
					writer.write(jsonObject.toJSONString());
					return;
				}
				System.out.println(data.size());
				//添加搜索记录
				searchDao.addSearchJiLu(wd,token);
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "获取成功");
				jsonObject.put("data", data);
				writer.write(jsonObject.toJSONString());
				return;
				
			}
			//根据用户名搜索
			else if(type.equals("user")){
				List<Map<String, Object>> data = new ArrayList();
				//根据用户
				List<Map<String, Object>> userPostList = searchDao.queryUserByName(wd,page,size);
				if(userPostList != null){
					for (Map<String, Object> map : userPostList) {
						Map<String, Object> map2 = new HashMap<>();
						map2.put("searchdata", 2);
						map2.put("uid", map.get("uid"));
						map2.put("uname", map.get("username").toString());
						map2.put("uavatar", map.get("useravatar").toString());
						//签名
						if(map.get("useras") != null){
							map2.put("usign", map.get("useras").toString());
						}
						else{
							map2.put("usign", "");
						}
						//是否关注
						Map<String, Object> guanZhu = searchDao.queryGuanZhu(token,map.get("uid").toString());
						if(guanZhu == null){
							map2.put("isfocus", false);
						}
						else{
							map2.put("isfocus", true);
						}
						//关注粉丝数
						Map<String, Object> gzMess = searchDao.queryGuanZhu(map.get("uid").toString());
						if(gzMess == null){
							map2.put("ufocus", 0);
							map2.put("ufans", 0);
						}
						else{
							if(gzMess.get("guanzhu") != null || !gzMess.get("guanzhu").toString().equals("")){
								map2.put("ufocus", gzMess.get("guanzhu").toString());
							}
							else{
								map2.put("ufocus", 0);
							}
							if(gzMess.get("fensi") != null || !gzMess.get("fensi").toString().equals("")){
								map2.put("ufans", gzMess.get("fensi").toString());
							}
							else{
								map2.put("ufans", 0);
							}
						}
						data.add(map2);
					}
					//如果没数据
					if(data == null || data.size() < 1){
						jsonObject = new JSONObject();
						jsonObject.put("code", "-1");
						jsonObject.put("msg", errorMsg);
						writer.write(jsonObject.toJSONString());
						return;
					}
					//添加搜索记录
					searchDao.addSearchJiLu(wd,token);
					jsonObject = new JSONObject();
					jsonObject.put("code", "200");
					jsonObject.put("msg", "获取成功");
					jsonObject.put("data", data);
					writer.write(jsonObject.toJSONString());
					return;
					
				}
			}
			//根据动态搜索
			else if(type.equals("dyn")){
				List<Map<String, Object>> data = new ArrayList();
				//根据内容
				List<Map<String, Object>> contentPostList = searchDao.queryPostByText(wd,page,size);
				if(contentPostList != null){
					for (Map<String, Object> map : contentPostList) {
						//获取信息
						//添加
						data.add(getPostMessageByList(map,token,1));
					}
				}
				//如果没数据
				if(data == null || data.size() < 1){
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", errorMsg);
					writer.write(jsonObject.toJSONString());
					return;
				}
				//添加搜索记录
				searchDao.addSearchJiLu(wd,token);
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "获取成功");
				jsonObject.put("data", data);
				writer.write(jsonObject.toJSONString());
				return;
			}
			//根据圈子
			else if(type.equals("cir")){
				List<Map<String, Object>> data = new ArrayList();
				//根据圈子
				List<Map<String, Object>> bkPostList = searchDao.querybkBynName(wd,page,size);
				if(bkPostList != null){
					for (Map<String, Object> map : bkPostList) {
						Map<String, Object> map2 = new HashMap<>();
						map2.put("searchdata", 3);
						map2.put("pid", map.get("placaid").toString());
						map2.put("pname", map.get("placaname").toString());
						if(map.get("pinfo") != null){
							map2.put("pinfo", map.get("pinfo").toString());
						}
						else{
							map2.put("pinfo", "暂无描述");
						}
						
						map2.put("puser", map.get("puser")==null ? "0":map.get("puser").toString());
						map2.put("pdynamic", map.get("pdynamic")==null ? "0":map.get("pdynamic").toString());
						//头像
						if(map.get("pavatar") == null){
							map2.put("pavatar", "");
						}
						else{
							map2.put("pavatar", map.get("pavatar").toString());
						}
						data.add(map2);
					}
				}
				//如果没数据
				if(data == null || data.size() < 1){
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", errorMsg);
					writer.write(jsonObject.toJSONString());
					return;
				}
				//添加搜索记录
				searchDao.addSearchJiLu(wd,token);
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "获取成功");
				jsonObject.put("data", data);
				writer.write(jsonObject.toJSONString());
				return;
			}
			//参数有误
			else{
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				writer.write(jsonObject.toString());
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toJSONString());
			return;
		} finally {
			writer.flush();
			writer.close();
		}
	}
	
	
	/**
	 * 获取帖子信息
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public static Map<String, Object> getPostMessageByList(Map<String, Object> map,String token,int type) throws NumberFormatException, ClassNotFoundException, SQLException{
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("searchdata", type);
		//userinfo
		Map<String, Object> userinfo = new HashMap<>();
		userinfo.put("uname",map.get("username").toString());//发帖人姓名
		userinfo.put("useravatar", map.get("useravatar").toString());//头像
		map2.put("userinfo", userinfo);
		//帖子信息
		map2.put("postid", map.get("postid"));
		//查看该帖子是否点赞或收藏
		Map<String, Object> zan = circleDao.queryUserZanAndAos(map.get("postid").toString(),token);
		//是否点赞
		if(zan != null && !zan.get("zuid").toString().equals("0")){
			map2.put("isgreat", true);
		}
		else{
			map2.put("isgreat", false);
		}
//		//是否收藏
//		if(!zan.get("auid").toString().equals("0")){
//			map2.put("collect", true);
//		}
//		else{
//			map2.put("collect", false);
//		}
		//帖子类型
		//视频帖
		if(map.get("postvideo") != null){
			map2.put("type", 3);
		}
		//图片帖
		else if(map.get("postimg") != null ){
			map2.put("type", 2);
		}
		//文字帖
		else if(map.get("posttext") != null ){
			map2.put("type", 1);
		}
		//如果有视频只显示视频
		if(map.get("postvideo") != null){
			map2.put("postvideo", map.get("postvideo"));
		}
		else if(map.get("postimg") != null){
			map2.put("postimg", map.get("postimg"));
		}
		else{
			map2.put("postimg", "");
			map2.put("postvideo", "");
		}
		//各种信息
		map2.put("createtime", DateUtils.MillToHourAndMin(map.get("createtime").toString()));
		map2.put("posttext", map.get("posttext"));
		map2.put("great", map.get("postzan"));
		//map2.put("postaos", map.get("postaos"));
		map2.put("share", map.get("postshare"));
		map2.put("comment", map.get("postos"));
		map2.put("postsee", map.get("postsee"));
		//板块信息
		Map<String, Object> placa = new HashMap<>();
		placa.put("placaid", map.get("placaid"));
		System.out.println(map.get("placaid").toString());
		placa.put("placaname", map.get("placaname").toString());
		map2.put("placa", placa);
		
		return map2;
	}
	
}
