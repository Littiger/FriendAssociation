/**
 * 
 */
package com.yvlu.servlet.controller.school;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.mapper.MappingData;
import org.bytedeco.javacpp.presets.opencv_core.Str;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tencentcloudapi.cdn.v20180606.models.ReportData;
import com.yvlu.dao.controller.register.RegisterDao;
import com.yvlu.tools.tools;

/**
 * @desc 获取学校信息
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月3日 下午3:35:46
 */
public class GetSchoolInfoServlet {
	private static RegisterDao registerDao = new RegisterDao();
	public static void Info(String token,HttpServletResponse response) {
		Map<String, Object> userinfo = registerDao.getRootByToken(token);
		if (userinfo == null || userinfo.size() ==0) {
			tools.print(response, -2, "登录过期", null);
			return;
		}else{
			List<Map<String, Object>> info = registerDao.GetSchoolInfo();
			if (info ==null || info.size() ==0) {
				tools.print(response, -3, "获取软件信息失败", null);
			}else{
				List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
				try {
					for (Map<String, Object> map : info) {
						System.out.println(map);
						Map<String, Object> MappingData = new HashMap<String, Object>();
						Map<String, Object> MappingData02 = new HashMap<String, Object>();
						MappingData.put("schoolid", map.get("schoolid"));
						MappingData.put("schoolname",map.get("schoolname"));
						MappingData.put("schoolsede", map.get("schoolsede"));
						if (map.get("schoolsede") ==null) {
							MappingData.put("schoolsede", "");
						}
						
						MappingData02.put("user", map.get("user"));
						if (map.get("user")==null) {
							MappingData02.put("user", 0);
						}
						MappingData02.put("posts", map.get("posts"));
						if(map.get("posts")==null){
							MappingData02.put("posts", 0);
						}
						MappingData02.put("audits", map.get("audits"));
						if (map.get("audits") ==null) {
							MappingData02.put("audits", 0);
						}
						MappingData.put("countinfo", MappingData02);
						listData.add(MappingData);
					}
					tools.print(response, 200, "获取成功", JSONObject.toJSONString(listData,SerializerFeature.WriteMapNullValue));
					return;
				} catch (Exception e) {
					tools.print(response, -3, "获取信息异常", null);
					e.printStackTrace();
				}
			}
		}
	}
}
