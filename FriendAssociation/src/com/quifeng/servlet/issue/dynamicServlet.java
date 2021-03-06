package com.quifeng.servlet.issue;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.quifeng.dao.issue.issueDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.qiniu.putfile;

/**
 * @desc 发布帖子
 * @author JZH
 * @time 2021-01-12
 */
public class dynamicServlet {

	issueDao issueDao = new issueDao();
	TokenDao tokenDao = new TokenDao();

	public void pushPost(HttpServletRequest request, HttpServletResponse response) {
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("PrintWriter获取异常");
		}

		try {

			String token = null;
			String placaid = null;
			String content = null;
			String video = null;
			List image = new ArrayList();// 只能存9张图片
			List<FileItem> imageItems = new ArrayList<>();//图片流

			FileItem videoItem = null;// 存储视频流
			String videoPrifix = "";//视频后缀
			List<String> prifixList = new ArrayList<>();//图片后缀
			List<FileItem> formItemList;
			

			// 将请求消息实体中每一个项目封装成单独的DiskFileItem(FileItem接口的实现)对象的任务
			// 将本次请求的request封装成DiskFileItemFactory对象
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 使用ServletFileUpload解析器上传数据，解析结果返回一个List<FileItem>集合，每一个FileItem对应一个Form表单
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 设定中文处理
			upload.setHeaderEncoding("utf-8");
			formItemList = upload.parseRequest(request);
			//System.out.println("formItemList : " + formItemList);
			if ((formItemList != null) || (formItemList.size() > 0)) {

				// 视频个数索引
				int vCount = 0;

				for (FileItem Item : formItemList) {
					if (!Item.isFormField()) {// 如果不是表单（筛选出文件）
						// 获取文件名字
						String fileName = Item.getName();
						System.out.println("上传文件的名字:" + fileName);
						// 获取后缀
						String prifix = fileName.substring(fileName.lastIndexOf(".") + 1);
						// 后缀全部转小写 防止后缀大小写不统一
						prifix = prifix.toLowerCase();
						//System.out.println("上传文件的后缀:" + prifix);
						// bmp,,png,tif,gif和JPEG
						if (prifix.equals("png") || prifix.equals("jpg") || prifix.equals("bmp") || prifix.equals("tif")
								|| prifix.equals("gif") || prifix.equals("jpeg")) {
							// 仅支持这几种格式的数据

							// 如果超出9张图片上限
							if (imageItems.size() >= 9) {
								jsonObject = new JSONObject();
								jsonObject.put("code", "-1");
								jsonObject.put("msg", "只能上传9张图片哦");
								writer.write(jsonObject.toJSONString());
								return;
							}
							// 添加图片流
							imageItems.add(Item);
							prifixList.add(prifix);
							

						}
						// 视频
						else if (prifix.equals("mp4") || prifix.equals("avi") || prifix.equals("flv")
								|| prifix.equals("wmv")) {
							// 超出视频上传个数
							if (vCount >= 1) {
								jsonObject = new JSONObject();
								jsonObject.put("code", "-1");
								jsonObject.put("msg", "只能上传一个视频哦");
								writer.write(jsonObject.toJSONString());
								return;
							}
							// 添加视频流
							videoItem = Item;
							// 视频后缀
							videoPrifix += prifix;
							
							// 视频个数+1
							vCount++;
							
						} else if (prifix == null || prifix.equals("")){
							continue;
						} else {
							jsonObject = new JSONObject();
							jsonObject.put("code", "-1");
							jsonObject.put("msg", "文件类型不支持");
							writer.write(jsonObject.toJSONString());
							return;
						}
					} else {// 获取其他信息
						if (Item.getFieldName().equals("token")) {
							token = Item.getString("utf-8");
						}
						if (Item.getFieldName().equals("placaid")) {
							placaid = Item.getString("utf-8");
						}
						if (Item.getFieldName().equals("content")) {
							content = Item.getString("utf-8");
						}
					}
				}
			}

			// 获取完信息后
			// 判空
			if (token == null || token.equals("")) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token获取异常，请重新登陆");
				writer.write(jsonObject.toJSONString());
				return;
			}
			// 判断是否登录
			if (tokenDao.queryToken(token) == null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toJSONString());
				return;
			}

			if (placaid == null || placaid.equals("")) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			if (placaid.equals("0")) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				writer.write(jsonObject.toJSONString());
				return;
			}
			// 判断版块是否存在
			if (issueDao.queryplacaById(placaid) == null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "无此版块");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			// 判断是否没输入
			if ((content == null || content.equals("")) && (videoItem == null || videoItem.equals("")) && imageItems.size() < 1) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入");
				writer.write(jsonObject.toJSONString());
				return;
			}
			// 判断是否同时用视频和图片
			if (videoItem != null && imageItems.size() > 0) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "不能同时上传视频和图片");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//限制视频大小
			//设置大小
			int maxSize = 1024*1024*100;
			if(videoItem != null){
				if(videoItem.getSize() > maxSize){
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "视频大小超出100MB");
					writer.write(jsonObject.toJSONString());
					return;
				}
			}
			
			
			
			// 获取自己的id
			String uid = tokenDao.queryUidByToken(token);
			// 判断是否被封禁
			Map<String, Object> user = issueDao.queryUserById(uid);
			if (user != null) {
				if (user.get("userzt").toString().equals("1") || user.get("userzt").toString().equals("2")) {
					jsonObject = new JSONObject();
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "您已被封禁");
					writer.write(jsonObject.toJSONString());
					return;
				}
			} else {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "该用户存在异常");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//查询该用户所在学校是否需要审核
			Map<String, Object> school = issueDao.querySchoolByUid(uid);
			//学校信息异常
			if(school == null || school.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请设置您的学校");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			
			//不为空 开启线程
			if(imageItems != null || videoItem != null){
				System.out.println("================================");
				SendPostThread sendPostThread = 
						new SendPostThread(video, videoItem, videoPrifix, image, imageItems, prifixList, uid, placaid, content, issueDao,school.get("postshenhe").toString());
				Thread thread = new Thread(sendPostThread);
				thread.start();
			}
			
			
			//需要审核
			if(school.get("postshenhe").toString().equals("0")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "发布成功，请等待审核");
				writer.write(jsonObject.toJSONString());
				return;
			}
			else{
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "发布成功,请等待数据传输~");
				writer.write(jsonObject.toJSONString());
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

}
