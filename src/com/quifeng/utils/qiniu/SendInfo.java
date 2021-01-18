package com.quifeng.utils.qiniu;

import java.util.UUID;

import io.goeasy.GoEasy;

public class SendInfo {

	/**
	 * 发送消息
	 * 
	 * @param userid 要发给谁的id
	 * @param data   发送的数据
	 * @return
	 */
	public static boolean pushInfo(String userid, String data) {
		GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-d844d3266bcc49cf9f5bab3ff195d10d");
		goEasy.publish(userid, data);
		return true;
	}

	/**
	 * @desc 返回去掉-的uuid
	 * @return
	 */
	public static String GetUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getTimeMillis() {
		return System.currentTimeMillis() + "";

	}

}
