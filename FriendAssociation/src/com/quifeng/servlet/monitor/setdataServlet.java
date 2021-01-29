package com.quifeng.servlet.monitor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.monitor.monitorDao;

/**
 * @desc   设置实时温湿度与平均数据
 * @author JZH
 * @time   2021年1月29日
 */
public class setdataServlet {

	monitorDao monitorDao = new monitorDao();
	
	public void setData(HttpServletRequest request, HttpServletResponse response) {
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		
		//接值
		String D = request.getParameter("D");
		String H = request.getParameter("H");
		String M = request.getParameter("M");
		String temps = request.getParameter("temps");
		String huty = request.getParameter("huty");
		String meantemps = request.getParameter("meantemps");
		String meanhuty = request.getParameter("meanhuty");
		
		try {
			//判空
			if(D == null || D.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常、设置失败");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(H == null || H.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常、设置失败");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(M == null || M.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常、设置失败");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(temps == null || temps.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常、设置失败");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(huty == null || huty.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常、设置失败");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(meantemps == null || meantemps.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常、设置失败");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(meanhuty == null || meanhuty.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常、设置失败");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			//添加数据
			int count = monitorDao.addTemp(D,H,M,temps,huty);
			//修改之前本小时内的平均温度(在一个小时内本数据会一直变化 所以应该一直存覆盖)
			int count2 = monitorDao.upDateMean(D,H,meantemps,meanhuty);
			if(count2 == 0){
				//返回值为0  表里没有本小时数据  添加数据
				int count3 = monitorDao.addMeanTemp(D,H,meantemps,meanhuty);
			}
			jsonObject = new JSONObject();
			jsonObject.put("code", "200");
			jsonObject.put("msg", "设置成功");
			writer.write(jsonObject.toJSONString());
			return;
			
			
			
			
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
