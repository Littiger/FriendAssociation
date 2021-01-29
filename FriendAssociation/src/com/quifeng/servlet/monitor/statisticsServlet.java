package com.quifeng.servlet.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quifeng.dao.monitor.monitorDao;
/**
 * @desc   获取统计列表
 * @author JZH
 * @time   2021年1月29日
 */
public class statisticsServlet {
	
	monitorDao monitorDao = new monitorDao();

	public void getTongJi(HttpServletRequest request, HttpServletResponse response) {
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		
		//接值
		String date = request.getParameter("date");
		
		try {
			//判空
			if(date == null || date.equals("")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "参数异常");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//存储数据的集合
			List<String> dateaxis = new ArrayList<String>();
			List<Double> temptureaxis = new ArrayList<>();
			List<Double> humidity = new ArrayList<>();
			//查询数据
			List<Map<String, Object>> list = monitorDao.getStatData(date);
			if(list == null || list.size() == 0){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "暂无数据");
				writer.write(jsonObject.toJSONString());
				return;
			}
			else{
				//最近的小时
				int uphour = Integer.parseInt(list.get(list.size()-1).get("hour").toString());
				//存储最终数据
				Map<String, Object> data = new HashMap<String, Object>();
				for(int i = 0;i<uphour;i++){
					dateaxis.add((i+1)+"点");//时间
					for (Map<String, Object> map : list) {
						//如果这个时间有值
						if (Integer.parseInt(map.get("hour").toString()) == i+1) {
							//温度
							temptureaxis.add(Double.parseDouble(map.get("meantemps").toString()));
							//湿度
							humidity.add(Double.parseDouble(map.get("meanhuty").toString()));
						}
					}
					//数据库中没有这个时间段，0.00填充
					if(temptureaxis == null || temptureaxis.size()<i+1){
						//温度
						temptureaxis.add(0.00);
						//湿度
						humidity.add(0.00);
					}
					
				}
				//取完值
				data.put("dateaxis", dateaxis);
				data.put("temptureaxis", temptureaxis);
				data.put("humidity", humidity);
				
				if(data != null){
					jsonObject = new JSONObject();
					jsonObject.put("code", "200");
					jsonObject.put("msg", "获取成功");
					jsonObject.put("data", data);
					writer.write(jsonObject.toJSONString());
					return;
				}
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
