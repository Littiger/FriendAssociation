package com.quifeng.servlet.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.monitor.monitorDao;
import com.quifeng.utils.dao.DateUtils;

/**
 * @desc   获取有数据DAY序号
 * @author JZH
 * @time   2021年1月29日
 */
public class getdayServlet {
	
	monitorDao monitorDao = new monitorDao();

	public void getDay(HttpServletRequest request, HttpServletResponse response) {
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
			List<Map<String, Object>> list = monitorDao.getDay(DateUtils.CurrentyyyymmddTime());
			if(list == null || list.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "暂无数据");
				writer.write(jsonObject.toJSONString());
				return;
			}
			List<String> dayList = new ArrayList<String>();
			for (Map<String, Object> map : list) {
				dayList.add(map.get("day").toString());
			}
			jsonObject = new JSONObject();
			jsonObject.put("code", "200");
			jsonObject.put("msg", "获取成功");
			jsonObject.put("data", dayList);
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
