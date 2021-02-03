/**
 * 
 */
package com.yvlu.servlet.controller.school;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.yvlu.dao.controller.register.RegisterDao;
import com.yvlu.tools.tools;

/**
 * @desc 删除学校信息
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
public class DeleteSchoolInfoServlet {
	private static RegisterDao registerDao = new RegisterDao();
	public static void Info(String token,String schoolid,HttpServletResponse response) {
		Map<String, Object> userinfo = registerDao.getRootByToken(token);
		if(userinfo ==null || userinfo.size() ==0){
			tools.print(response, -2, "未登录", null);
			return;
		}else{
			try {
				int doinfo = registerDao.DeleteSchool(Integer.parseInt(schoolid));
				if(doinfo>0){
					tools.print(response, 200, "删除成功", null);
					return;
				}else{
					tools.print(response, -3, "删除失败", null);
					return;
				}
			} catch (Exception e) {
				tools.print(response, -3, "删除失败", null);
				return;
			}
			
		}
	}
}
