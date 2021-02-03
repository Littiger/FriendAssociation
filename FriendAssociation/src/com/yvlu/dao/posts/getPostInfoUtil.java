package com.yvlu.dao.posts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.quifeng.utils.dao.DateUtils;

/**
 * @desc   提取帖子信息工具类
 * @author JZH
 * @time   2021年2月3日
 */ 
public class getPostInfoUtil {
	
	/**
	 * 提取信息
	 * {
            "userinfo":{
                "uname":"梦伴",//发帖人姓名
                "useravatar":"http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix"  // 用户头像
            },
            "placa":{//帖子模块的信息
                "placaid":5,
                "placaname":"表白墙"
            },
            "postid":1,   //帖子ID
            "type":1,  //帖子的类型  1 = 文字帖   2 = 图片帖子  3  = 视频帖子
            "posttext":"梦伴发送的第一条数字", // 文本数值
            "postimg" :"http://file.qsub.cn/082cfad61e9a4461a93d786c9a248c111608481610394.jpg", //图片Url
            "postvideo":"http://file.qsub.cn/%E8%BF%85%E9%9B%B7%E5%BD%B1%E9%9F%B3%202020-12-05%2012-56-57.mp4",// 视频 如果有视频的话就没有图片了
            "createtime":"2021年1月1日" //时间
        }
	 * @param map
	 * @return
	 */
	public static Map<String, Object> getInfo(Map<String, Object> map){
		//汇总
		Map<String, Object> all = new HashMap<>();
		//usreinfo
		Map<String, Object> userinfo = new HashMap<String, Object>();
		userinfo.put("uname", map.get("username").toString());//用户名
		userinfo.put("useravatar", map.get("useravatar").toString());//头像
		//placa
		Map<String, Object> placa = new HashMap<String, Object>();
		placa.put("placaid", Integer.parseInt(map.get("placaid").toString()));//板块id
		placa.put("placaname", map.get("placaname").toString());//板块名
		//添加到汇总
		all.put("userinfo", userinfo);
		all.put("placa", placa);
		//其他信息
		all.put("postid", Integer.parseInt(map.get("postid").toString()));//帖子id
		all.put("type", getPosttype(map));//帖子类型
		//帖子文本
		if(map.get("posttext") == null || map.get("posttext").equals("")){
			all.put("posttext", "");
		}
		else{
			all.put("posttext", map.get("posttext").toString());
		}
		//显示图片或视频
		String postvideo = (String) map.get("postvideo");
		String postimg = (String) map.get("postimg");
		if (postvideo != null) {
			all.put("postvideo", postvideo);
		} 
		else if (postimg != null) {
			all.put("postimg", postimg);
		} 
		else {
			all.put("postimg", "");
			all.put("postvideo", "");
		}
		//发帖时间
		all.put("createtime", DateUtils.MillToHourAndMin(map.get("ptime").toString()));
		
		return all;
	}
	
	/**
	 * 获取帖子类型    1 = 文字帖   2 = 图片帖子  3  = 视频帖子
	 * @return
	 */
	public static int getPosttype(Map<String, Object> map) {
		// 帖子类型
		// 视频帖
		if (map.get("postvideo") != null) {
			return 3;
		}
		// 图片帖
		else if (map.get("postimg") != null) {
			return 2;
		}
		// 文字帖
		else if (map.get("posttext") != null) {
			return 1;
		}
		return 1;
	}
	
	
	
	
	
}
