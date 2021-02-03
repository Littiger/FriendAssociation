package com.yvlu.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.regexp.internal.recompile;

/**
 * @desc 工具类
 * @version 1.0
 * @author 秋枫
 * @time 2021年2月2日15:42:23
 */
public class tools {
	/**
	 * @desc 写出响应
	 * @param response
	 * @param code
	 *            响应码
	 * @param msg
	 *            响应信息
	 * @param data
	 *            数据内容 可以为null
	 */
	public static void print(HttpServletResponse response, int code, String msg, String data) {

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			// 写入响应内容
			String putss = "";
			if (data == null) {
				putss = "{\"code\":" + code + ",\"msg\":\"" + msg + "\"}";
			} else {
				putss = "{\"code\":" + code + ",\"msg\":\"" + msg + "\"" + ",\"data\":" + data + "}";
			}
			out.write(putss);
			out.flush();
			out.close();
		} catch (IOException e) {
			System.err.println("写出响应异常,发生异常的详细信息");
			e.printStackTrace();
		}

	}

	/**
	 * @desc MD5加密
	 * @author qiufeng
	 * @param plainText
	 * @return 加密值
	 */
	public static String MD5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有这个md5算法！");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}
	
	/**
	 * @desc 多个字符串判空
	 * 		如果有一个字符串为空则返回{true}  都不为空则返回{false}
	 * @author qiufeng
	 * @param args
	 */
	public static boolean isnull(String... args) {
		for (String string : args) {
			if(string ==null || string.equals("")){
				return true;
			}
		}
		return false;
	}
}
