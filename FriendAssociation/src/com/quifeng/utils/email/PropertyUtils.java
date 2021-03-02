package com.quifeng.utils.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
/**
 * @desc   properties文件的处理工具类
 * @author JZH
 * @time   2020-12-14
 */
public class PropertyUtils {

	/**
	 * 解析properties文件并转为map
	 * @param propertyPath 被解析文件地址
	 * @return 返回Map
	 * @throws IOException
	 */
	public static Map<String, Object> getyInfoOfPropert(String propertyPath) throws IOException{
		 Map<String, Object> map=new HashMap<String, Object>();
		 Properties prop=new Properties();
		//读取mail.properties这个文件，转变为流
		 InputStream inputStream = PropertyUtils.class.getClassLoader().getResourceAsStream(propertyPath);
		 //把获取到的流对象转变为缓存区读对象
		 BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
		 
		 //加载mail.properties
		 prop.load(bufferedReader);
		 //获取一个迭代器
		 Iterator<String> it = prop.stringPropertyNames().iterator();
		 while (it.hasNext()) 
		 {
			String key = (String) it.next();
			System.out.println(key+" : "+prop.getProperty(key));
			map.put(key, prop.getProperty(key));
		}
		 bufferedReader.close();
		 inputStream.close();
		
		return map;
	}



}
