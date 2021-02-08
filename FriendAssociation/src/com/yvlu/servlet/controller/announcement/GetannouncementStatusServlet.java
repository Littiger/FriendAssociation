/**
 * 
 */
package com.yvlu.servlet.controller.announcement;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yvlu.dao.controller.register.RegisterDao;
import com.yvlu.tools.tools;

/**
 * @desc 获取公告
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
public class GetannouncementStatusServlet {
	private static RegisterDao registerDao = new RegisterDao();
	public static void Info(HttpServletResponse response) {
		Map<String, Object> info = registerDao.GetannouncementInfo(1);
		if (info ==null || info.size() ==0) {
			tools.print(response, -3, "获取软件信息失败", null);
		}else{
			tools.print(response, 200, "获取成功", JSON.toJSONString(info));
		}
		
	}
}
