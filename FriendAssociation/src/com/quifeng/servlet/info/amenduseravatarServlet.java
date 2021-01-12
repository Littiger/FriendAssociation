package com.quifeng.servlet.info;
/**
 * @desc   修改头像
 * @author JZH
 * @time   2021-01-12
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.info.infoDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.qiniu.putfile;

public class amenduseravatarServlet {
	infoDao infoDao = new infoDao();
	TokenDao tokenDao = new TokenDao();
	
	public void updateHeadImg(HttpServletRequest request, HttpServletResponse response) {
		//json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("PrintWriter获取异常");
		}
		
		
		try {
			
			String token = null;
			String useravatar = null;
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
							
							// 上传图片 获取url 地址
							useravatar = putfile.Putimgs(Item.getInputStream(), prifix);
							
						}
						
						else{
							jsonObject = new JSONObject();
							jsonObject.put("code", "-1");
							jsonObject.put("msg", "文件类型不支持");
							writer.write(jsonObject.toJSONString());
							return;
						}
			    	}
			    	else{//获取其他信息
			    		if(Item.getFieldName().equals("token")){
			    			token = Item.getString("utf-8");
			    		}
			    	}
			    }
		    }
		    
		    //获取完信息后
			//判空
			if(token == null || token.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "token获取异常，请重新登陆");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//判断是否登录
			if(tokenDao.queryToken(token) == null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(useravatar == null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请选择头像");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//自己的id
			String uid = tokenDao.queryUidByToken(token);
			//修改头像
			int count = infoDao.updateHeadImage(uid,useravatar);
		    if(count > 0){
		    	jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "修改成功");
				writer.write(jsonObject.toJSONString());
				return;
		    }
		    else{
		    	jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "修改失败");
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
