/**
 * 
 */
package com.yvlu.controller.controller.update;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yvlu.servlet.controller.update.SetUpdateServlet;
import com.yvlu.servlet.controller.version.SetversionServlet;
import com.yvlu.tools.tools;

/**
 * @desc 写入软件更新地址
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
@SuppressWarnings("serial")
@WebServlet("/api/controller/update/postinfo")
public class SetUpdateController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("21asdasdasddasdas");
			SetUpdateServlet.Info(request, response);
		} catch (Exception e) {
			tools.print(response, -1, "请求异常", null);
		}
		
		
	}
}
