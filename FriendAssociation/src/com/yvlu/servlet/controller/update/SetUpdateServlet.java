/**
 * 
 */
package com.yvlu.servlet.controller.update;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import com.quifeng.utils.qiniu.SendInfo;
import com.quifeng.utils.qiniu.putfile;
import com.yvlu.dao.controller.register.RegisterDao;
import com.yvlu.tools.tools;


/**
 * @desc 写入软件更新地址
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
public class SetUpdateServlet {
	private static RegisterDao registerDao = new RegisterDao();
	public static void Info(HttpServletRequest  request,HttpServletResponse response) {
		// 4.0.设定类型和编码
				response.setContentType("text/html;charset=utf-8");
				//声明变量
				List<FileItem> formItemList;// 解析的上传页面的表单元素的结果集合
				// 直白的说就是将本次请求的request封装成DiskFileItemFactory对象
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// 使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
				ServletFileUpload upload = new ServletFileUpload(factory);
				// 设定中文处理
				upload.setHeaderEncoding("utf-8");
				try {
					formItemList = upload.parseRequest(request);
					InputStream FileInputStream = null;
					String token = null;
					// 遍历formItemList
					// .getFieldName() 为表单名称
					// .getString("utf-8") 为获取文本值
					if ((formItemList != null) && (formItemList.size() > 0)) {
						for (FileItem Item : formItemList) {
							if (!Item.isFormField()) {
								// System.out.println("--->"+Item);
								// 获取上传图片的名字
								String fileName = Item.getName();
								System.out.println("上传文件的名字:" + fileName);
								// 获取后缀
								String prifix = fileName.substring(fileName.lastIndexOf(".") + 1);
								// 后缀全部转小写 防止后缀大小写不统一
								prifix = prifix.toLowerCase();
								// 仅支持apk
								if (prifix.equals("apk")) {
									FileInputStream = Item.getInputStream();
									
								}else{
									tools.print(response, -2, "文件后缀异常，暂时仅支持apk格式后缀文件上传", null);
									return;
								}
							} else {
								if (Item.getFieldName().equals("token")) { // 发送人的token
									token = Item.getString("utf-8");
								}
							}
						}
						// 信息获取完毕
						if(token != null && token!="" && FileInputStream !=null){
							ProcessingData(token, FileInputStream, response);
						}else{
							tools.print(response, -4, "参数异常", null);
						}
					}else{
						tools.print(response, -4, "参数异常", null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					tools.print(response, -3, "文件处理异常", null);
				}
		
		
	}
	
	private static void  ProcessingData(String token,InputStream FileInputStream,HttpServletResponse response){
		Map<String, Object> userinfo = registerDao.getRootByToken(token);
		if(userinfo ==null || userinfo.size() ==0){
			tools.print(response, -2, "未登录", null);
			return;
		}else{
			//写入数据库    应用id URl 连接ID
			String url = putfile.Putimgs(FileInputStream, "apk");
			int doinfo = registerDao.SetUpdateInfo(1, url);
			if(doinfo>0){
				tools.print(response, 200, "写入成功", null);
				return;
			}else{
				tools.print(response, -3, "设置失败", null);
				return;
			}
		}
	}
}
