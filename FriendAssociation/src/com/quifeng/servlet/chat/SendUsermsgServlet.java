package com.quifeng.servlet.chat;
/**
 * @desc   发送消息 & 发送图片
 * @author JZH
 * @time   2021-01-02
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.chat.ChatDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.qiniu.SendInfo;
import com.quifeng.utils.qiniu.putfile;

public class SendUsermsgServlet {
	
	ChatDao chatDao = new ChatDao();
	TokenDao tokenDao = new TokenDao();
	
	@SuppressWarnings("unused")
	public void sendMessage(HttpServletRequest request, HttpServletResponse response) {
		//json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("PrintWriter获取异常");
		}
		
		try{
			String token = null;
			String targetid = null;
			String contenttext = null;
			String contentpic = null;
			List<FileItem> formItemList;
			//设定类型和编码
			//response.setContentType("text/html;charset=utf-8");
		    

		    //将请求消息实体中每一个项目封装成单独的DiskFileItem(FileItem接口的实现)对象的任务
		    //将本次请求的request封装成DiskFileItemFactory对象
		    DiskFileItemFactory factory = new DiskFileItemFactory();
		    //使用ServletFileUpload解析器上传数据，解析结果返回一个List<FileItem>集合，每一个FileItem对应一个Form表单
		    ServletFileUpload upload = new ServletFileUpload(factory);
		    //设定中文处理
		    upload.setHeaderEncoding("utf-8");
		    formItemList = upload.parseRequest(request);
		    if((formItemList != null) || (formItemList.size() > 0)){
			    for (FileItem Item : formItemList) {
			    	if(!Item.isFormField()){//如果不是表单（筛选出文件）
			    		//获取文件名字
			    		String fileName = Item.getName();
						System.out.println("上传文件的名字:" + fileName);
			    		// 获取后缀
						String prifix = fileName.substring(fileName.lastIndexOf(".") + 1);
						// 后缀全部转小写 防止后缀大小写不统一
						prifix = prifix.toLowerCase();
						System.out.println("上传文件的后缀:" + prifix);
						// bmp,,png,tif,gif和JPEG
						if (prifix.equals("png") || prifix.equals("jpg") || prifix.equals("bmp") || prifix.equals("tif") || prifix.equals("gif")
								|| prifix.equals("jpeg")) {
							// 仅支持这几种格式的数据
							// 上传文件 获取url 地址
							contentpic = putfile.Putimgs(Item.getInputStream(), prifix);
						}
						else{
							jsonObject = new JSONObject();
							jsonObject.put("code", "-4");
							jsonObject.put("msg", "图片类型不支持");
							writer.write(jsonObject.toJSONString());
							return;
						}
			    	}
			    	else{
			    		if(Item.getFieldName().equals("token")){
			    			token = Item.getString("utf-8");
			    		}
			    		if(Item.getFieldName().equals("targetid")){
			    			targetid = Item.getString("utf-8");
			    		}
			    		if(Item.getFieldName().equals("contenttext")){
			    			contenttext = Item.getString("utf-8");
			    		}
			    	}
			    }
		    }
			
		    
		    //判空
		    if(token == null || token.equals("")){
		    	jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token获取异常，请重新登陆");
				writer.write(jsonObject.toJSONString());
				return;
		    }
		    if(tokenDao.queryToken(token) == null){
		    	jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toJSONString());
				return;
		    }
		    if(targetid == null){
		    	jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				writer.write(jsonObject.toJSONString());
				return;
		    }
		    
		    if(chatDao.queryUserById(targetid) == null){
		    	jsonObject = new JSONObject();
				jsonObject.put("code", "-3");
				jsonObject.put("msg", "接受对象不存在");
				writer.write(jsonObject.toJSONString());
				return;
		    }
		    String uid = tokenDao.queryUidByToken(token);
		    //不能发给自己
		    if(uid.equals(targetid)){
		    	jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请勿给自己发消息");
				writer.write(jsonObject.toJSONString());
				return;
		    }
		    if(chatDao.queryFixById(uid, targetid).size() < 2){//不是互相关注
		    	jsonObject = new JSONObject();
				jsonObject.put("code", "-2");
				jsonObject.put("msg", "接受对象不是好友关系");
				writer.write(jsonObject.toJSONString());
				return;
		    }
		    //接收完数据   判断发送的类型
		    if(contentpic == null && contenttext == null){
		    	if(contentpic.equals("") && contenttext.equals("")){
			    	jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "消息为空");
					writer.write(jsonObject.toJSONString());
					return;
		    	}
		    }
		    //只有文本消息
		    if(contentpic == null || contentpic.equals("")){
		    	if (contenttext != null && !contenttext.equals("")){
		    	
			    	List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			    	//打印消息
			    	Map<String, Object> news = new HashMap<String, Object>();
			    	
			    	news.put("data", contenttext);
			    	news.put("type", 1);
			    	JSONObject messageJson = new JSONObject();
			    	messageJson.put("data", contenttext);
			    	messageJson.put("type", 1);
			    	//存入数据库
			    	Map<String, Object> newsTemp = chatDao.addMess(uid,targetid,messageJson.toString());
			    	news.put("chatid", newsTemp.get("chatid").toString());
			    	data.add(news);
			    	//发送消息
			    	JSONObject js = new JSONObject();
			    	js.put("data", data);
			    	SendInfo.pushInfo(targetid,js.toString());
			    	
			    	jsonObject = new JSONObject();
			    	jsonObject.put("code", "200");
			    	jsonObject.put("msg", "发送成功");
			    	jsonObject.put("data", data);
			    	writer.write(jsonObject.toJSONString());
			    	return;
		    	}
		    	else{
		    		jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "消息为空");
					writer.write(jsonObject.toJSONString());
					return;
		    	}
		    }
		    //只有图片
		    if(contentpic != null && !contentpic.equals("")) {
		    	if (contenttext == null || contenttext.equals("")) {
		    	
			    	List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			    	//打印消息
			    	Map<String, Object> news = new HashMap<String, Object>();
			    
			    	news.put("data", contentpic.toString());
			    	news.put("type", 2);
			    	JSONObject messageJson = new JSONObject();
			    	messageJson.put("data", contentpic.toString());
			    	messageJson.put("type", 2);
			    	//存入数据库
			    	Map<String, Object> newsTemp = chatDao.addMess(uid,targetid,messageJson.toString());
			    	news.put("chatid", newsTemp.get("chatid").toString());
			    	data.add(news);
			    	//发送消息
			    	JSONObject js = new JSONObject();
			    	js.put("data", data);
			    	SendInfo.pushInfo(targetid,js.toString());
			    	
			    	jsonObject = new JSONObject();
			    	jsonObject.put("code", "200");
			    	jsonObject.put("msg", "发送成功");
			    	jsonObject.put("data", data);
			    	writer.write(jsonObject.toJSONString());
			    	return;
		    	}
		    }
		    //既有图片也有文本
		    if(contentpic != null && !contentpic.equals("")){
		    	if (contenttext != null && !contenttext.equals("")) {
		    
			    	List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			    	
			    	//打印消息
			    	Map<String, Object> img = new HashMap<String, Object>();
			    	img.put("data",  contentpic.toString());
			    	img.put("type", 2);
			    	JSONObject messageJson = new JSONObject();
			    	messageJson.put("data", contentpic.toString());
			    	messageJson.put("type", 2);
			    	
			    	Map<String, Object> text = new HashMap<String, Object>();
			    	text.put("data",  contenttext.toString());
			    	text.put("type", 1);
			    	JSONObject messageJson2 = new JSONObject();
			    	messageJson.put("data", contenttext.toString());
			    	messageJson.put("type", 1);
			    	
			    	//存入数据库
			    	Map<String, Object> newsImg = chatDao.addMess(uid,targetid,messageJson.toString());
			    	Map<String, Object> newsText = chatDao.addMess(uid,targetid,messageJson2.toString());
			    	//获取id
			    	img.put("chatid", newsImg.get("chatid").toString());
			    	text.put("chatid", newsImg.get("chatid").toString());
			    	
			    	data.add(img);
			    	data.add(text);
			    	//发送消息
			    	JSONObject js = new JSONObject();
			    	js.put("data", data);
			    	SendInfo.pushInfo(targetid,js.toJSONString());
			    	
			    	jsonObject = new JSONObject();
			    	jsonObject.put("code", "200");
			    	jsonObject.put("msg", "发送成功");
			    	jsonObject.put("data", data);
			    	writer.write(jsonObject.toJSONString());
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
			writer.flush();
			writer.close();
		}
	}
	
	
}
