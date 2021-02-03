/**
 * 
 */
package com.yvlu.servlet.controller.announcement;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.yvlu.dao.controller.register.RegisterDao;
import com.yvlu.tools.tools;

/**
 * @desc 发布公告
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
public class SetannouncementStatusServlet {
	private static RegisterDao registerDao = new RegisterDao();
	public static void Info(String token,String info,HttpServletResponse response) {
		Map<String, Object> userinfo = registerDao.getRootByToken(token);
		if(userinfo ==null || userinfo.size() ==0){
			tools.print(response, -2, "未登录", null);
			return;
		}else{
			int doinfo = registerDao.SetannouncementInfo(1, info);
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
