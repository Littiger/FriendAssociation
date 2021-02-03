package com.yvlu.servlet.controller.register;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yvlu.dao.controller.register.RegisterDao;
import com.yvlu.tools.tools;

/**
 * @desc 获取注册信息
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:12:00
 */
public class GetStatusServlet {
	private static RegisterDao registerDao = new RegisterDao();
	public static void Info(String token,HttpServletResponse response) {
		
		Map<String, Object> userinfo = registerDao.getRootByToken(token);
		if(userinfo ==null || userinfo.size() ==0){
			 tools.print(response, -1, "未登录", null);
			 return;
		}else{
			//用戶合法性驗證成功
			Map<String, Object> StatusInfo = registerDao.GetStatus(1);
			if(StatusInfo == null || StatusInfo.size() ==0){
				tools.print(response, -2, "获取应用信息失败", null);
				return;
			}else{
				tools.print(response, 200, "请求成功", JSON.toJSONString(StatusInfo));
			}
			
		}
		
	}
}
