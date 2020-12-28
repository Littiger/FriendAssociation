package com.quifeng.utils.state;

/**
 * @Desc 用户状态
 * @author 语录
 *
 */
public enum UserType {

	USERZERO, //正常
	USERONE, //用户发帖子被禁
	USRETOW, //禁全部
	USERTHREE, //禁评论
	USERFOUR, //注册没有验证
	USERFIVE, // 注册没有验证人脸
	USERSIX,   //注册没有验证手机号
	USERSEVEN  //注册成功后没有选择学校
	
}
