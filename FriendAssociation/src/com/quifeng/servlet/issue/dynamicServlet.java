package com.quifeng.servlet.issue;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.issue.issueDao;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.qiniu.putfile;

/**
 * @desc   发布帖子
 * @author JZH
 * @time   2021-01-12
 */
public class dynamicServlet {

	issueDao issueDao = new issueDao();
	TokenDao tokenDao= new TokenDao();
	
	public void pushPost(HttpServletRequest request, HttpServletResponse response) {
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
			String placaid = null;
			String content = null;
			String video = null;
			String image = null;
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
							image = putfile.Putimgs(Item.getInputStream(), prifix);
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
			    		if(Item.getFieldName().equals("placaid")){
			    			placaid = Item.getString("utf-8");
			    		}
			    		if(Item.getFieldName().equals("content")){
			    			content = Item.getString("utf-8");
			    		}
			    		if(Item.getFieldName().equals("video")){
			    			content = Item.getString("utf-8");
			    		}
			    		if(Item.getFieldName().equals("content")){
			    			content = Item.getString("utf-8");
			    		}
			    	}
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
