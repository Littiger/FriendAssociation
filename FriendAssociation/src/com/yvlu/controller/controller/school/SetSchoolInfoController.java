/**
 * 
 */
package com.yvlu.controller.controller.school;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bytedeco.javacpp.presets.opencv_core.Str;

import com.yvlu.servlet.controller.school.DeleteSchoolInfoServlet;
import com.yvlu.servlet.controller.school.addSchoolInfoServlet;
import com.yvlu.servlet.controller.update.SetUpdateServlet;
import com.yvlu.servlet.controller.version.SetversionServlet;
import com.yvlu.tools.tools;

/**
 * @desc 写入学校信息
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
@SuppressWarnings("serial")
@WebServlet("/api/controller/school/postinfo")
public class SetSchoolInfoController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		String type = request.getParameter("type");
		String schoolid  = request.getParameter("schoolid");
		String schoolname = request.getParameter("schoolname");
		System.out.println(token);
		System.out.println(type);
		System.out.println(schoolid);
		System.out.println(schoolname);
		if(tools.isnull(token,type)){
			tools.print(response, -1, "参数错误", null);
		}else{
			//新增 
			if ("1".equals(type)) {
				if (tools.isnull(schoolname)) {
					tools.print(response, -1, "参数错误", null);
				}else { //新增-效验成功
					addSchoolInfoServlet.Info(token, schoolname, response);
				}
			}else if("2".equals(type)){
				//删除
				if (tools.isnull(schoolid)) {
					tools.print(response, -1, "参数错误", null);
				}else { //删除-效验成功
					DeleteSchoolInfoServlet.Info(token, schoolid, response);
				}
			}else{
				tools.print(response, -1, "参数值错误", null);
			}
		}
	}
}
