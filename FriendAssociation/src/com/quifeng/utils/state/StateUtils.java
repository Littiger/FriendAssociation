package com.quifeng.utils.state;

import com.quifeng.dao.user.UserStateDao;
import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 * @Desc 用户状态
 * @author 语录
 *
 */
public class StateUtils {
	
	private static UserStateDao usersatte=new UserStateDao();
	
	/**
	 * @Desc 更新用户状态
	 * @param user 用户id 或者手机号
	 * @param type
	 * @return
	 */
	public static boolean queryType(String user,UserType type){
		switch (type) {
		case USERZERO:
			return 0<usersatte.updateUserState(user, "0");
		case  USERONE:
			return 0<usersatte.updateUserState(user, "1");
		case  USRETOW:
			return 0<usersatte.updateUserState(user, "2");
		case  USERTHREE:
			return 0<usersatte.updateUserState(user, "3");
		case  USERFIVE:
			return 0<usersatte.updateUserState(user, "5");
		case  USERSIX:
			return 0<usersatte.updateUserState(user, "6");
		case USERSEVEN:
			return 0<usersatte.updateUserState(user, "7");			
		}	
		return false;
	}
	
	

	
}
