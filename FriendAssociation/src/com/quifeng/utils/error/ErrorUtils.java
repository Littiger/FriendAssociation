package com.quifeng.utils.error;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * @Desc 对常见返回数据的封装
 * @author 语录
 *
 */
public class ErrorUtils {

	/**
	 * @Desc 缺少参数
	 * @return
	 */
	public static String isNullP(){
		Map<String, Object> data =new HashMap<>();
		data.put("code", "-1");
		data.put("msg", "缺少参数");
		return JSON.toJSONString(data);
	}
	
	/**
	 * @Desc Token 失效
	 * @return
	 */
	public static String isToken(){
		Map<String, Object> data =new HashMap<>();
		data.put("code", "-1");
		data.put("msg", "登录失效，请重新登录");
		return JSON.toJSONString(data);
	}
	
	
	/**
	 * @Desc 请求成功
	 * @return
	 */
	public static String successful(){
		Map<String, Object> data =new HashMap<>();
		data.put("code", "200");
		data.put("msg", "请求成功");
		return JSON.toJSONString(data);
	}
	
	/**
	 * @Desc 请求成功
	 * @return
	 */
	public static String errorTomCat(){
		Map<String, Object> data =new HashMap<>();
		data.put("code", "-5");
		data.put("msg", "服务器内部错误");
		return JSON.toJSONString(data);
	}
	
	
}
