package com.quifeng.servlet.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.chat.ChatDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.dao.user.UserDao;
import com.quifeng.utils.qiniu.SendInfo;
import com.quifeng.utils.qiniu.putfile;

/**
 * @Desc  http：//127.0.0.1/api/chat/send2usermsg
 * @author 语录
 *
 */
public class Send2usermsgServlet {
	
	UserDao userDao = new UserDao();
	ChatDao chatDao = new  ChatDao();
	TokenDao tokenDao = new TokenDao();
	/**
	 * @Desc 发送消息业务逻辑
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws FileUploadException 
	 */
	public void send2Usermsg(HttpServletRequest request, HttpServletResponse response) throws IOException, FileUploadException{
		//接收值
		String token = request.getParameter("token");  //自己的token
		String targetid = request.getParameter("targetid"); //对方的id
		String contenttext = request.getParameter("contenttext"); //发送的内容
		
		
		//返回的PrintWriter对象
		PrintWriter out = response.getWriter();
		//创建返回数据的data
		Map<String, Object> data = new HashMap<String, Object>();
		//验证传递的参数 这里是判断防止非法请求
		if (token==null||targetid==null||token.equals("")||targetid.equals("")) {		
			print(out, data, "-5", "非法请求");
			return;
		}
		//验证token
		Map<String, Object> userMap = userDao.getUserByToken(token);
		//如果其为空则用户没有登录
		if (userMap==null) {
			print(out, data, "-1", "末登录");
			return;
		}
		//查询接收的账户是否存在
		Map<String, Object> userTar = userDao.getUserById(targetid);
		//为空接收对象不存在
		if (userTar==null) {
			print(out, data, "-3", "接收对象不存在");
			return;
		}
		//查询发送的人是否是自己的好友
		
		//获取uid
		String uid = userMap.get("uid").toString();
		//查询是否相互关注
		Map<String, Object> fix1 = chatDao.isFixidez(uid, targetid);
		Map<String, Object> fix2 = chatDao.isFixidez(targetid, uid);
		//不是好友返回数据
		if (fix1==null||fix2==null) {
			print(out, data, "-2", "接收对象不是好友关系");
			return;
		}
		//判断图片的类型 
		//获取 DiskFileItemFactory对象 
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		System.out.println(upload);
//		response.setContentType("text/html;charset=utf-8");
		List<FileItem> formItemList = upload.parseRequest(request);
		
		
		String imgurl ="";		
		if ((formItemList != null) && (formItemList.size() > 0)) {
			for (FileItem Item : formItemList) {
				if (!Item.isFormField()) {
					// 获取上传图片的名字
					String fileName = Item.getName();
					// 获取后缀
					String prifix = fileName.substring(fileName.lastIndexOf(".") + 1);
					// 后缀全部转小写 防止后缀大小写不统一
					prifix = prifix.toLowerCase();
					// bmp,,png,tif,gif和JPEG。
					if (prifix.equals("png") || prifix.equals("jpg") || prifix.equals("bmp") || prifix.equals("tif") || prifix.equals("gif")|| prifix.equals("jpeg")) {
						// 上传文件 获取url 地址
						imgurl = putfile.Putimgs(Item.getInputStream(), prifix);
					}else{
						print(out, data, "-4", "图片类型不支持");
						return;
					}	
				}
				//这里是带图片的
				
				//先判断内容是否为空 为空只发送图片
				if (contenttext.equals("")) {
					//只发送  图片
					//创建返回的对象
					Map<String, Object> dataP = new HashMap<String, Object>();
					//这里的内容存入的是 json  
					Map<String, Object> conMap = new HashMap<>();
					conMap.put("type", "2");
					conMap.put("data",imgurl);
					String chatid = SendInfo.GetUUID();
					conMap.put("chatid", chatid);
					//转为json
					String content = JSON.toJSONString(conMap);
					
					//插入数据
					int count = chatDao.addChat(chatid ,uid, targetid, content, System.currentTimeMillis()+"");
					//发送成功
					if (count>0) {
						//存入类型
						dataP.put("type", "1");
						//存入内容
						dataP.put("data", contenttext);
						//这里是需要id的
						dataP.put("chatid", chatid);
						//存入data
						data.put("data", dataP);
						//调用goesay发送
						SendInfo.pushInfo(targetid, contenttext);
						//向前端返回数据
						print(out, data, "200", "发送成功");
					}else {
						//这里是发送失败的
						print(out, data, "-1", "发送失败");	
					}
				}else {					
					//这里是图片和内容都有的
					Map<String, Object> dataP1 = new HashMap<String, Object>();
					Map<String, Object> dataP2 = new HashMap<String, Object>();
					List<Map<String, Object>> dataCon = new ArrayList<Map<String,Object>>();
					//这里的内容存入的是 json  
					Map<String, Object> conMap1 = new HashMap<>();
					Map<String, Object> conMap2 = new HashMap<>();
					conMap2.put("type", "1");
					conMap1.put("type", "2");
					conMap1.put("data",imgurl);
					conMap2.put("data", contenttext);
					String chatid1 = SendInfo.GetUUID();
					conMap1.put("chatid", chatid1);
					String chatid2 = SendInfo.GetUUID();
					conMap2.put("chatid", chatid2);
					//转为json
					String content1 = JSON.toJSONString(conMap1);
					String content2 = JSON.toJSONString(conMap2);
					//插入数据
					int count = chatDao.addChat(chatid1 ,uid, targetid, content1, System.currentTimeMillis()+"");
					int count1 = chatDao.addChat(chatid2 ,uid, targetid, content2, System.currentTimeMillis()+"");
					//发送成功
					if (count>0&count1>0) {
						//存入类型
						dataP1.put("type", "1");
						dataP2.put("type", "2");
						//存入内容
						dataP1.put("data", contenttext);
						dataP2.put("data", imgurl);
						//这里是需要id的
						dataP1.put("chatid", chatid2);
						dataP1.put("chatid", chatid1);
						//存入data
						dataCon.add(dataP1);
						dataCon.add(dataP2);
						data.put("data", dataCon);
						//调用goesay发送
						SendInfo.pushInfo(targetid, contenttext);
						SendInfo.pushInfo(targetid, imgurl);
						//向前端返回数据
						print(out, data, "200", "发送成功");
					}else {
						//这里是发送失败的
						print(out, data, "-1", "发送失败");	
					}
					
					
					
				}
				

				
			}
		}else {
			//如果为空说明没有图片直接是第二种方式
			//创建返回的对象
			Map<String, Object> dataP = new HashMap<String, Object>();
			//先存入类型是 type=1		
			//这里的内容存入的是 json  
			Map<String, Object> conMap = new HashMap<>();
			conMap.put("type", "1");
			conMap.put("data",contenttext);
			//转为json
			String content = JSON.toJSONString(conMap);
			String chatid = SendInfo.GetUUID();
			//插入数据
			int count = chatDao.addChat(chatid ,uid, targetid, content, System.currentTimeMillis()+"");
			//发送成功
			if (count>0) {
				//存入类型
				dataP.put("type", "1");
				//存入内容
				dataP.put("data", contenttext);
				//这里是需要id的
				dataP.put("chatid", chatid);
				//存入data
				data.put("data", dataP);
				//调用goesay发送
				SendInfo.pushInfo(targetid, contenttext);
				//向前端返回数据
				print(out, data, "200", "发送成功");
			}else {
				//这里是发送失败的
				print(out, data, "-1", "发送失败");
			}
			
			
		}
		
		
		                   
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
