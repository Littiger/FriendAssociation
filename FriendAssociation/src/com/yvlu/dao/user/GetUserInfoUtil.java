package com.yvlu.dao.user;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc   获取用户信息工具类
 * @author JZH
 * @time   2021年2月3日
 */
public class GetUserInfoUtil {
	
	public static Map<String, Object> getUserInfo(Map<String, Object> map){
		Map<String, Object> user = new HashMap<String, Object>();
		//uid
		user.put("userid", Integer.parseInt(map.get("uid").toString()));
		//用户名
		user.put("username", map.get("username").toString());
		//签名
		Object usersign = map.get("useras");
		if(usersign == null || usersign.toString().equals("")){//没有签名
			user.put("usersign", "");
		}
		else{
			user.put("usersign", usersign.toString());
		}
		//头像
		user.put("useravatar", map.get("useravatar").toString());
		//发帖数量
		Object dynamic = map.get("pcount");
		if(dynamic == null || dynamic.toString().equals("0")){//没发过帖子
			user.put("dynamic", "0");
		}
		else{
			user.put("dynamic", dynamic.toString());
		}
		//粉丝
		Object fans = map.get("fcount");
		if(fans == null || fans.toString().equals("0")){//没粉丝
			user.put("fans", "0");
		}
		else{
			user.put("fans", fans.toString());
		}
		//关注
		Object concern = map.get("ccount");
		if(concern == null || concern.toString().equals("0")){//没关注
			user.put("concern", "0");
		}
		else{
			user.put("concern", concern.toString());
		}
		//历史浏览
		Object history = map.get("tcount");
		if(history == null || history.toString().equals("0")){//没历史记录
			user.put("history", "0");
		}
		else{
			user.put("history", history.toString());
		}
		//性别
		Object usersex = map.get("usersex");
		if(usersex == null || usersex.toString().equals("") || usersex.toString().equals("null")){//未设置性别
			user.put("usersex", "");
		}
		else{
			user.put("usersex", usersex.toString());
		}
		//所学专业
		Object usermajor = map.get("usermajor");
		if(usermajor == null || usermajor.toString().equals("") || usermajor.toString().equals("null")){//未选专业
			user.put("usermajor", "");
		}
		else{
			user.put("usermajor", usermajor.toString());
		}
		//生日
		Object userbir = map.get("userbirth");
		if(userbir == null || userbir.toString().equals("") || userbir.toString().equals("null")){//未选专业
			user.put("userbir", "");
		}
		else{
			user.put("userbir", userbir.toString());
		}
		//是否为在校生
		Object ugraduate = map.get("auth");
		if(ugraduate == null || ugraduate.toString().equals("") || ugraduate.toString().equals("null")){//未选专业
			user.put("ugraduate", "");
		}
		else{
			user.put("ugraduate", Integer.parseInt(ugraduate.toString()));
		}
		//学校
		Object uschool = map.get("schoolname");
		if(uschool == null || uschool.toString().equals("") || uschool.toString().equals("null")){//未选专业
			user.put("uschool", "");
		}
		else{
			user.put("uschool", uschool.toString());
		}
		
		return user;
		
	}
	
}
