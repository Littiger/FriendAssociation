package com.quifeng.servlet.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.monitor.monitorDao;
/**
 * @desc   获取实时温湿度
 * @author JZH
 * @time  2021年1月29日
 */
public class getdataServlet {
	
	monitorDao monitorDao = new monitorDao();

	public void getData(HttpServletRequest request, HttpServletResponse response) {
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		
		
		try {
			
			//获取数据
			Map<String, Object> temp = monitorDao.getData();
			if(temp == null || temp.size() == 0){//没有数据
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "暂无数据");
				writer.write(jsonObject.toJSONString());
				return;
			}
			else{
				System.out.println(temp);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("tempture", temp.get("temps").toString());
				data.put("humidity", temp.get("huty").toString());
				jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("msg", "获取成功");
				jsonObject.put("data", data);
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			
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
