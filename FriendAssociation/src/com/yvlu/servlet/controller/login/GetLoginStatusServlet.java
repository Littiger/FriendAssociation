/**
 * 
 */
package com.yvlu.servlet.controller.login;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yvlu.dao.controller.register.RegisterDao;
import com.yvlu.tools.tools;

/**
 * @desc 获取软件登录权限
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
public class GetLoginStatusServlet {
	private static RegisterDao registerDao = new RegisterDao();
	public static void Info(String token,HttpServletResponse response) {
		Map<String, Object> userinfo = registerDao.getRootByToken(token);
		if(userinfo ==null || userinfo.size() ==0){
			tools.print(response, -2, "登录失效", null);
		}else{
			Map<String, Object> logininfo = registerDao.GetLoginstatus(1);
			if (logininfo ==null || logininfo.size() ==0) {
				tools.print(response, -3, "获取软件信息失效", null);
			}else{
				tools.print(response, 200, "获取成功", JSON.toJSONString(logininfo));
			}
		}
	}
}
