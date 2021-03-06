package com.quifeng.utils.userinfo;

import com.sun.accessibility.internal.resources.accessibility;
import com.sun.mail.handlers.text_html;

/**
 * @desc   验证用户信息
 * @author JZH
 *
 */
public class UserInfoUtil {
	
	/**
	 * 验证用户名
	 * @return
	 */
	public static boolean userNameYanZheng(String username,int EClen,int ENlen,int CHlen){
		boolean flag = false;
		boolean flag2 = false;
		
		//遍历字符串
		for(int i  = 0;i<username.length() ; i++){
			char temp = username.charAt(i);
			System.out.println(temp);
			//判断英文
			if((Integer.valueOf(temp) >= Integer.valueOf('a') && Integer.valueOf(temp) <= Integer.valueOf('z'))
				|| Integer.valueOf(temp) >= Integer.valueOf('A') && Integer.valueOf(temp) <= Integer.valueOf('Z')){
				flag=true;
			}
			else{
				flag2 = true;
			}
		}
		
		//有中文有英文
		if(flag == true && flag2 == true){
			System.out.println("中英文");
			if(username.getBytes().length > EClen){//中英文名字超过16位
				return false;
			}
			else{
				return true;
			}
		}
		//纯英文 
		if(flag == true && flag2 == false){
			System.out.println("纯英文");
			if(username.getBytes().length > ENlen){//超过16位
				return false;
			}
			else{
				return true;
			}
		}
		//纯中文
		if(flag == false){
			System.out.println("纯中文");
			if(username.getBytes().length > CHlen){//超过24位
				return false;
			}
			else{
				return true;
			}
		}
		return false;
	}
	
}
