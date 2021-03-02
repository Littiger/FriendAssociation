package com.yvlu.servlet.controller.school;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.yvlu.controller.controller.school.PostShenHeDao;
import com.yvlu.dao.posts.tokenUtils;

/**
 * @desc   控制学校帖子是否需要审核
 * @author JZH
 *
 */
public class PostShenHeServlet {

	PostShenHeDao postShenHeDao = new PostShenHeDao();
	
	public void postShenHe(HttpServletRequest request, HttpServletResponse response) {
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("PrintWriter获取异常");
		}
		
		//接值
		String token = request.getParameter("token");
		String schoolid = request.getParameter("schoolid");
		
		try {
			
			//判空
			if(token == null || token.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "非法请求");
				return;
			}
			if(schoolid == null || schoolid.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				return;
			}
			//判断是否登录
			Map<String, Object> manager = tokenUtils.queryToken(token);
			if(manager == null || manager.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				return;
			}
			
			//判断学校是否存在
			Map<String, Object> school = postShenHeDao.querySchoolById(schoolid);
			if(school == null || school.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "学校不存在");
				return;
			}
			
			//修改审核状态
			//获取学校审核状态
			String postShenHe = school.get("postshenhe").toString();
			if(postShenHe.equals("0")){//当前状态为审核，应改为不审核
				int count = postShenHeDao.updateUnShenHe(schoolid);
				if(count != 0){
					jsonObject = new JSONObject();
					jsonObject.put("code", "200");
					jsonObject.put("msg", "以关闭审核");
					return;
				}
				else{
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "修改失败");
					return;
				}
			}
			else{//当前状态为不审核，应改为审核
				int count = postShenHeDao.updateShenHe(schoolid);
				if(count != 0){
					jsonObject = new JSONObject();
					jsonObject.put("code", "200");
					jsonObject.put("msg", "以开启审核");
					return;
				}
				else{
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "修改失败");
					return;
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toJSONString());
			return;
		} finally {
			writer.write(jsonObject.toJSONString());
			writer.flush();
			writer.close();
		}
	}
	
}
