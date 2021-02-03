/**
 * 
 */
package com.yvlu.servlet.controller.register;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.yvlu.dao.controller.register.RegisterDao;
import com.yvlu.tools.tools;

/**
 * @desc 设置注册权限
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
public class SetStatusServlet {
	private static RegisterDao registerDao = new RegisterDao();
	public static void Info(String token,String status,HttpServletResponse response) {
		Map<String, Object> userinfo = registerDao.getRootByToken(token);
		if(userinfo ==null || userinfo.size() ==0){
			tools.print(response, -2, "未登录", null);
			return;
		}else{
			int doinfo = registerDao.SetStatus(1,status);
			if(doinfo>0){
				tools.print(response, 200, "设置成功", null);
				return;
			}else{
				tools.print(response, -3, "设置失败", null);
				return;
			}
		}
	}
}
