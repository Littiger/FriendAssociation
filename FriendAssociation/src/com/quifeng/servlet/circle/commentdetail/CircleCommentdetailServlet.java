package com.quifeng.servlet.circle.commentdetail;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @desc   获取一级评论的详情
 * @author JZH
 * @time   2020-12-27
 */

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.circle.CircleDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.dao.DateUtils;
public class CircleCommentdetailServlet {
	
	CircleDao circleDao = new CircleDao();
	TokenDao tokenDao = new TokenDao();
	
	public void queryFiOsAndOtherOs(HttpServletRequest request, HttpServletResponse response) {
		//jsonObj
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			 writer = response.getWriter();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		//接值
		String token = request.getParameter("token");
		String comment = request.getParameter("comment");
		//判空
		if(token == null || token.equals("")){
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "token异常，请重新登录");
			writer.write(jsonObject.toString());
			return;
		}
		if(comment == null || comment.equals("")){
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "参数异常");
			writer.write(jsonObject.toString());
			return;
		}
		try {
			
			//判断token
			if(tokenDao.queryToken(token) == null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}
			//获取一级评论信息
			Map<String, Object> FiOsMessage = circleDao.queryFiOsMessageById(comment,token);
			//存储data
			Map<String, Object> data = new HashMap<>();
			if(FiOsMessage != null){
				//存储userinfo
				Map<String, Object> userinfo = new HashMap<>();
				userinfo.put("uname", FiOsMessage.get("username").toString());
				userinfo.put("useravatar", FiOsMessage.get("useravatar").toString());
				data.put("userinfo", userinfo);
				data.put("osfirstid", FiOsMessage.get("osfirstid").toString());
				//是否点赞
				//查看该评论是否被点赞
				Map<String, Object> zan = circleDao.queryOsZan(FiOsMessage.get("osfirstid").toString() ,token);
				if(zan == null){
					data.put("isgreat", false);
				}
				else{
					data.put("isgreat", true);
				}
				//type....
				
				data.put("comment", FiOsMessage.get("ostext").toString());
				data.put("postzan", circleDao.queryOsZan(FiOsMessage.get("osfirstid").toString()));
				data.put("commenttime", DateUtils.MillToTime(FiOsMessage.get("createtime").toString()));
				data.put("postos", circleDao.queryOsCount(FiOsMessage.get("osfirstid").toString()));
			}
			//获取二级或以上评论
			List<Map<String, Object>> allOthOs =circleDao.querySEOthOs(comment);
			for (Map<String, Object> map : allOthOs) {
				//获取所有二级以上评论
				
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toString());
			return;
		}finally {
			writer.flush();
			writer.close();
		}
	}
	
}
